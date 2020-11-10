package com.ljcx.framework.sys.service.impl;

import com.ljcx.common.config.Global;
import com.ljcx.common.config.ServerConfig;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.common.utils.StringUtils;
import com.ljcx.common.utils.file.FileUploadUtils;
import com.ljcx.common.utils.file.FileUtils;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljcx.framework.sys.service.IGenerator;

import com.ljcx.framework.sys.dao.SysFileDao;
import com.ljcx.framework.sys.beans.SysFileBean;
import com.ljcx.framework.sys.service.SysFileService;
import com.ljcx.framework.sys.dto.SysFileDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
@Slf4j
public class SysFileServiceImpl extends ServiceImpl<SysFileDao, SysFileBean> implements SysFileService {

    @Autowired
    private IGenerator generator;

    @Autowired
    private SysFileDao sysFileDao;

    @Autowired
    private ServerConfig serverConfig;

    @Override
    public IPage<SysFileBean> pageList(SysFileDto sysFileDto) {
        IPage<SysFileBean> page = new Page<>();
        page.setCurrent(sysFileDto.getPageNum());
        page.setSize(sysFileDto.getPageSize());
        return sysFileDao.pageList(page,sysFileDto);
    }

    @Override
    @Transactional
    public SysFileBean upload(MultipartFile file) throws IOException {
        //上传文件路径
        String filePath = Global.getUploadPath();
        // 上传并返回新文件名称
        String lineName = FileUploadUtils.upload(filePath, file);
        String url = serverConfig.getUrl() + lineName;
        // 基础文件路径
        String baseFilePath = filePath + "/" + lineName.substring(StringUtils.indexOf(lineName,"2"));
        // 文件类型
        String suffix = FileUploadUtils.getExtension(file);
        // 基础文件路径前缀（不包括文件名称）
        String baseFilePathStuff =  baseFilePath.substring(0,baseFilePath.lastIndexOf("/"));

        SysFileBean bean = new SysFileBean();

        if(suffix.equals("jpg") || suffix.equals("png")){
            //图片名称
            String jpgName =  baseFilePath.substring(baseFilePath.lastIndexOf("/")+1,baseFilePath.lastIndexOf("."));
            String thumbFilePath = baseFilePathStuff+"/thumb_"+jpgName+".jpg";
            Thumbnails.of(baseFilePath).size(320, 180).toFile(thumbFilePath);
            bean.setThumbPath(thumbFilePath);
            //thumbUrl
            bean.setThumbUrl(url.substring(0,url.lastIndexOf("/"))+"/thumb_"+jpgName+".jpg");
        }

        // 数据持久化

        bean.setFileName(file.getOriginalFilename());
        bean.setFilePath(filePath + "/" + lineName.substring(StringUtils.indexOf(lineName,"2")) );
        bean.setSize(file.getSize());
        bean.setSuffix(suffix);
        bean.setUrl(url);
        sysFileDao.insert(bean);
        return bean;
    }

    @Override
    public ResponseInfo del(SysFileDto fileDto) {
        List<SysFileBean> sysFileBeans = sysFileDao.selectBatchIds(fileDto.getIds());
        sysFileBeans.stream().forEach(fileBean -> {
            FileUtils.deleteFile(fileBean.getFilePath());
        });
        sysFileDao.deleteByIds(fileDto.getIds());
        return new ResponseInfo().success("删除成功");
    }

}
