package com.ruoxi.gm.controller;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.ruoxi.basic.common.Result;
import com.ruoxi.gm.domain.SearchMasterMsg;
import com.ruoxi.gm.query.PetSearchQuery;
import com.ruoxi.gm.service.IPetSearchService;
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
@RequestMapping("/petSearch")
@Api(tags = "宠物寻主接口")
public class PetSearchController {
    @Resource
    private IPetSearchService service;

    @Resource
    private FastFileStorageClient fastFileStorageClient;

    @ApiOperation(value = "寻主信息添加或修改",notes = "有id则为修改，没有id则为添加")
    @PutMapping
    private Result addOrUpdate(@RequestBody SearchMasterMsg smm) {
        if (smm.getId() != null) {
            try {
                service.update(smm);
                return Result.me().setMsg("修改成功");
            } catch (Exception e) {
                return Result.me(false).setMsg("修改失败-" + e.getCause());
            }
        } else {
            try {
                service.add(smm);
                return Result.me().setMsg("添加成功");
            } catch (Exception e) {
                return Result.me(false).setMsg("添加失败-" + e.getCause());
            }
        }
    }

    @ApiOperation("根据id删除寻主信息")
    @DeleteMapping("delPetSearch")
    private Result delete(@RequestParam Long id/*,@RequestParam String path*/) {
        try {
            service.del(id);
//            fastFileStorageClient.deleteFile("http://34.80.47.178/"+path);
            return Result.me().setMsg("删除成功！");
        } catch (Exception e) {
            return Result.me(false).setMsg("删除失败-" + e.getCause());
        }
    }

    @ApiOperation("根据id批量删除寻主信息")
    @DeleteMapping
    private Result deletes(@RequestBody ArrayList<Long> ids) {
        try {
            service.removeAll(ids);
            return Result.me().setMsg("批量删除成功");
        } catch (Exception e) {
            return Result.me(false).setMsg("批量删除失败-" + e.getCause());
        }
    }

    @ApiOperation("分页查询寻主信息")
    @PostMapping
    private Result findByPage(@RequestBody PetSearchQuery query) {
        return Result.me(service.findByPage(query));
    }

//    @ApiOperation("查询所有寻主信息信息")
//    @GetMapping
//    private Result findAll() {
//        return Result.me(service.findAllShop());
//    }

//    @ApiOperation("寻主信息logo上传")
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

//    @ApiOperation("寻主信息logo删除")
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
