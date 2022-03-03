package com.ruoxi.gm.controller;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.ruoxi.basic.common.Result;
import com.ruoxi.gm.domain.Shop;
import com.ruoxi.gm.query.ShopQuery;
import com.ruoxi.gm.service.IShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
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

    @Resource
    private FastFileStorageClient fastFileStorageClient;

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
    @DeleteMapping("delShop")
    private Result delete(@RequestParam Long id,@RequestParam String path) {
        try {
            service.del(id);
            fastFileStorageClient.deleteFile("http://34.80.47.178/"+path);
            return Result.me().setMsg("删除成功！");
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

    @ApiOperation("店铺logo上传")
    @PostMapping("upload")
    private Result logoUpload(@RequestBody MultipartFile file) {
        String suffix = file.getOriginalFilename().split("[.]")[1];
        try {
            StorePath path =
                    fastFileStorageClient.uploadFile(file.getInputStream(),
                            file.getSize(),
                            suffix,
                            null);
            if (path!=null){
                return Result.me(path).setMsg("上传成功！");
            }
            return Result.me(false).setMsg("上传失败！");
        } catch (IOException e) {
            return Result.me(false).setMsg("上传失败！"+e.getCause());
        }
    }

    @ApiOperation("店铺logo删除")
    @DeleteMapping("delLogo")
    private Result delLogo(@RequestBody String path) {
        try {
            fastFileStorageClient.deleteFile("http://34.80.47.178/"+path);
            return Result.me().setMsg("删除成功！");
        } catch (Exception e) {
            return Result.me(false).setMsg("删除失败！"+e.getCause());
        }

    }
}
