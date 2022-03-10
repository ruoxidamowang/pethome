package com.ruoxi.gm.mapper;

import com.ruoxi.gm.domain.SearchMasterMsg;
import com.ruoxi.gm.query.PetSearchQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-03-09 14:45
 */
public interface PetSearchMapper {
    void save(SearchMasterMsg smm);
    void remove(Long id);
    void update(SearchMasterMsg smm);

    List<SearchMasterMsg> loadAll(PetSearchQuery query);

    void dels(ArrayList<Long> ids);

    List<SearchMasterMsg> selectAll();
}
