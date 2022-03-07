package com.ruoxi.gm.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 若兮
 * @create 2022-03-07 10:33
 */
@Data
public class ProductDetail implements Serializable {
    private Long id;
    private Long product_id;
    private String intro;
    private String orderNotice;
}
