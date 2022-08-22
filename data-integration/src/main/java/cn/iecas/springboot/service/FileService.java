package cn.iecas.springboot.service;

import cn.iecas.springboot.bean.FileInfoBean;
import cn.iecas.springboot.bean.FileInfoBean;
import cn.iecas.springboot.config.DiProperties;
import cn.iecas.springboot.dao.FileInfoDao;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.framework.util.DateUtil;
import cn.iecas.springboot.framework.util.ExampleUtil;
import cn.iecas.springboot.framework.util.PageableUtil;
import cn.iecas.springboot.framework.util.UploadUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author dragon
 * @since 2022/8/16 15:01
 */
@Slf4j
@Service
public class FileService {

    @Autowired
    private FileInfoDao fileInfoDao;
    @Autowired
    private DiProperties diProperties;

    //确认文件重名情况
    public ApiResult<Boolean> checkName(String fileName, Integer type) {
        if(fileInfoDao.existsByNameAndType(fileName, type)){
            return ApiResult.success(false);
        }else{
            return ApiResult.success(true);
        }
    }

    //上传文件到本地文件系统
    public ApiResult<String> upload2LocalFileSystem(MultipartFile multipartFile, FileInfoBean fileInfoBean) throws Exception {

        String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 上传文件，返回保存的文件名称
        String saveFileName = UploadUtil.upload(diProperties.getUploadPath() + "/" + dateString,
                multipartFile,
                originalFilename -> fileInfoBean.getFileHash());

        return ApiResult.success("成功");
    }

    //上传文件到SFTP文件系统
    public ApiResult<String> upload2SFTP(MultipartFile multipartFile, FileInfoBean fileInfoBean) {
        // TODO
        return ApiResult.success("todo");
    }

    //上传文件到HDFS文件系统
    public ApiResult<String> upload2HDFS(MultipartFile multipartFile, FileInfoBean fileInfoBean) {
        // TODO
        return ApiResult.success("todo");
    }

    
}