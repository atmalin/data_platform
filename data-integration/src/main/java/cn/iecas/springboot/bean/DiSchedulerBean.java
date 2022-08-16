package cn.iecas.springboot.bean;

import cn.iecas.springboot.entity.DataSourceEntity;
import cn.iecas.springboot.framework.common.bean.BaseBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @author dragon
 * @since 2022/8/15 13:41
 */
@Data
@Entity
@Accessors(chain = true)
@ApiModel("数据接入调度类")
@Table(name="di_scheduler")
@TypeDef(name = "json", typeClass = JsonStringType.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class DiSchedulerBean extends BaseBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id")
    private long id;

    @Column(name = "name")
    @ApiModelProperty("调度名称")
    private String name;

    @Column(name = "description")
    @ApiModelProperty("调度描述")
    private String description;

    @Column(name = "user_id")
    @ApiModelProperty("创建者id")
    private String userId;

    @Column(name = "start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("开始时间")
    private Date startTime;

    @Column(name = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("结束时间")
    private Date endTime;

    @Column(name = "enable", columnDefinition = "TINYINT", length = 1)
    @ApiModelProperty("是否生效")
    private Boolean enable;

    @Column(name = "run_again", columnDefinition = "TINYINT", length = 1)
    @ApiModelProperty("出错是否重跑")
    private Boolean runAgain;

    @Column(name = "time_out")
    @ApiModelProperty("超时时间")
    private Integer timeOut;

    @Column(name = "trigger_mode")
    @ApiModelProperty("触发条件:0自动, 1手动")
    private Integer triggerMode;

    @Column(name = "access_mode")
    @ApiModelProperty("接入方式, 全量, CDC等")
    private Integer accessMode;

    @Column(name = "scheduler_cron")
    @ApiModelProperty("调度周期")
    private String schedulerCron;

    @Column(name = "status")
    @ApiModelProperty("状态: -1  运行失败；-2 等待超时；-3 运行超时；-4 手动终止; 0 未运行 >> 1 开始运行 >> 2 等待资源  >> 3 正在执行 >> 4 运行成功")
    private Integer status;

    @Column(name = "scheduler_status")
    @ApiModelProperty("审批状态, 0 未发布, 1 待审核, 2 已拒绝, 3 已通过")
    private Integer schedulerStatus;

    @Column(name = "scheduler_type")
    @ApiModelProperty("unknown")
    private Integer schedulerType;

    @Column(name = "process_id")
    @ApiModelProperty("unknown")
    private Long processId;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    @ApiModelProperty("数据来源")
    private DataSourceEntity source;

    @Type(type = "json")
    @Column(name = "target",columnDefinition = "json")
    @ApiModelProperty("数据去向,前端传的targe")
    private DataSourceEntity targe;
}
