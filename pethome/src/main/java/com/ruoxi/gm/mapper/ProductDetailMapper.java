package com.ruoxi.gm.mapper;

import com.ruoxi.gm.domain.ProductDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-02-21 13:59
 */
public interface ProductDetailMapper {
    void save(ProductDetail pd);
    void remove(Long id);
    void update(ProductDetail pd);

    void dels(ArrayList<Long> ids);

    List<ProductDetail> selectAll();
}
