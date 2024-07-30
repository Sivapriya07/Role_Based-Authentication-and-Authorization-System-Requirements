package com.exercise.process.validation;

import com.exercise.process.Entity.Role;
import com.exercise.process.Entity.User;
import com.exercise.process.Repository.Rolerepository;
import com.exercise.process.Repository.UserRepository;

import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DefaultRoleInitializer {


    private final PasswordEncoder passwordEncoder;
    private final Rolerepository rolerepo;
    private final UserRepository userrepo;

    public DefaultRoleInitializer(PasswordEncoder passwordEncoder, Rolerepository rolerepo, UserRepository userrepo) {
        this.passwordEncoder = passwordEncoder;
        this.rolerepo = rolerepo;
        this.userrepo = userrepo;
    }


    @PostConstruct
    public void init(){
        if(rolerepo.count() == 0){
          Role userRole = new Role();
            userRole.setRolename("user");
            rolerepo.save(userRole);

            Role adminRole = new Role();
            adminRole.setRolename("admin");
            rolerepo.save(adminRole);

            Role superAdminRole = new Role();
            superAdminRole.setRolename("SuperAdmin");
            rolerepo.save(superAdminRole);
        }


        if (userrepo.count()==0) {
            Role superAdminRole = rolerepo.findById(3).orElseThrow(() -> new RuntimeException("Superadmin role does not exist"));


            //Role role;

            User superadmin = new User();
            superadmin.setName("SuperAdmin");
            superadmin.setEmail("superadmin@gmail.com");
            superadmin.setPhone_no("9345623987");
            superadmin.setPassword(passwordEncoder.encode("12345@shr"));
            superadmin.setConfirmpassword(passwordEncoder.encode("12345@shr"));
            superadmin.setStatus("active");
            //superadmin.setRole(rolerepo.findByRolename("superAdmin"));
            superadmin.setRole(superAdminRole);
            userrepo.save(superadmin);
        }

        System.out.println("Default roles and superadmin are set successfully");
    }

    }

