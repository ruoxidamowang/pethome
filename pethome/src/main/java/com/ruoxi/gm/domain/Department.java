package com.ruoxi.gm.domain;

import lombok.Data;

/**
 * @author 若兮
 * @create 2022-02-21 13:53
 */
@Data
public class Department {
    private Long id;
    private String sn;
    private String name;
    private String dirPath;
    private Integer state;
    private Employee manager;
    private Department parent;
}
