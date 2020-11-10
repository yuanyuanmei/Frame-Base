package com.ljcx.platform.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
//import org.bson.types.ObjectId;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoutesFlightBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * mongodb id 须为objectId类型
     */
    //private ObjectId id;

    /**
     * 任务记录id
     */
    private Long recordsId;

    /**
     * 航线
     */
    private String routes;

}
