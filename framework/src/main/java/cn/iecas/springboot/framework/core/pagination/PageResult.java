package cn.iecas.springboot.framework.core.pagination;

import cn.iecas.springboot.framework.common.constant.CommonConstant;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页结果对象
 *
 * @author ch
 * @date 2021-09-29
 */
@Slf4j
@Data
@ApiModel("分页结果对象")
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 4784961132604516495L;

    @ApiModelProperty("总行数")
    @JSONField(name = CommonConstant.PAGE_TOTAL_NAME)
    @JsonProperty(CommonConstant.PAGE_TOTAL_NAME)
    private long total = 0;

    @ApiModelProperty("数据列表")
    @JSONField(name = CommonConstant.PAGE_RECORDS_NAME)
    @JsonProperty(CommonConstant.PAGE_RECORDS_NAME)
    private List<T> records = Collections.emptyList();

    public PageResult() {

    }

    public PageResult(Page<T> page) {
        this.total = page.getTotalElements();
        this.records = page.getContent();
    }

    public PageResult(long total, List<T> records) {
        this.total = total;
        this.records = records;
    }
}
