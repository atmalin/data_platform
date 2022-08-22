package cn.iecas.springboot.controller;

import cn.iecas.springboot.bean.DiFetchTypeBean;
import cn.iecas.springboot.dao.TypeDao;
import cn.iecas.springboot.framework.log.annotation.Module;
import cn.iecas.springboot.framework.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dragon
 * @since 2022/08/15
 */
@Api("数据接入支持接入类型管理")
@RestController
@Module("DiType")
@RequestMapping("/di/type")
@CrossOrigin(value = "*", maxAge = 3600)
public class TypeController{

    @Autowired
    private TypeDao typeDao;

    @GetMapping("/fetch/all")
    @ApiOperation("获取数据接入支持类型信息")
    protected ApiResult<List<DiFetchTypeBean>> getOne() {
        List<DiFetchTypeBean> diFetchTypeBeanList = typeDao.findAll();
        return ApiResult.success(diFetchTypeBeanList, "成功");
    }
}
