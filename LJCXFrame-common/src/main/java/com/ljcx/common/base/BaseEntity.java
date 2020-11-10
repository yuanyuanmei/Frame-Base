package com.ljcx.common.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public abstract class BaseEntity<ID extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private ID id;


    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date createTime;

    @TableLogic
    @TableField(value = "is_delete")
    @JSONField(serialize = false)
    private Integer deleteStatus;

    public BaseEntity(Date createTime) {
        this.createTime = createTime;
    }
}
