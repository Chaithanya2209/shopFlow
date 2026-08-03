package com.shopFlow.user_service.service;

import com.shopFlow.user_service.dto.PagedResponse;
import com.shopFlow.user_service.dto.UserRequest;
import com.shopFlow.user_service.dto.UserResponse;
import com.shopFlow.user_service.entity.User;
import com.shopFlow.user_service.exception.DuplicateEmailException;
import com.shopFlow.user_service.exception.UserNotFoundException;
import com.shopFlow.user_service.mapper.UserMapper;
import com.shopFlow.user_service.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

   public  UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder=passwordEncoder;
    }

    public UserResponse create(UserRequest userRequest) {

       if(userRepository.existsByEmail(userRequest.getEmail()))
       {
           throw new DuplicateEmailException("Email already exists");
       }

       User userDetails = userMapper.toEntity(userRequest);



        User savedUser = userRepository.save(userDetails);
        return userMapper.toResponse(savedUser);

    }

    public UserResponse getUserById(Long id) {
       Optional<User> user= userRepository.findById(id);

       if(!user.isPresent())
       {
          throw new UserNotFoundException(id);
       }
        return userMapper.toResponse(user.get());
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already exists");
        }
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User updated = userRepository.save(user);
        return userMapper.toResponse(updated);
    }
    public PagedResponse<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserResponse> content = userPage.getContent().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());

        return new PagedResponse<>(
                content,
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.isLast()
        );
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
