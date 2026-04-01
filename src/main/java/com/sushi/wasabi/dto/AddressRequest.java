package com.sushi.wasabi.dto;

import lombok.Data;

@Data
public class AddressRequest {
    private String label;
    private String street;
    private String city;
    private String postalCode;
    private Boolean isDefault;
}
