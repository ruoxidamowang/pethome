package com.ruoxi.gm.service;

import com.github.pagehelper.PageInfo;
import com.ruoxi.gm.domain.Product;
import com.ruoxi.gm.query.ProductQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-03-07 10:39
 */
public interface IProductService {
    void add(Product p);
    void del(Long id);
    void update(Product p);

    PageInfo<Product> findByPage(ProductQuery query);

    void removeAll(ArrayList<Long> ids);

    List<Product> findAllProduct();
}
