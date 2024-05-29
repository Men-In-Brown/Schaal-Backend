package com.nighthawk.spring_portfolio.mvc.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nighthawk.spring_portfolio.mvc.person.PersonRole;
import com.nighthawk.spring_portfolio.mvc.person.PersonRoleJpaRepository;
import com.nighthawk.spring_portfolio.mvc.teacher.Teacher;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class TeacherDetailsService implements UserDetailsService {

    @Autowired
    private TeacherJpaRepository teacherJpaRepository;

    @Autowired
    private PersonRoleJpaRepository personroleJpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Teacher teacher = teacherJpaRepository.findByEmail(email);
        if (teacher == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        teacher.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(teacher.getEmail(), teacher.getPassword(), authorities);
    }

    public List<Teacher> listAll() {
        return teacherJpaRepository.findAllByOrderByNameAsc();
    }

    public List<Teacher> list(String name, String email) {
        return teacherJpaRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(name, email);
    }

    public List<Teacher> listLike(String term) {
        return teacherJpaRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);
    }

    public List<Teacher> listLikeNative(String term) {
        String likeTerm = String.format("%%%s%%", term);
        return teacherJpaRepository.findByLikeTermNative(likeTerm);
    }

    public void save(Teacher teacher) {
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        teacherJpaRepository.save(teacher);
    }

    public Teacher get(long id) {
        return teacherJpaRepository.findById(id).orElse(null);
    }

    public Teacher getByEmail(String email) {
        return teacherJpaRepository.findByEmail(email);
    }

    public void delete(long id) {
        teacherJpaRepository.deleteById(id);
    }

    public void defaults(String password, String roleName) {
        for (Teacher teacher : listAll()) {
            if (teacher.getPassword() == null || teacher.getPassword().isEmpty() || teacher.getPassword().isBlank()) {
                teacher.setPassword(passwordEncoder.encode(password));
            }
            if (teacher.getRoles().isEmpty()) {
                PersonRole role = personroleJpaRepository.findByName(roleName);
                if (role != null) {
                    teacher.getRoles().add(role);
                }
            }
        }
    }

    public void saveRole(PersonRole role) {
        PersonRole roleObj = personroleJpaRepository.findByName(role.getName());
        if (roleObj == null) {
            personroleJpaRepository.save(role);
        }
    }

    public List<PersonRole> listAllRoles() {
        return personroleJpaRepository.findAll();
    }

    public PersonRole findRole(String roleName) {
        return personroleJpaRepository.findByName(roleName);
    }

    public void addRoleToTeacher(String email, String roleName) {
        Teacher teacher = teacherJpaRepository.findByEmail(email);
        if (teacher != null) {
            PersonRole role = personroleJpaRepository.findByName(roleName);
            if (role != null) {
                boolean addRole = true;
                for (PersonRole roleObj : teacher.getRoles()) {
                    if (roleObj.getName().equals(roleName)) {
                        addRole = false;
                        break;
                    }
                }
                if (addRole) teacher.getRoles().add(role);
            }
        }
    }
}