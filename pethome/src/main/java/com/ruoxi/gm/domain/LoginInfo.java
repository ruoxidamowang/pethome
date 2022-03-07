package com.ruoxi.gm.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 若兮
 * @create 2022-02-21 16:52
 */
@Data
public class LoginInfo implements Serializable {
    private Long id;
    private String username;
    private String phone;
    private String email;
    private String salt;
    private String password;
    private Byte type;
    private Byte disable;
}
