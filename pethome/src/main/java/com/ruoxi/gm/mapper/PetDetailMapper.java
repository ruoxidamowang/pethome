package com.ruoxi.gm.mapper;

import com.ruoxi.gm.domain.Pet;
import com.ruoxi.gm.query.PetQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 若兮
 * @create 2022-03-09 14:45
 */
public interface PetDetailMapper {
    void save(Pet p);
    void remove(Long id);
    void update(Pet p);

    List<Pet> loadAll(PetQuery query);

    void dels(ArrayList<Long> ids);

    List<Pet> selectAll();

    Pet findPetById(Long id);
}
