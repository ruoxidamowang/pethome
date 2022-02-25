package com.ruoxi.gm.query;

import com.ruoxi.basic.query.BaseQuery;
import com.ruoxi.gm.domain.Department;
import com.ruoxi.gm.domain.Employee;
import lombok.Data;

/**
 * @author 若兮
 * @create 2022-02-21 14:10
 */
@Data
public class DepartmentQuery extends BaseQuery {
    private Long id;
    private String sn;
    private String name;
    private String dirPath;
    private Integer state;
    private Employee manager;
    private Department parent;
}
