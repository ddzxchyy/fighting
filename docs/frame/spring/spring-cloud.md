# spring cloud





## 问题解决

###  spring cloud 注册的 ip 不正确

```
spring:
    cloud:
        inetutils:
        	# 忽略网卡
            ignored-interfaces: ['eth.*', 'VMware.*']
            preferred-networks: ['192.168']
            use-only-site-local-interfaces: true
```

