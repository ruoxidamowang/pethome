package com.ruoxi.gm.controller;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.ruoxi.basic.common.Result;
import com.ruoxi.gm.domain.Pet;
import com.ruoxi.gm.query.PetQuery;
import com.ruoxi.gm.service.IPetService;
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
@RequestMapping("/pet")
@Api(tags = "宠物信息接口")
public class PetController {
    @Resource
    private IPetService service;

    @Resource
    private FastFileStorageClient fastFileStorageClient;

    @ApiOperation(value = "宠物信息添加或修改",notes = "有id则为修改，没有id则为添加")
    @PutMapping
    private Result addOrUpdate(@RequestBody Pet pet) {
        if (pet.getId() != null) {
            try {
                service.update(pet);
                return Result.me().setMsg("修改成功");
            } catch (Exception e) {
                return Result.me(false).setMsg("修改失败-" + e.getCause());
            }
        } else {
            try {
                service.add(pet);
                return Result.me().setMsg("添加成功");
            } catch (Exception e) {
                return Result.me(false).setMsg("添加失败-" + e.getCause());
            }
        }
    }

    @ApiOperation("根据id删除宠物信息")
    @DeleteMapping("delPet")
    private Result delete(@RequestParam Long id/*,@RequestParam String path*/) {
        try {
            service.del(id);
//            fastFileStorageClient.deleteFile("http://34.80.47.178/"+path);
            return Result.me().setMsg("删除成功！");
        } catch (Exception e) {
            return Result.me(false).setMsg("删除失败-" + e.getCause());
        }
    }

    @ApiOperation("根据id批量删除宠物信息")
    @DeleteMapping
    private Result deletes(@RequestBody ArrayList<Long> ids) {
        try {
            service.removeAll(ids);
            return Result.me().setMsg("批量删除成功");
        } catch (Exception e) {
            return Result.me(false).setMsg("批量删除失败-" + e.getCause());
        }
    }

    @ApiOperation("分页查询宠物信息")
    @PostMapping
    private Result findByPage(@RequestBody PetQuery query) {
        return Result.me(service.findByPage(query));
    }

    @ApiOperation("根据ID查询宠物信息")
    @GetMapping("{id}")
    private Result findById(@PathVariable Long id) {
        return Result.me(service.findPetById(id));
    }

//    @ApiOperation("查询所有宠物信息信息")
//    @GetMapping
//    private Result findAll() {
//        return Result.me(service.findAllShop());
//    }

//    @ApiOperation("宠物信息logo上传")
//    @PostMapping("upload")
//    private Result logoUpload(@RequestBody MultipartFile file) {
//        String suffix = file.getOriginalFilename().split("[.]")[1];
//        try {
//            StorePath path =
//                    fastFileStorageClient.uploadFile(file.getInputStream(),
//                            file.getSize(),
//                            suffix,
//                            null);
//            if (path!=null){
//                return Result.me(path).setMsg("上传成功！");
//            }
//            return Result.me(false).setMsg("上传失败！");
//        } catch (IOException e) {
//            return Result.me(false).setMsg("上传失败！"+e.getCause());
//        }
//    }

//    @ApiOperation("宠物信息logo删除")
//    @DeleteMapping("delLogo")
//    private Result delLogo(@RequestBody String path) {
//        try {
//            fastFileStorageClient.deleteFile("http://34.80.47.178/"+path);
//            return Result.me().setMsg("删除成功！");
//        } catch (Exception e) {
//            return Result.me(false).setMsg("删除失败！"+e.getCause());
//        }
//    }
}
