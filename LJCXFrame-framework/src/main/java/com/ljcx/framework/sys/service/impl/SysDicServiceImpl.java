
package com.ljcx.framework.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.sys.beans.SysDicBean;
import com.ljcx.framework.sys.beans.SysDicDescBean;
import com.ljcx.framework.sys.dao.SysDicDao;
import com.ljcx.framework.sys.dao.SysDicDescDao;
import com.ljcx.framework.sys.dto.SysDicDto;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.framework.sys.service.SysDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("sysDicService")
public class SysDicServiceImpl extends ServiceImpl<SysDicDao, SysDicBean> implements SysDicService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private SysDicDao sysDicDao;

    @Autowired
    private SysDicDescDao sysDicDescDao;

    @Override
    public IPage<SysDicDescBean> pageList(SysDicDto sysDicDto) {
        IPage<SysDicBean> page = new Page<>();
        page.setCurrent(sysDicDto.getPageNum());
        page.setSize(sysDicDto.getPageSize());
        return sysDicDescDao.pageList(page,sysDicDto);
    }

    @Override
    public List<SysDicDescBean> list(SysDicDto sysDicDto) {
        return sysDicDescDao.pageList(sysDicDto);
    }

    @Transactional
    @Override
    public ResponseInfo save(SysDicDto sysDicDto) {
        if(sysDicDto.getOp().equals("type")){
            SysDicBean bean = generator.convert(sysDicDto, SysDicBean.class);
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("code",sysDicDto.getCode());
            SysDicBean coditionBean = sysDicDao.selectOne(wrapper);
            if(coditionBean != null){
                return new ResponseInfo().failed("编码已存在！");
            }
            if(bean.getId() == null){
                sysDicDao.insert(bean);
            }else{
                SysDicBean byId = sysDicDao.selectById(bean.getId());
                //修改从表
                if(!byId.getCode().equals(bean.getCode())){
                    sysDicDescDao.updateByCode(byId.getCode(),bean.getCode());
                }
                sysDicDao.updateById(bean);

            }
        }else{
            SysDicDescBean bean = generator.convert(sysDicDto, SysDicDescBean.class);
            if(bean.getSeq() == null){
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("code",sysDicDto.getCode());
                wrapper.orderByDesc("seq");
                List<SysDicDescBean> coditionBeanList = sysDicDescDao.selectList(wrapper);
                int id = 1 ;
                if(coditionBeanList != null && coditionBeanList.size()>0){
                    id = coditionBeanList.get(0).getId()+1;
                }
                bean.setId(id);
                sysDicDescDao.insert(bean);
            }else{
                sysDicDescDao.updateById(bean);
            }
        }
        return new ResponseInfo().success("保存成功！");
    }

    @Override
    @Transactional
    public ResponseInfo del(SysDicDto sysDicDto) {
        if(sysDicDto.getOp().equals("type")){
            //先删从表
            SysDicBean bean = sysDicDao.selectById(sysDicDto.getId());
            QueryWrapper delWrapper = new QueryWrapper();
            delWrapper.eq("code",bean.getCode());
            sysDicDescDao.delete(delWrapper);
            //再删除主表
            sysDicDao.deleteById(bean.getId());
        }else{
            sysDicDescDao.deleteById(sysDicDto.getSeq());
        }
        return new ResponseInfo().success("删除成功");
    }
}
