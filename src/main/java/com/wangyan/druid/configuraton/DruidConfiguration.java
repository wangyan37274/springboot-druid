package com.wangyan.druid.configuraton;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.alibaba.druid.wall.WallTableStat;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: wangyan
 * @Date: 2019/10/24
 */
@Configuration
public class DruidConfiguration {

    @Bean(initMethod = "init",destroyMethod = "close")
    public DruidDataSource druid() {
        return new DruidDataSource();
    }

    //servlet配置内置监控
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>();
        registrationBean.setServlet(new StatViewServlet());
        registrationBean.setUrlMappings(Collections.singletonList("/druid/*"));
        registrationBean.setEnabled(true);
        //设置初始化参数
        Map<String, String> initMap = new HashMap<>();
        initMap.put("loginUsername", "admin");
        initMap.put("loginPassword", "123456");
        initMap.put("allow", "");
        initMap.put("deny", "");
        initMap.put("resetEnable", "false");// 禁用HTML页面上的“Reset All”功能
        registrationBean.setInitParameters(initMap);
        return registrationBean;
    }

    //servlet配置过滤拦截
    @Bean
    public FilterRegistrationBean<WebStatFilter> webStatFilter() {
        FilterRegistrationBean<WebStatFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new WebStatFilter());
        registrationBean.setUrlPatterns(Collections.singletonList("/*"));
        registrationBean.setEnabled(true);
        //设置初始化参数
        Map<String, String> initMap = new HashMap<>(1);
        initMap.put("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        registrationBean.setInitParameters(initMap);
        return registrationBean;
    }

    @Bean
    public Filter StatFilter(){
        StatFilter filter = new StatFilter();
        filter.setLogSlowSql(true);
        filter.setSlowSqlMillis(1000);
        return filter;
}

    @Bean
    public Filter WallFilter(){
        return new WallFilter();
    }
}