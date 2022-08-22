package cn.iecas.springboot.bean;

import cn.iecas.springboot.framework.common.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author dragon
 * @since 2022/8/16 10:31
 */
@Data
@Entity
@Accessors(chain = true)
@ApiModel("文件信息类")
@Table(name="file_system_file_info")
public class FileInfoBean extends BaseBean {

    @Id
    @Column(length = 32)
    @ApiModelProperty("文件id, 用户ID + file_hash 再hash")
    private String fileId;

    @Column(name = "user_id", length = 36)
    @ApiModelProperty("用户id")
    private String userId;

    @Column(name = "file_hash", length = 32)
    @ApiModelProperty("文件hash码")
    private String fileHash;

    @Column(name = "name", length = 128)
    @ApiModelProperty("文件名称")
    private String name;

    @Column(name = "size")
    @ApiModelProperty("文件大小")
    private Long size;

    @Column(name = "mime_type", length = 128)
    @ApiModelProperty("多媒体文件格式MIME")
    private String mimeType;

    @Column(name = "status")
    @ApiModelProperty("状态: 0: 正在上传, 1: 上传完成, 2: 删除")
    private Integer status;

    @Column(name = "type")
    @ApiModelProperty("文件系统类型: 1(本地目录), 11 sftp, 12 hdfs")
    private Integer type;

    @Column(name = "file_path")
    @ApiModelProperty("文件上传目标父路径")
    private String filePath;

    @Column(name = "full_path")
    @ApiModelProperty("文件上传目标全路径")
    private String fullPath;

    @Column(name = "file_force", columnDefinition = "TINYINT", length = 1)
    @ApiModelProperty("是否强制上传, 为true时, 无论文件是否存在都上传一份")
    private Boolean fileForce;

    @Column(name = "file_suffix",length = 128)
    @ApiModelProperty("扩展字段")
    private String fileSuffix;

    //暂未用到的字段

    @Column(length = 32)
    @ApiModelProperty("租户id")
    private String tenantId;

    @Column(length = 32)
    @ApiModelProperty("项目id")
    private String projectId;

    @Column(length = 128)
    @ApiModelProperty("文件描述")
    private String description;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    @ApiModelProperty("文件连接信息")
    private String fileConnection;


    public FileInfoBean(String fileId, String userId, String fileHash, String name, Long size, String mimeType, Integer status, Integer type, String filePath, String fullPath, Boolean fileForce, String fileSuffix, String tenantId, String projectId, String description, String fileConnection) {
        this.fileId = fileId;
        this.userId = userId;
        this.fileHash = fileHash;
        this.name = name;
        this.size = size;
        this.mimeType = mimeType;
        this.status = status;
        this.type = type;
        this.filePath = filePath;
        this.fullPath = fullPath;
        this.fileForce = fileForce;
        this.fileSuffix = fileSuffix;
        this.tenantId = tenantId;
        this.projectId = projectId;
        this.description = description;
        this.fileConnection = fileConnection;
    }

    public FileInfoBean() {
    }
}
