package com.ecommerce.user.services;

import com.ecommerce.user.dto.AddressDto;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.models.Address;
import com.ecommerce.user.models.User;
import com.ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponse)
                .toList();
    }

    public void addUser(UserRequest userRequest) {
        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }

    // ✅ String id
    public Optional<UserResponse> fetchAUser(String id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    // ✅ String id
    public boolean updateUser(String id, UserRequest updatedUserRequest) {
        return userRepository.findById(id)
                .map(user -> {
                    updateUserFromRequest(user, updatedUserRequest);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }

    // ================== HELPER METHODS ==================

    private void updateUserFromRequest(User user, UserRequest userRequest) {

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());

        if (userRequest.getAddress() != null) {

            Address address = user.getAddress();
            if (address == null) {
                address = new Address();
            }

            AddressDto dto = userRequest.getAddress();
            address.setStreet(dto.getStreet());
            address.setCity(dto.getCity());
            address.setState(dto.getState());
            address.setZipCode(dto.getZipCode());
            address.setCountry(dto.getCountry());

            user.setAddress(address);
        }
    }

    private UserResponse mapToUserResponse(User user) {

        UserResponse response = new UserResponse();
        response.setId(user.getId()); // String id
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setZipCode(user.getAddress().getZipCode());
            addressDto.setCountry(user.getAddress().getCountry());
            response.setAddress(addressDto);
        }

        return response;
    }
}
