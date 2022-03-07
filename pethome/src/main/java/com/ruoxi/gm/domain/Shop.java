package com.ruoxi.gm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 若兮
 * @create 2022-02-21 16:52
 */
@Data
public class Shop implements Serializable {
    private Long id;
    private String name;
    private String tel;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime = new Date();

    private Integer state;
    private String address;
    private String logo;
    private Employee admin;
}
