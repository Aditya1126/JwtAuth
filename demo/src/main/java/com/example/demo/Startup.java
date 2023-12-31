package com.example.demo;

import com.example.demo.entity.RoleEntity;
import com.example.demo.enums.ERole;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Startup implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Optional<RoleEntity> optRoleAd = roleRepository.findByRoleName(ERole.ROLE_ADMIN);
        if(optRoleAd.isEmpty()){
            RoleEntity re = new RoleEntity();
            re.setRoleName(ERole.ROLE_ADMIN);
            roleRepository.save(re);
        }
        Optional<RoleEntity> optRoleUs = roleRepository.findByRoleName(ERole.ROLE_USER);
        if(optRoleUs.isEmpty()){
            RoleEntity re = new RoleEntity();
            re.setRoleName(ERole.ROLE_USER);
            roleRepository.save(re);
        }
    }
}