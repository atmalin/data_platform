package cn.iecas.springboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dragon
 * @since 2022/8/16 15:24
 */
@Data
@Component
@ConfigurationProperties(prefix = "di-properties")
public class DiProperties {
    /*
    上传路径
     */
    private String uploadPath;
    /*
    允许上传的文件后缀集合
     */
    private List<String> allowUploadFileExtensions;
    /*
    允许下载的文件后缀集合
     */
    private List<String> allowDownloadFileExtensions;

}
