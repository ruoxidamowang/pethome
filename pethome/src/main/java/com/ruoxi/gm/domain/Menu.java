package com.ruoxi.gm.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-02-21 17:06
 */
@Data
public class Menu implements Serializable {
    private Long id;
    private String name;
    private String url;
    private String icon;
    private Integer index;
    private List<Menu> children;
}
