package com.ruoxi.gm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoxi.gm.domain.Product;
import com.ruoxi.gm.mapper.ProductMapper;
import com.ruoxi.gm.query.ProductQuery;
import com.ruoxi.gm.service.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-03-07 10:39
 */
@Service
@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
public class ProductServiceImpl implements IProductService {
    @Resource
    private ProductMapper mapper;

    @Override
    public PageInfo<Product> findByPage(ProductQuery query) {
        PageHelper.startPage(query.getStart(), query.getPageSize());
        List<Product> shops = mapper.loadAll(query);
        Page<Product> page= (Page<Product>) shops;
        return page.toPageInfo();
    }
}
