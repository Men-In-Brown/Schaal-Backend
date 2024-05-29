package com.nighthawk.spring_portfolio.mvc.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nighthawk.spring_portfolio.mvc.person.PersonRoleJpaRepository;
import com.nighthawk.spring_portfolio.mvc.teacher.Teacher;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class AdminDetailsService implements UserDetailsService {

    @Autowired
    private AdminJpaRepository adminJpaRepository;

    @Autowired
    private PersonRoleJpaRepository personroleJpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminJpaRepository.findByEmail(email);
        if (admin == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        admin.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(admin.getEmail(), admin.getPassword(), authorities);
    }

    public List<Admin> listAll() {
        return adminJpaRepository.findAllByOrderByNameAsc();
    }

    public List<Admin> list(String name, String email) {
        return adminJpaRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(name, email);
    }

    public List<Admin> listLike(String term) {
        return adminJpaRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);
    }

    public List<Admin> listLikeNative(String term) {
        String likeTerm = String.format("%%%s%%", term);
        return adminJpaRepository.findByLikeTermNative(likeTerm);
    }

    public void save(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminJpaRepository.save(admin);
    }

    public Admin get(long id) {
        return adminJpaRepository.findById(id).orElse(null);
    }

    public Admin getByEmail(String email) {
        return adminJpaRepository.findByEmail(email);
    }

    public void delete(long id) {
        adminJpaRepository.deleteById(id);
    }

    public void defaults(String password, String roleName) {
        for (Admin admin : listAll()) {
            if (admin.getPassword() == null || admin.getPassword().isEmpty() || admin.getPassword().isBlank()) {
                admin.setPassword(passwordEncoder.encode(password));
            }
            if (admin.getRoles().isEmpty()) {
                AdminRole role = personroleJpaRepository.findByName(roleName);
                if (role != null) {
                    admin.getRoles().add(role);
                }
            }
        }
    }

    public void saveRole(AdminRole role) {
        AdminRole roleObj = personroleJpaRepository.findByName(role.getName());
        if (roleObj == null) {
            personroleJpaRepository.save(role);
        }
    }

    public List<AdminRole> listAllRoles() {
        return personroleJpaRepository.findAll();
    }

    public AdminRole findRole(String roleName) {
        return personroleJpaRepository.findByName(roleName);
    }

    public void addRoleToAdmin(String email, String roleName) {
        Admin admin = adminJpaRepository.findByEmail(email);
        if (admin != null) {
            AdminRole role = personroleJpaRepository.findByName(roleName);
            if (role != null) {
                boolean addRole = true;
                for (AdminRole roleObj : admin.getRoles()) {
                    if (roleObj.getName().equals(roleName)) {
                        addRole = false;
                        break;
                    }
                }
                if (addRole) admin.getRoles().add(role);
            }
        }
    }
}