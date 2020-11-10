package com.ljcx.code.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.code.beans.ApkVersionBean;
import com.ljcx.code.dao.ApkVersionDao;
import com.ljcx.code.dto.ApkVersionDto;
import com.ljcx.code.service.ApkVersionService;
import com.ljcx.code.vo.ApkVersionVo;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.framework.sys.beans.SysFileBean;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.framework.sys.service.SysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class ApkVersionServiceImpl extends ServiceImpl<ApkVersionDao, ApkVersionBean> implements ApkVersionService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private ApkVersionDao apkVersionDao;

    @Autowired
    private SysFileService fileService;


    @Override
    public ApkVersionVo info() {
        return apkVersionDao.info();
    }

    @Override
    public IPage<ApkVersionBean> pageList(ApkVersionDto apkVersionDto) {
        IPage<ApkVersionBean> page = new Page<>();
        page.setCurrent(apkVersionDto.getPageNum());
        page.setSize(apkVersionDto.getPageSize());
        return apkVersionDao.pageList(page,apkVersionDto);
    }

    @Override
    public ResponseInfo updateApk(ApkVersionDto apkVersionDto) {
        ApkVersionBean bean = generator.convert(apkVersionDto, ApkVersionBean.class);
        apkVersionDao.updateById(bean);
        return new ResponseInfo().success("保存成功");
    }

    @Override
    public ResponseInfo upload(MultipartFile file) throws Exception {
        SysFileBean fileBean = fileService.upload(file);
        fileBean.setMSrc("APK");
        fileBean.setMId(1L);
        fileService.updateById(fileBean);
        ApkVersionBean bean = apkVersionDao.selectById(1);
        bean.setApkName(fileBean.getFileName());
        apkVersionDao.updateById(bean);
        return new ResponseInfo().success("上传成功");
    }
}
