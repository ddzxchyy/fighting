# 基于微信 code  生成 token

前端传微信 code，后端去根据 code 获取 openId，获取到 openId 了说明 code 是正确的，然后用 oauth2 的密码模式获取一个 token。

