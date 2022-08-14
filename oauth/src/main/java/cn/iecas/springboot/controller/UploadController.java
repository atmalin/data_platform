package cn.iecas.springboot.controller;

import cn.iecas.springboot.framework.config.properties.SpringBootMasterProperties;
import cn.iecas.springboot.framework.log.annotation.Module;
import cn.iecas.springboot.framework.log.annotation.OperationLog;
import cn.iecas.springboot.framework.log.enums.OperationLogType;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.framework.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 上传控制器
 *
 * @author geekidea
 * @date 2019/8/20
 * @since 1.2.1-RELEASE
 */
@Slf4j
@RestController
@RequestMapping("/upload")
@Module("system")
@Api(value = "文件上传", tags = {"文件上传"})
public class UploadController {

    @Autowired
    private SpringBootMasterProperties springBootMasterProperties;

    /**
     * 上传单个文件
     * @return
     */
    @PostMapping
    @OperationLog(name = "上传单个文件", type = OperationLogType.UPLOAD)
    @ApiOperation(value = "上传单个文件", response = ApiResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true,dataType = "__file"),
            @ApiImplicitParam(name = "directory", value = "目录 avatar:头像",required = true)
    })
    public ApiResult<String> upload(@RequestParam("file") MultipartFile multipartFile,
                                    @RequestParam("directory") String directory) throws Exception {
        log.info("multipartFile = " + multipartFile);
        log.info("ContentType = " + multipartFile.getContentType());
        log.info("OriginalFilename = " + multipartFile.getOriginalFilename());
        log.info("Name = " + multipartFile.getName());
        log.info("Size = " + multipartFile.getSize());
        log.info("directory = " + directory);

        // 上传文件，返回保存的文件名称
        String saveFileName = UploadUtil.upload(springBootMasterProperties.getUploadPath() + directory + "/", multipartFile, originalFilename -> {
            // 文件后缀
            String fileExtension = FilenameUtils.getExtension(originalFilename);
            // 这里可自定义文件名称，比如按照业务类型/文件格式/日期
            String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssS")) + RandomStringUtils.randomNumeric(6);
            String fileName = dateString + "." + fileExtension;
            return fileName;
        });

        // 上传成功之后，返回访问路径，请根据实际情况设置

        String fileAccessPath = springBootMasterProperties.getResourceAccessUrl() + saveFileName;
        log.info("fileAccessPath:{}", fileAccessPath);

        return ApiResult.success(fileAccessPath);
    }

}
