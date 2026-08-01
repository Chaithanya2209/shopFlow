package com.shopFlow.user_service.service;

import com.shopFlow.user_service.dto.UserRequest;
import com.shopFlow.user_service.dto.UserResponse;
import com.shopFlow.user_service.entity.User;
import com.shopFlow.user_service.exception.DuplicateEmailException;
import com.shopFlow.user_service.exception.UserNotFoundException;
import com.shopFlow.user_service.mapper.UserMapper;
import com.shopFlow.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

   public  UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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

    public List<UserResponse> getAllUsers() {
        List<User> userList = userRepository.findAll();

        List<UserResponse> userResponses = userList.stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());

        return userResponses;
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
