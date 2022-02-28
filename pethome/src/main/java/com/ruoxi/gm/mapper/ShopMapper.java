package com.ruoxi.gm.mapper;

import com.ruoxi.gm.domain.Shop;
import com.ruoxi.gm.query.ShopQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-02-21 13:59
 */
public interface ShopMapper {
    void save(Shop s);
    void remove(Long id);
    void update(Shop s);
    List<Shop> loadAll(ShopQuery query);

    void dels(ArrayList<Long> ids);

    List<Shop> selectAll();
}
