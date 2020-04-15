# keytool

## 使用 keytool 生成证书

https://blog.csdn.net/dwyane__wade/article/details/80350548

**一、为服务器生成证书**

`keytool -genkey -alias tomcat -keypass 123456 -keyalg RSA -keysize 1024 -validity 365 -keystore tomcat.keystore -storepass 123456`



**二、让客户端信任服务器证书**

`keytool -keystore tomcat.keystore -export -alias tomcat -file test1.cer `