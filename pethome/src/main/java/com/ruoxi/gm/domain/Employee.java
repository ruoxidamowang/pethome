package com.ruoxi.gm.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 若兮
 * @create 2022-02-21 13:58
 */
@Data
public class Employee implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String salt;
    private String password;
    private Integer age;
    private Integer state;
    private Department department;
    private LoginInfo logininfo;
    private Shop shop;
}
