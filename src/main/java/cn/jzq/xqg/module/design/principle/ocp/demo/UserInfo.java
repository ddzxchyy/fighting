package cn.jzq.xqg.module.design.principle.ocp.demo;

import lombok.Data;

/**
 * 用户信息
 *
 * @author jzq
 * @date 2019-12-17
 */
@Data
public class UserInfo {
    private long userId;
    private String username;
    private String email;
    private String telephone;
    private long createTime;
    private long lastLoginTime;
    private String avatarUrl;
    private String provinceOfAddress;
    private String cityOfAddress;
    private String regionOfAddress;
    private String detailedAddress;
}