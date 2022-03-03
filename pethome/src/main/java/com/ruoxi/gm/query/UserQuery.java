package com.ruoxi.gm.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 若兮
 * @create 2022-03-02 20:23
 */
@Data
public class UserQuery {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String salt;
    private String password;
    private String repassword;
    private String token;
    private Integer type;
    private Integer state;
    private Integer disable;
    private Integer age;
    private Date createtime = new Date();
    private String headImg;
    private Integer code;
    private Integer logininfo_id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getCreatetime() {
        return createtime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
