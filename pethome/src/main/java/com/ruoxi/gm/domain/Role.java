package com.ruoxi.gm.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 若兮
 * @create 2022-02-21 16:55
 */
@Data
public class Role implements Serializable {
    private Long id;
    private String name;
    private String sn;
}
