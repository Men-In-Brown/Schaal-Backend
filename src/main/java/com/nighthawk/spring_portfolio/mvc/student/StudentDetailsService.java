package com.nighthawk.spring_portfolio.mvc.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nighthawk.spring_portfolio.mvc.teacher.Teacher;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class StudentDetailsService implements UserDetailsService {

    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Autowired
    private StudentRoleJpaRepository studentRoleJpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = studentJpaRepository.findByEmail(email);
        if (student == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        student.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(student.getEmail(), student.getPassword(), authorities);
    }

    public List<Student> listAll() {
        return studentJpaRepository.findAllByOrderByNameAsc();
    }

    public List<Student> list(String name, String email) {
        return studentJpaRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(name, email);
    }

    public List<Student> listLike(String term) {
        return studentJpaRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);
    }

    public List<Student> listLikeNative(String term) {
        String likeTerm = String.format("%%%s%%", term);
        return studentJpaRepository.findByLikeTermNative(likeTerm);
    }

    public void save(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentJpaRepository.save(student);
    }

    public Student get(long id) {
        return studentJpaRepository.findById(id).orElse(null);
    }

    public Student getByEmail(String email) {
        return studentJpaRepository.findByEmail(email);
    }

    public void delete(long id) {
        studentJpaRepository.deleteById(id);
    }

    public void defaults(String password, String roleName) {
        for (Student student : listAll()) {
            if (student.getPassword() == null || student.getPassword().isEmpty() || student.getPassword().isBlank()) {
                student.setPassword(passwordEncoder.encode(password));
            }
            if (student.getRoles().isEmpty()) {
                StudentRole role = studentRoleJpaRepository.findByName(roleName);
                if (role != null) {
                    student.getRoles().add(role);
                }
            }
        }
    }

    public void saveRole(StudentRole role) {
        StudentRole roleObj = studentRoleJpaRepository.findByName(role.getName());
        if (roleObj == null) {
            studentRoleJpaRepository.save(role);
        }
    }

    public List<StudentRole> listAllRoles() {
        return studentRoleJpaRepository.findAll();
    }

    public StudentRole findRole(String roleName) {
        return studentRoleJpaRepository.findByName(roleName);
    }

    public void addRoleToStudent(String email, String roleName) {
        Student student = studentJpaRepository.findByEmail(email);
        if (student != null) {
            StudentRole role = studentRoleJpaRepository.findByName(roleName);
            if (role != null) {
                boolean addRole = true;
                for (StudentRole roleObj : student.getRoles()) {
                    if (roleObj.getName().equals(roleName)) {
                        addRole = false;
                        break;
                    }
                }
                if (addRole) student.getRoles().add(role);
            }
        }
    }
}