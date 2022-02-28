package com.ruoxi.gm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoxi.gm.domain.Shop;
import com.ruoxi.gm.mapper.ShopMapper;
import com.ruoxi.gm.query.ShopQuery;
import com.ruoxi.gm.service.IShopService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-02-28 18:52
 */
@Service
@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
public class ShopServiceImpl implements IShopService {
    @Resource
    private ShopMapper mapper;

    @Override
    @Transactional
    public void add(Shop s) {
        mapper.save(s);
    }

    @Override
    @Transactional
    public void del(Long id) {
        mapper.remove(id);
    }

    @Override
    @Transactional
    public void update(Shop s) {
        mapper.update(s);
    }

    @Override
    public PageInfo<Shop> findByPage(ShopQuery query) {
        PageHelper.startPage(query.getStart(), query.getPageSize());
        List<Shop> shops = mapper.loadAll(query);
        Page<Shop> page= (Page<Shop>) shops;
        return page.toPageInfo();
    }

    @Override
    @Transactional
    public void removeAll(ArrayList<Long> ids) {
        mapper.dels(ids);
    }

    @Override
    public List<Shop> findAllShop() {
        return mapper.selectAll();
    }
}
