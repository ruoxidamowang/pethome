package com.ruoxi.gm.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 若兮
 * @create 2022-02-21 16:57
 */
@Data
public class Permission implements Serializable {
    private String id;
    private String name;
    private String url;
    private String descs;
    private String sn;
    private String menu_id;
}
