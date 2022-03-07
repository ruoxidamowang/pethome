package com.ruoxi.gm.service;

import com.github.pagehelper.PageInfo;
import com.ruoxi.gm.domain.Product;
import com.ruoxi.gm.query.ProductQuery;

/**
 * @author 若兮
 * @create 2022-03-07 10:39
 */
public interface IProductService {
    PageInfo<Product> findByPage(ProductQuery query);
}
