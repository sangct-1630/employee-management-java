package com.example.employee.service;

import com.example.employee.dto.UserRegistrationDTO;
import com.example.employee.dto.UserViewDTO;
import com.example.employee.model.User;
import com.example.employee.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, 
         PasswordEncoder passwordEncoder, 
         ModelMapper modelMapper
     ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public UserViewDTO registerUser(UserRegistrationDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserViewDTO.class);
    }

    public UserViewDTO findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserViewDTO dto = modelMapper.map(user, UserViewDTO.class);
        
        return dto;
    }
}
