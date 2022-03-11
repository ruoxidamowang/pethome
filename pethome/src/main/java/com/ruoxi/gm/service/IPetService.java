package com.ruoxi.gm.service;

import com.github.pagehelper.PageInfo;
import com.ruoxi.gm.domain.Pet;
import com.ruoxi.gm.query.PetQuery;

import java.util.ArrayList;

/**
 * @author 若兮
 * @create 2022-03-07 10:39
 */
public interface IPetService {
    void add(Pet p);
    void del(Long id);
    void update(Pet p);

    PageInfo<Pet> findByPage(PetQuery query);

    void removeAll(ArrayList<Long> ids);

    Pet findPetById(Long id);
}
