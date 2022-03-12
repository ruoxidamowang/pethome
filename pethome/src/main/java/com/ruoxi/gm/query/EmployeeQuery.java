package com.ruoxi.gm.query;

import com.ruoxi.basic.query.BaseQuery;
import com.ruoxi.gm.domain.Department;
import com.ruoxi.gm.domain.Role;
import com.ruoxi.gm.domain.Shop;
import lombok.Data;

/**
 * @author 若兮
 * @create 2022-03-12 15:09
 */
@Data
public class EmployeeQuery extends BaseQuery {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private Integer age;
    private Integer state;
    private Department department;
    private Shop shop;
    private Role role;
}
