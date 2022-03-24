package com.ruoyi.web.controller.common;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.BASE64DecodedMultipartFile;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.sign.Base64;
import com.ruoyi.framework.config.ServerConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@Api(tags = {"通用请求处理"})
@RestController
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "fileName", value = "文件名称"),
            @ApiImplicitParam(paramType = "query", dataType = "boolean", name = "delete", value = "是否删除"),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = ""),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "")
    })
    @ApiOperation(value = "通用下载请求", notes = "通用下载请求", httpMethod = "GET")
    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.checkAllowDownload(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "file", name = "file", value = "")
    })
    @ApiOperation(value = "通用上传请求", notes = "通用上传请求", httpMethod = "POST")
    @PostMapping("/common/upload")
    public BaseResult uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            HashMap<String, Object> data = new HashMap<>();
            data.put("fileName", fileName);
            data.put("url", url);
            return BaseResult.success(data);
        } catch (Exception e) {
            return BaseResult.error(e.getMessage());
        }
    }

    /**
     * base64图片文件上传
     *
     * @param data base64数据
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "data", value = "base64数据")
    })
    @ApiOperation(value = "base64图片文件上传", notes = "base64图片文件上传", httpMethod = "POST")
    @PostMapping("/common/upload_base64_img")
    public BaseResult uploadFileBase64(@RequestParam("data") String data) {
        String[] d = data.split("base64,");
        String dataPrefix;
        if (d != null && d.length == 2) {
            dataPrefix = d[0];
        } else {
            return BaseResult.error("上传失败,数据不合法");
        }
        if ("data:image/jpeg;".equalsIgnoreCase(dataPrefix)
                || "data:image/jpg;".equalsIgnoreCase(dataPrefix)
                || "data:image/png;".equalsIgnoreCase(dataPrefix)) {
            String[] baseStr = data.split(",");
            byte[] byteImgData = Base64.decode(baseStr[1]);

            for (int i = 0; i < byteImgData.length; ++i) {
                // 调整异常数据
                if (byteImgData[i] < 0) {
                    byteImgData[i] += 256;
                }
            }
            BASE64DecodedMultipartFile file = new BASE64DecodedMultipartFile(byteImgData, baseStr[0]);
            HashMap<String, Object> dataMap = new HashMap<>();
            try {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(RuoYiConfig.getUploadPath(), file);
                String url = serverConfig.getUrl() + fileName;
                dataMap.put("fileName", fileName);
                dataMap.put("url", url);
            } catch (IOException e) {
                return BaseResult.error(e.getMessage());
            }
            return BaseResult.success(dataMap);
        } else {
            return BaseResult.error("上传失败,上传图片格式不合法");
        }
    }

    /**
     * 本地资源通用下载
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "resource", value = ""),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "")
    })
    @ApiOperation(value = "本地资源通用下载", notes = "本地资源通用下载")
    @RequestMapping("/common/download/resource")
    public void resourceDownload(String resource, HttpServletResponse response) {
        try {
            if (!FileUtils.checkAllowDownload(resource)) {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = RuoYiConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }
}
