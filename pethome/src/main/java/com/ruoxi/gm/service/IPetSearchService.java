package com.ruoxi.gm.service;

import com.github.pagehelper.PageInfo;
import com.ruoxi.gm.domain.SearchMasterMsg;
import com.ruoxi.gm.query.PetSearchQuery;

import java.util.ArrayList;

/**
 * @author 若兮
 * @create 2022-03-07 10:39
 */
public interface IPetSearchService {
    void add(SearchMasterMsg smm);
    void del(Long id);
    void update(SearchMasterMsg smm);

    PageInfo<SearchMasterMsg> findByPage(PetSearchQuery query);

    void removeAll(ArrayList<Long> ids);
}
