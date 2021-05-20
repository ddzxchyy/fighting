# websocket

## nginx 配置 websocket

[原文](https://www.jianshu.com/p/99e09cd896d4)

如果你的项目是https域名访问的，那么你去请求websocket的时候，如果不是wss协议的websocket接口，会报错。所以本文将讲述如何在Nginx中为websocket配置证书。



```conf
  location / {
        
        proxy_set_header Upgrade $http_upgrade;    #支持wss
        proxy_set_header Connection "upgrade";    #支持wss
        proxy_pass api.demoProject.com;
    }
```

