package com.shopFlow.user_service.controller;


import com.shopFlow.user_service.dto.PagedResponse;
import com.shopFlow.user_service.dto.UserRequest;
import com.shopFlow.user_service.dto.UserResponse;
import com.shopFlow.user_service.service.UserService;
import jakarta.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController( UserService productService)
    {

        this.userService = productService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserRequest user)
    {
        UserResponse created = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }




    @GetMapping
    public ResponseEntity<PagedResponse<UserResponse>> getAllProducts(@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "10") int size)
    {
        Pageable pageable= PageRequest.of(page, size);
        PagedResponse<UserResponse>products =  userService.getAllUsers(pageable);

        return  ResponseEntity.status(HttpStatus.OK).body(products);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {

        UserResponse fetch= userService.getUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(fetch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody  UserRequest userRequest)
    {
        UserResponse userResponse= userService.updateUser(id, userRequest);

        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }



}
