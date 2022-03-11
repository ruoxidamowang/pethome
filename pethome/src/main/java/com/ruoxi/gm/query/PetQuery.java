package com.ruoxi.gm.query;

import com.ruoxi.basic.query.BaseQuery;
import lombok.Data;

/**
 * @author 若兮
 * @create 2022-03-10 14:01
 */
@Data
public class PetQuery extends BaseQuery {
    private String name;
    private Integer state;
}
