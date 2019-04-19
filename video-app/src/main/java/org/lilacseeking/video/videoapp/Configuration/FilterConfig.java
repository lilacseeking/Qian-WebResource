package org.lilacseeking.video.videoapp.Configuration;

import org.lilacseeking.video.videoapp.Filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: lilacseeking
 * @Date: 2019/3/26 00:07
 * @Description: 过滤器配置
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean registerFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new LoginFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("loginFilter");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

}
