package com.example.demo.repository;

import com.example.demo.entity.RoleEntity;
import com.example.demo.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {

    Optional<RoleEntity> findByRoleName(ERole eRole);
}
