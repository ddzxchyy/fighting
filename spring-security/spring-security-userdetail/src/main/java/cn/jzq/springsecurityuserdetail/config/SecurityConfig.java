package cn.jzq.springsecurityuserdetail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 重写 `#configure(HttpSecurity http)` 方法，来配置 URL 的权限控制。
     *
     * @param http h
     * @throws Exception e
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                // 身份认证
                .authorizeRequests()
                // 所有请求
                .anyRequest()
                // 身份认证
                .authenticated();
//
//        http
//                // <X> 配置请求地址的权限
//                .authorizeRequests()
//                // 所有用户可访问
//                .antMatchers("/test/echo").permitAll()
//                // 需要 ADMIN 角色
//                .antMatchers("/test/admin").hasRole("ADMIN")
//                // 需要 NORMAL 角色。
//                .antMatchers("/test/normal").access("hasRole('ROLE_NORMAL')")
//                // 任何请求，访问的用户都需要经过认证
//                .anyRequest().authenticated()
//                .and()
//                // <Y> 设置 Form 表单登录
//                .formLogin()
//                //.loginPage("/login") // 登录 URL 地址
//                .permitAll() // 所有用户可访问
//                .and()
//                // 配置退出相关
//                .logout()
//                // .logoutUrl("/logout") // 退出 URL 地址
//                .permitAll(); // 所有用户可访问
    }

    /**
     * 必须配置密码编码器
     * 没有针 对 “null”PasswordEncoder(映射的密码编码器)
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}