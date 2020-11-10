package com.ljcx.api.mgdao;

import com.ljcx.api.mgbean.UavInfoMongo;
import com.ljcx.common.base.MongoDbDao;
import org.springframework.stereotype.Repository;

@Repository
public class UavInfoMongoDbDao extends MongoDbDao<UavInfoMongo> {
    @Override
    protected Class<UavInfoMongo> getEntityClass() {
        return UavInfoMongo.class;
    }


}
