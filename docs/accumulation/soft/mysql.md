#  mysql 

mysql 5.7 windows 下安装

## 安装

my.ini

不要自己创建 data 目录

```ini
[mysqld]
# 设置3306端口
port=3306
# 设置mysql的安装目录
basedir=C:\\soft\mysql-5.7.25
# 设置mysql数据库的数据的存放目录
datadir=C:\\soft\mysql-5.7.25\data
# 允许最大连接数
max_connections=200
# 允许连接失败的次数。
max_connect_errors=10
# 服务端使用的字符集默认为UTF8
character-set-server=UTF8MB4
# 创建新表时将使用的默认存储引擎
default-storage-engine=INNODB
# 默认使用“mysql_native_password”插件认证
#mysql_native_password
default_authentication_plugin=mysql_native_password
[mysql]
# 设置mysql客户端默认字符集
default-character-set=UTF8MB4
[client]
# 设置mysql客户端连接服务端时默认使用的端口
port=3306

```

1. 微软官网，下载Visual Studio 2013 [文件地址](https://www.microsoft.com/zh-CN/download/details.aspx?id=40784)
2. 在根目录创建 my.ini 文件
3.  以管理员身份运行cmd, 初始化在 bin 目录下  `mysqld --initialize`
4. 安装 mysql 服务  `mysqld --install`
5. 启动 mysql 服务 `net start mysql`



如果出现错误，请用命令 `mysqld --console` 查看日志

比如出现如下错误：

Can't start server: Bind on TCP/IP port: No such file or directory 

Do you already have another mysqld server running on port: 3306 ? 

端口被占用，使用命令 `netstat -aon|findstr "3306"` 查看 3306 端口的情况，找到 pid 使用命令  `taskkill /f /pid 具体的pid` 杀掉进程

## 登录

1. 找到初始密码，在 data/*.err 文件中
2.  登录 mysql -u root -p
3.  修改初始密码 ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';
4.  修改密码 update user set authentication_string=password('root') where user='root';

## 开启远程访问

如果需要远程访问要开启对应的端口，如：3306 端口

```sql
USE mysql;
SELECT User, Authentication_string, Host FROM user;
# 允许指定 ip 访问
GRANT ALL PRIVILEGES ON *.* TO 'myuser'@'192.168.1.110'IDENTIFIED BY 'root' WITH GRANT OPTION;
# 允许所有 ip 访问
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%'IDENTIFIED BY 'root' WITH GRANT OPTION;
FLUSH PRIVILEGES;
# 删除 ip 的访问权限
delete from user where host = '192.168.1.100';
# 禁止 root 远程访问
delete from user where user="root" and host!="localhost";
FLUSH PRIVILEGES;
```

