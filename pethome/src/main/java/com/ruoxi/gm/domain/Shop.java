package com.ruoxi.gm.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author 若兮
 * @create 2022-02-21 16:52
 */
@Data
public class Shop {
    private Long id;
    private String name;
    private String tel;
    private Date registerTime;
    private Integer state;
    private String address;
    private String logo;
    private Employee admin;
}
