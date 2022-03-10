package com.ruoxi.gm.query;

import com.ruoxi.basic.query.BaseQuery;
import com.ruoxi.gm.domain.PetType;
import com.ruoxi.gm.domain.Shop;
import com.ruoxi.gm.domain.User;
import lombok.Data;

/**
 * @author 若兮
 * @create 2022-03-09 15:38
 */
@Data
public class PetSearchQuery extends BaseQuery {
    private Long id;
    private String name;
    private Long age;
    private Long gender;
    private String coatColor;
    private String resources;
    private PetType petType;
    private Double price;
    private String address;
    private Long state;
    private String title;
    private User user;
    private Shop shop;
}
