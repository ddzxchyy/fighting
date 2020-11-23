package cn.jzq.springsecurityconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 实现 AuthenticationManager 认证管理器
     *
     * @param auth a
     * @throws Exception e
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // <X> 使用内存中的 InMemoryUserDetailsManager
                .inMemoryAuthentication()
                // <Y> 不使用 PasswordEncoder 密码编码器
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                // <Z> 配置 admin 用户
                .withUser("admin").password("admin").roles("ADMIN")
                // <Z> 配置 normal 用户
                .and().withUser("normal").password("normal").roles("NORMAL");
    }


    /**
     * 重写 `#configure(HttpSecurity http)` 方法，来配置 URL 的权限控制。
     *
     * @param http h
     * @throws Exception e
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // <X> 配置请求地址的权限
                .authorizeRequests()
                // 所有用户可访问
                .antMatchers("/test/echo").permitAll()
                // 需要 ADMIN 角色
                .antMatchers("/test/admin").hasRole("ADMIN")
                // 需要 NORMAL 角色。
                .antMatchers("/test/normal").access("hasRole('ROLE_NORMAL')")
                // 任何请求，访问的用户都需要经过认证
                .anyRequest().authenticated()
                .and()
                // <Y> 设置 Form 表单登录
                .formLogin()
                //.loginPage("/login") // 登录 URL 地址
                .permitAll() // 所有用户可访问
                .and()
                // 配置退出相关
                .logout()
                // .logoutUrl("/logout") // 退出 URL 地址
                .permitAll(); // 所有用户可访问
    }
}