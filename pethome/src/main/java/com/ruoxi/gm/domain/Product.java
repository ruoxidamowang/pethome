package com.ruoxi.gm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 若兮
 * @create 2022-03-07 9:48
 */
@Data
public class Product implements Serializable {
    private Long id;
    private String name;
    private String resources;
    private Double saleprice;
    private Date offsaletime;
    private Date onsaletime;
    private Integer state;
    private String costprice;
    private Date createtime = new Date();
    private Long salecount;
    private ProductDetail productDetail;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getCreatetime() {
        return createtime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
