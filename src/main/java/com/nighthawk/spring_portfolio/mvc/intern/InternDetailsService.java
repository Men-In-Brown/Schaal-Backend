package com.nighthawk.spring_portfolio.mvc.intern;

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
public class InternDetailsService implements UserDetailsService {

    @Autowired
    private InternJpaRepository internJpaRepository;

    @Autowired
    private InternRoleJpaRepository internRoleJpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Intern intern = internJpaRepository.findByEmail(email);
        if (intern == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        intern.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(intern.getEmail(), intern.getPassword(), authorities);
    }

    public List<Intern> listAll() {
        return internJpaRepository.findAllByOrderByNameAsc();
    }

    public List<Intern> list(String name, String email) {
        return internJpaRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(name, email);
    }

    public List<Intern> listLike(String term) {
        return internJpaRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);
    }

    public List<Intern> listLikeNative(String term) {
        String likeTerm = String.format("%%%s%%", term);
        return internJpaRepository.findByLikeTermNative(likeTerm);
    }

    public void save(Intern intern) {
        intern.setPassword(passwordEncoder.encode(intern.getPassword()));
        internJpaRepository.save(intern);
    }

    public Intern get(long id) {
        return internJpaRepository.findById(id).orElse(null);
    }

    public Intern getByEmail(String email) {
        return internJpaRepository.findByEmail(email);
    }

    public void delete(long id) {
        internJpaRepository.deleteById(id);
    }

    public void defaults(String password, String roleName) {
        for (Intern intern : listAll()) {
            if (intern.getPassword() == null || intern.getPassword().isEmpty() || intern.getPassword().isBlank()) {
                intern.setPassword(passwordEncoder.encode(password));
            }
            if (intern.getRoles().isEmpty()) {
                InternRole role = internRoleJpaRepository.findByName(roleName);
                if (role != null) {
                    intern.getRoles().add(role);
                }
            }
        }
    }

    public void saveRole(InternRole role) {
        InternRole roleObj = internRoleJpaRepository.findByName(role.getName());
        if (roleObj == null) {
            internRoleJpaRepository.save(role);
        }
    }

    public List<InternRole> listAllRoles() {
        return internRoleJpaRepository.findAll();
    }

    public InternRole findRole(String roleName) {
        return internRoleJpaRepository.findByName(roleName);
    }

    public void addRoleToIntern(String email, String roleName) {
        Intern intern = internJpaRepository.findByEmail(email);
        if (intern != null) {
            InternRole role = internRoleJpaRepository.findByName(roleName);
            if (role != null) {
                boolean addRole = true;
                for (InternRole roleObj : intern.getRoles()) {
                    if (roleObj.getName().equals(roleName)) {
                        addRole = false;
                        break;
                    }
                }
                if (addRole) intern.getRoles().add(role);
            }
        }
    }
}