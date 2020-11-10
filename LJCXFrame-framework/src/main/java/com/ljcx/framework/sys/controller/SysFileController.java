package com.ljcx.framework.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.common.config.Global;
import com.ljcx.common.config.ServerConfig;
import com.ljcx.common.constant.Constants;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.common.utils.StringUtils;
import com.ljcx.common.utils.file.FileUploadUtils;
import com.ljcx.common.utils.file.FileUtils;
import com.ljcx.framework.sys.beans.SysFileBean;
import com.ljcx.framework.sys.dto.SysFileDto;
import com.ljcx.framework.sys.service.SysFileService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(value = "文件上传模块模块")
@RestController
@RequestMapping(value = "/sys/file")
@Slf4j
public class SysFileController {

    @Autowired
    private SysFileService fileService;


    /**
     * 分页查询列表
     * @param info
     * info(flyAreaDto} 对象)
     * @return
     */
    @PostMapping("/pageList")
    @RequiresPermissions("sys:file:query")
    public ResponseInfo pageList(@RequestBody String info) {
        SysFileDto fileDto = JSONObject.parseObject(info, SysFileDto.class);
        return new ResponseInfo(fileService.pageList(fileDto));
    }

    /**
     * 通用下载请求
     * @param id
     * @param response
     * @param request
     */
    @GetMapping("/download")
    public void fileDownload(String id, HttpServletResponse response, HttpServletRequest request) throws IOException {
//            if(!FileUtils.isValidFilename(fileName)){
//                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载", fileName));
//            }
            SysFileBean bean = fileService.getById(id);
            String realFileName = System.currentTimeMillis() + bean.getFilePath().substring(bean.getFilePath().indexOf("_") + 1);
            String filePath = bean.getFilePath();

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;filename="+FileUtils.setFileDownloadHeader(request, bean.getFileName()));
            FileUtils.writeBytes(filePath, response.getOutputStream());
    }

    /**
     * 批量删除
     * @param info
     * @return
     */
    @PostMapping("/del")
    public ResponseInfo del(@RequestBody String info)
    {
        SysFileDto fileDto = JSONObject.parseObject(info, SysFileDto.class);
        return fileService.del(fileDto);

    }

    /**
     * 通用上传请求
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public ResponseInfo uploadFile(MultipartFile file) throws Exception{
        return new ResponseInfo(fileService.upload(file));
    }

    /**
     * 本地资源通用下载
     * @param resource
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 本地资源路径
        String localPath = Global.getProfile();
        // 数据库资源地址
        String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
        // 下载名称
        String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, downloadName));
        FileUtils.writeBytes(downloadPath, response.getOutputStream());
    }

}
