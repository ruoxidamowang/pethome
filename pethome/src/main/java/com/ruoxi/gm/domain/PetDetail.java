package com.ruoxi.gm.domain;

import lombok.Data;

@Data
public class PetDetail {
    private Long id;
    private Pet pet;
    private String adoptNotice;
    private String intro;
}
