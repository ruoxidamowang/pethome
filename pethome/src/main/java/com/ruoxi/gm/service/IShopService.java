package com.ruoxi.gm.service;

import com.github.pagehelper.PageInfo;
import com.ruoxi.gm.domain.Shop;
import com.ruoxi.gm.query.ShopQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-02-21 14:06
 */
public interface IShopService {
    void add(Shop s);
    void del(Long id);
    void update(Shop s);
    PageInfo<Shop> findByPage(ShopQuery query);

    void removeAll(ArrayList<Long> ids);

    List<Shop> findAllShop();
}
