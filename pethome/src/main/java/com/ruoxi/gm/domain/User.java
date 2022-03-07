package com.ruoxi.gm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 若兮
 * @create 2022-03-02 15:05
 */
@Data
public class User implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String salt;
    private String password;
    private Integer state;
    private Integer age;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime = new Date();

    private String headImg;
    private LoginInfo logininfo;
}
