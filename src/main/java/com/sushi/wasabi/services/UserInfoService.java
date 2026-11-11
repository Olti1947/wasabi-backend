package com.sushi.wasabi.services;

import com.sushi.wasabi.dto.AddressDto;
import com.sushi.wasabi.dto.AddressRequest;
import com.sushi.wasabi.dto.EditUserRequest;
import com.sushi.wasabi.dto.UserDto;
import com.sushi.wasabi.entity.Address;
import com.sushi.wasabi.entity.User;
import com.sushi.wasabi.repository.AddressRepository;
import com.sushi.wasabi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public void editUser(Integer userId, EditUserRequest request){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("No such user with said id"));
        user.setFirstName(request.getFirstName());
        user.setEmail(request.getEmail());
        user.setLastName(request.getLastName());
        user.setPhone(!request.getPhone().isEmpty() ? request.getPhone() : null);

        userRepository.save(user);
    }

    public UserDto getPersonalInfo(Integer userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("No such user"));
        return user.getUserInfo();
    }

    public List<AddressDto> getAllAddresses(Integer userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("No such user"));

        return user.getAddresses().stream().map(Address::toDto).toList();
    }

    public AddressDto getDefaultAddress(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("No such user"));
        Optional<AddressDto> addressDto = user.getAddresses().stream().map(Address::toDto).filter(AddressDto::getIsDefault).findFirst();
        return addressDto.orElse(null);

    }

    @Transactional
    public void setDefaultAddress(Integer userId, Long addressId){
        addressRepository.unsetDefaultForUser(userId);

        Address address = addressRepository.findById(addressId).orElseThrow(
                ()-> new RuntimeException("Address not found")
        );

        address.setIsDefault(true);
        addressRepository.save(address);
    }

    @Transactional
    public void addAddress(AddressRequest addressRequest, Integer userId){


        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("No such user"));
        if(addressRequest.getIsDefault()){
            addressRepository.unsetDefaultForUser(userId);
        }
        Address address = new Address();
        address.setCity(addressRequest.getCity());
        address.setLabel(addressRequest.getLabel());
        address.setUser(user);
        address.setStreet(addressRequest.getStreet());
        address.setIsDefault(addressRequest.getIsDefault());
        address.setPostalCode(addressRequest.getPostalCode());

        addressRepository.save(address);
    }

    public void deleteAddress(Long addressId){
        addressRepository.deleteById(addressId);
    }

}
