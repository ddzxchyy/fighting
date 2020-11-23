package cn.jzq.springsecurityuserdetail.config;

import cn.jzq.springsecurityuserdetail.user.dao.SysUserDao;
import cn.jzq.springsecurityuserdetail.user.entity.SysUserEntity;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailsService只负责从特定的地方（通常是数据库）加载用户信息
 */
@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final SysUserDao sysUserDao;
//    private final PasswordEncoder passwordEncoder;


    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserEntity userEntity = sysUserDao.getByUsername(username);
        if (userEntity == null) {
            throw new Exception("用户不存在");
        }
//        String psd = passwordEncoder.encode("admin");
        return new User(username, userEntity.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
