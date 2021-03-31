# 社交登录

[原文](http://www.iocoder.cn/Spring-Security/longfei/Spring-Social-QQ-login-process/)

1. 请求第三方应用
2. 第三方应用将用户请求导向服务提供商
3. 用户同意授权
4. 服务提供商返回code
5. client根据code去服务提供商换取令牌
6. 返回令牌
7. 获取用户信息



<img src="http://dandandeshangni.oss-cn-beijing.aliyuncs.com/github/Spring%20Security/OAuth2-Sequence.png">