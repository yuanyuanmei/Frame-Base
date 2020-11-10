package com.ljcx.platform.mgdao;

import com.ljcx.common.base.MongoDbDao;
import com.ljcx.platform.beans.UavInfoBean;
import com.ljcx.platform.mgbean.UavInfoMongo;
import com.ljcx.platform.vo.UavInfoVo;
import org.springframework.stereotype.Repository;

@Repository
public class UavInfoMongoDbDao extends MongoDbDao<UavInfoMongo> {
    @Override
    protected Class<UavInfoMongo> getEntityClass() {
        return UavInfoMongo.class;
    }


}
