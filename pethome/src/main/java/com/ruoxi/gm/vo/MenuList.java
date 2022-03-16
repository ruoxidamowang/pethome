package com.ruoxi.gm.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 若兮
 * @create 2022-03-12 15:00
 */
@Data
public class MenuList {
    private Integer id;
    private String name;
    private String path;
    private List<MenuList> children;
}
