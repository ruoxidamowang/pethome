package com.ruoxi.gm.mapper;

import com.ruoxi.gm.domain.Product;
import com.ruoxi.gm.query.ProductQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-02-21 13:59
 */
public interface ProductMapper {
    void save(Product s);
    void remove(Long id);
    void update(Product s);

    List<Product> loadAll(ProductQuery query);

    void dels(ArrayList<Long> ids);

    List<Product> selectAll();
}
