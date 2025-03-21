package com.example.democustomerdetails.domain;

import lombok.Data;

@Data
public class CustomerRequestObject {

    private Long id;

    private String firstName;

    private String lastName;

    private String dob;
}
