package org.lilacseeking.video.app.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.lilacseeking.video.app.Model.VO.CommonRequestVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: lilacseeking
 * @Date: 2018/10/16 22:56
 * @Description:唯一入口接口
 */
@Api(value = "/entrance", tags = {"芊芊学堂统一入口"})
@RestController
@RequestMapping(value = "/entrance")
public class MainController {
    @RequestMapping(value = "/entrance", method = RequestMethod.POST)
    @ApiOperation(value = "芊芊学堂统一入口", notes = "芊芊学堂统一入口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "CommonRequestVO", value = "CommonRequestVO", required = true, dataType = "CommonRequestVO")
    })
    public void QianCommonEntrance(@RequestBody CommonRequestVO commonRequestVO, HttpServletResponse response){

    }
}
