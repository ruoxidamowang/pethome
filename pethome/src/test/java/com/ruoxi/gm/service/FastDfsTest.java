package com.ruoxi.gm.service;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.ruoxi.PetHomeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author 若兮
 * @create 2022-03-01 15:11
 */
@SpringBootTest(classes = PetHomeApplication.class)
@RunWith(SpringRunner.class)
public class FastDfsTest {
    @Resource
    private FastFileStorageClient fastFileStorageClient;

    @Test
    public void upload() throws FileNotFoundException {
        StorePath path = fastFileStorageClient.uploadFile(new FileInputStream("D:\\test\\bizhi.jpg"), 637344, ".jpg", null);
        System.out.println(path);
    }
}
