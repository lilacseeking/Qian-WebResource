package org.lilacseeking.video.app.Filter;

import org.apache.commons.lang3.StringUtils;
import org.lilacseeking.video.app.Service.Redis.RedisService;
import org.lilacseeking.video.app.Service.ResponseUtil;
import org.lilacseeking.video.infrastructure.enums.ErrorCodeEumn;
import org.lilacseeking.video.infrastructure.Model.DTO.UserBasicInfoDTO;
import org.lilacseeking.video.infrastructure.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/26 00:02
 * @Description: 登录过滤器
 */
public class LoginFilter implements Filter {

    @Autowired
    private RedisService redisService;
    @Autowired
    private ResponseUtil responseUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext context = filterConfig.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        redisService = ctx.getBean(RedisService.class);
        responseUtil = ctx.getBean(ResponseUtil.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,mobile,token");
        String requestUrl = request.getRequestURI();
        //1.判断url是否可以过滤
        String token = request.getHeader("token") != null ? request.getHeader("token") : request.getParameter("token");
        String mobile = request.getHeader("mobile") != null ? request.getHeader("mobile") : request.getParameter("username");
        // URL验证的情况
        if (!UserUtils.filterUrl(requestUrl)) {
            if (StringUtils.isNotBlank(token) && StringUtils.isNotBlank(mobile)) {
                UserBasicInfoDTO userBasicInfoDTO = new UserBasicInfoDTO();
                userBasicInfoDTO.setMobile(mobile);
                UserBasicInfoDTO userResult = redisService.getUserToken(userBasicInfoDTO);
                if (null != userResult && StringUtils.equals(token, userResult.getToken())) {
                    UserUtils.userBasicInfoDTO = userResult;
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    putLoginError(response);
                }
            } else {
                putLoginError(response);
            }
        }
        // URL不验证的情况
        else {
            if (StringUtils.isNotBlank(token) && StringUtils.isNotBlank(mobile)) {
                UserBasicInfoDTO userBasicInfoDTO = new UserBasicInfoDTO();
                userBasicInfoDTO.setMobile(mobile);
                UserBasicInfoDTO userResult = redisService.getUserToken(userBasicInfoDTO);
                if (null != userResult && StringUtils.equals(token, userResult.getToken())) {
                    UserUtils.userBasicInfoDTO = userResult;
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    // 未登录异常返回
    public void putLoginError(HttpServletResponse res) {
        responseUtil.putErrorStr(ErrorCodeEumn.HAVE_NOT_LOGIN_IN.getCode(), ErrorCodeEumn.HAVE_NOT_LOGIN_IN.name(), ErrorCodeEumn.HAVE_NOT_LOGIN_IN.getName());
        responseUtil.writeMessage(res);
    }
}
