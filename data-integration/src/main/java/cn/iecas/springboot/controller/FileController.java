package cn.iecas.springboot.controller;

import cn.iecas.springboot.bean.FileInfoBean;
import cn.iecas.springboot.framework.log.annotation.Module;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.service.FileService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.annotations.TypeDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dragon
 * @since 2022/8/16 14:08
 * TODO  1.分片上传, 2.分文件系统上传
 */
@Api("文件上传管理")
@Slf4j
@RestController
@Module("FileSystem")
@RequestMapping("/file-system/file")
@CrossOrigin(value = "*", maxAge = 3600)
@TypeDef(name = "json", typeClass = JsonStringType.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class FileController{

    @Autowired
    private FileService fileService;

    @GetMapping("/check-name")
    @ApiOperation("文件重名情况确认")
    protected ApiResult<Boolean> checkName(@RequestParam("fileName") String fileName, @RequestParam("type") Integer type){
        return fileService.checkName(fileName, type);
    }

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    protected ApiResult<String> upload(@RequestParam("file") MultipartFile multipartFile,
                                       @RequestParam("fileInfo.name") String fileName,
                                       @RequestParam("fileInfo.size") Long fileSize,
                                       @RequestParam("fileInfo.type") String fileType,
                                       @RequestParam("fileHash") String fileHash,
                                       @RequestParam("sliceTotal") Integer sliceTotal,
                                       @RequestParam("force") Boolean force,
                                       @RequestParam("type") Integer type,
                                       @RequestParam("description") String description,
                                       @RequestParam("filePath") String filePath,
                                       @RequestHeader("userid") String userId
                                       ) throws Exception {
        // 打印文件信息
        uploadLogPrint(multipartFile);

        FileInfoBean fileInfoBean = new FileInfoBean(
                DigestUtils.sha256Hex((userId+fileHash)).substring(32),
                userId,
                fileHash,
                fileName,
                fileSize,
                multipartFile.getContentType(),
                1,
                type,
                "",
                "",
                force,
                FilenameUtils.getExtension(fileName),
                "",
                "",
                description,
                ""
        );

        //不分片上传时
            try {
                if(sliceTotal == 1){
                    switch (type){
                        case 1:
                            return fileService.upload2LocalFileSystem(multipartFile, fileInfoBean);
                        case 11:
                            return fileService.upload2SFTP(multipartFile, fileInfoBean);
                        case 12:
                            return fileService.upload2HDFS(multipartFile, fileInfoBean);
                        default:
                            return ApiResult.fail("不支持的文件系统类型");
                    }
                }else{
                    // todo
                    return ApiResult.fail("todo");
                }

            }catch (Exception e){
                e.printStackTrace();
                return ApiResult.fail("上传失败");
            }
    }

    private void uploadLogPrint(MultipartFile multipartFile) {
        log.info("multipartFile = " + multipartFile);
        log.info("ContentType = " + multipartFile.getContentType());
        log.info("OriginalFilename = " + multipartFile.getOriginalFilename());
        log.info("Name = " + multipartFile.getName());
        log.info("Size = " + multipartFile.getSize());
    }
}
