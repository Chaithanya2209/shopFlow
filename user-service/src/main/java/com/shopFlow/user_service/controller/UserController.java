package com.shopFlow.user_service.controller;


import com.shopFlow.user_service.dto.UserRequest;
import com.shopFlow.user_service.dto.UserResponse;
import com.shopFlow.user_service.service.UserService;
import jakarta.validation.Valid;

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

//    @PostMapping("/bulk")
//    public ResponseEntity<List<UserResponse>> addProductBulk(@Valid @RequestBody  List<ProductRequest> product)
//    {
//        List<ProductResponse> created = productService.createBulk(product);
//        return ResponseEntity.status(HttpStatus.CREATED).body(created);
//    }


    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {

        List<UserResponse> users=  userService.getAllUsers();

        return  ResponseEntity.status(HttpStatus.OK).body(users);

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

//    @PutMapping("/{id}")
//    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody  ProductRequest productRequest)
//    {
//        ProductResponse productResponse= productService.update(id, productRequest);
//
//        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
//    }



}
