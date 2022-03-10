package com.ruoxi.gm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoxi.gm.domain.SearchMasterMsg;
import com.ruoxi.gm.mapper.PetSearchMapper;
import com.ruoxi.gm.query.PetSearchQuery;
import com.ruoxi.gm.service.IPetSearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-03-09 16:45
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PetSearchServiceImpl implements IPetSearchService {
    @Resource
    private PetSearchMapper mapper;

    @Override
    @Transactional
    public void add(SearchMasterMsg smm) {
        mapper.save(smm);
    }

    @Override
    @Transactional
    public void del(Long id) {
        mapper.remove(id);
    }

    @Override
    @Transactional
    public void update(SearchMasterMsg smm) {
        mapper.update(smm);
    }

    @Override
    public PageInfo<SearchMasterMsg> findByPage(PetSearchQuery query) {
        PageHelper.startPage(query.getStart(), query.getPageSize());
        List<SearchMasterMsg> shops = mapper.loadAll(query);
        Page<SearchMasterMsg> page= (Page<SearchMasterMsg>) shops;
        return page.toPageInfo();
    }

    @Override
    @Transactional
    public void removeAll(ArrayList<Long> ids) {
        mapper.dels(ids);
    }
}
