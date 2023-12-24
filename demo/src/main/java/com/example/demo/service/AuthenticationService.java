package com.example.demo.service;

import com.example.demo.dto.ErrorDTO;
import com.example.demo.dto.JwtResponseDTO;
import com.example.demo.dto.SignUpDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.enums.ERole;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    public JwtResponseDTO login(UserDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtil.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(role->role.getAuthority())
                .collect(Collectors.toList());

        JwtResponseDTO jwtReponseDTO = new JwtResponseDTO();
        jwtReponseDTO.setName(userDetails.getName());
        jwtReponseDTO.setToken(jwtToken);
        jwtReponseDTO.setRoles(roles);
        jwtReponseDTO.setId(userDetails.getId());

        return jwtReponseDTO;
    }

    public Long signup(SignUpDTO signupDTO) {
        List<ErrorDTO> errorDTOS = new ArrayList<>();
        if(userRepository.existsByEmail(signupDTO.getEmail())){
            errorDTOS.add(new ErrorDTO("AUTH_001", "Email already exist"));
            throw new BusinessException(errorDTOS);
        }
        String hashedPassword = passwordEncoder.encode(signupDTO.getPassword());
        Set<RoleEntity> roleEntities = new HashSet<>();
        Optional<RoleEntity> optRole = null;
        if (signupDTO.getRole() != null && signupDTO.getRole().equals("ADMIN")){
            optRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN);
        }
        else{
            optRole = roleRepository.findByRoleName(ERole.ROLE_USER);
        }

        roleEntities.add(optRole.get());

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(signupDTO.getEmail());
        userEntity.setName(signupDTO.getName());
        userEntity.setPhone(signupDTO.getPhone());
        userEntity.setPassword(hashedPassword);
        userEntity.setRoles(roleEntities);

        userEntity = userRepository.save(userEntity);
        return userEntity.getId();
    }

}
