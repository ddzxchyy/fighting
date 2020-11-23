package cn.jzq.springsecurityuserdetail.user.dao;

import cn.jzq.springsecurityuserdetail.user.entity.SysUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author jzq
 * @date 2020-11-18 14:44:12
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {

    /**
     * 通过 username 获取用户
     * @param username username
     * @return
     */
    SysUserEntity getByUsername(String username);
}
