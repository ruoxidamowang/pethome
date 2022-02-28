package com.ruoxi.gm.controller;

import com.ruoxi.basic.common.Result;
import com.ruoxi.gm.domain.Shop;
import com.ruoxi.gm.query.ShopQuery;
import com.ruoxi.gm.service.IShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author 若兮
 * @create 2022-02-21 14:43
 */
@RestController
@RequestMapping("/shop")
@Api(tags = "店铺接口")
public class ShopController {
    @Resource
    private IShopService service;

    @ApiOperation(value = "店铺添加或修改",notes = "有id则为修改，没有id则为添加")
    @PutMapping
    private Result addOrUpdate(@RequestBody Shop shop) {
        if (shop.getId() != null) {
            try {
                service.update(shop);
                return Result.me();
            } catch (Exception e) {
                return Result.me(false).setMsg("更新失败-" + e.getCause());
            }
        } else {
            try {
                service.add(shop);
                return Result.me();
            } catch (Exception e) {
                return Result.me(false).setMsg("添加失败-" + e.getCause());
            }
        }
    }

    @ApiOperation("根据id删除店铺")
    @DeleteMapping("/{id}")
    private Result delete(@PathVariable("id") Long id) {
        try {
            service.del(id);
            return Result.me();
        } catch (Exception e) {
            return Result.me(false).setMsg("删除失败-" + e.getCause());
        }
    }

    @ApiOperation("根据id批量删除店铺")
    @DeleteMapping
    private Result deletes(@RequestBody ArrayList<Long> ids) {
        try {
            service.removeAll(ids);
            return Result.me();
        } catch (Exception e) {
            return Result.me(false).setMsg("删除失败-" + e.getCause());
        }
    }

    @ApiOperation("分页查询店铺")
    @PostMapping
    private Result findByPage(@RequestBody ShopQuery query) {
        return Result.me(service.findByPage(query));
    }

    @ApiOperation("查询所有店铺信息")
    @GetMapping
    private Result findAll() {
        return Result.me(service.findAllShop());
    }
}
