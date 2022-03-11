package com.ruoxi.gm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoxi.gm.domain.Pet;
import com.ruoxi.gm.mapper.PetMapper;
import com.ruoxi.gm.query.PetQuery;
import com.ruoxi.gm.service.IPetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-03-10 14:33
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PetServiceImpl implements IPetService {
    @Resource
    private PetMapper mapper;

    @Override
    @Transactional
    public void add(Pet p) {
        mapper.save(p);
    }

    @Override
    @Transactional
    public void del(Long id) {
        mapper.remove(id);
    }

    @Override
    @Transactional
    public void update(Pet p) {
        mapper.update(p);
    }

    @Override
    public PageInfo<Pet> findByPage(PetQuery query) {
        PageHelper.startPage(query.getStart(), query.getPageSize());
        List<Pet> shops = mapper.loadAll(query);
        Page<Pet> page= (Page<Pet>) shops;
        return page.toPageInfo();
    }

    @Override
    @Transactional
    public void removeAll(ArrayList<Long> ids) {
        mapper.dels(ids);
    }

    @Override
    public Pet findPetById(Long id) {
        return mapper.findPetById(id);
    }
}
