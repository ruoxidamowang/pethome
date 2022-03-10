package com.ruoxi.gm.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class SearchMasterMsg implements Serializable {
    private Long id;
    private String name;
    private Long age;
    private Long gender;
    private String coatColor;
    private String resources;
    private PetType petType;
    private Double price;
    private String address;
    private Long state;
    private String title;
    private User user;
    private Shop shop;
}
