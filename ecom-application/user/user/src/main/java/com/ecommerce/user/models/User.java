package com.ecommerce.user.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private UserRole role = UserRole.CUSTOMER;


    private Address address;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;




}
