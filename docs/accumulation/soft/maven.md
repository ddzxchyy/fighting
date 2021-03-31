# Maven

## dependencyManagement

dependencyManagement 里只是声明依赖，并不自动实现引入，因此子项目需要显示的声明需要用的依赖。如果不在子项目中声明依赖，是不会从父项目中继承下来的；只有在子项目中写了该依赖项，并且没有指定具体版本，才会从父项目中继承该项，并且 version 和 scope 都读取自父 pom。如果子项目中指定了版本号，那么会使用子项目中指定的版本

##  打包指定环境

bootstrap.yml 配置 active

```yml
spring:
  profiles:
    active: @profiles.active@
```

pom.xml 配置环境，<profiles> 位于在<project> 下面

```xml
<profiles>
    <profile>
        <id>dev</id>
        <properties>
            <profiles.active>dev</profiles.active>
        </properties>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
    </profile>
    <profile>
        <id>test</id>
        <properties>
            <profiles.active>test</profiles.active>
        </properties>
    </profile>
    <profile>
        <id>prod</id>
        <properties>
            <profiles.active>prod</profiles.active>
        </properties>
    </profile>
</profiles>
```

配置之后，在 idea 的 maven 栏中会出现 Profiles 选项，可以勾选对应的环境。

打包命令：`mvn clean  package -P prod`