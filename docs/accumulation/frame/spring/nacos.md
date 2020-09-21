# nacos

nacos 服务发现



## spring-cloud 实现

### 声明依赖

要注意版本之间关系

```xml
 <properties>
        <spring-boot.version>2.1.3.RELEASE</spring-boot.version>
        <spring-cloud.version>Greenwich.SR3</spring-cloud.version>
        <nacos.version>2.1.2.RELEASE</nacos.version>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>
<dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${nacos.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

### 引入依赖

```xml
   <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba.nacos</groupId>
            <artifactId>nacos-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
<dependency>
        <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>Greenwich.SR3</version>
            <type>pom</type>
            <scope>runtime</scope>
</dependency>
```

### 配置 APP 信息

```yml
server:
  port: 8090
spring:
  application:
    name: nacos-discovery

  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848
```

### 开启服务注册与发现

开启 Spring Cloud 的服务注册与发现，这里引入了`spring-cloud-starter-alibaba-nacos-discovery`模块，所以Spring Cloud Common中定义的那些与服务治理相关的接口将使用Nacos的实现

```java
@EnableDiscoveryClient
@SpringBootApplication
public class NacosDiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosDiscoveryServerApplication.class, args);
    }


```

