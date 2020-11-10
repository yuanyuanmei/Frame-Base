package com.ljcx.code.vo;

import com.ljcx.framework.sys.beans.SysFileBean;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class EqumentCountVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int type;

    private int count;

}
