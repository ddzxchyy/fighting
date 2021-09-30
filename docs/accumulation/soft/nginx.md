# nginx



## 配置



### 使用 json 返回异常

`default_type application/json;` 告诉浏览器是 json 类型，不然会下载文件

```conf
 server {
	location ~ ^/get_json {
    	default_type application/json;
      	return 200 '{"code":"0","msg":"nginx json"}';
    }
    
	error_page 403 /respon_403.json;
	location = /respon_403.json {
		default_type application/json;
		return 403 '{"code":"403","data": null, "msg":"Forbidden"}';
	}

    error_page 429 /respon_429.json;
    location = /respon_429.json {
    default_type application/json;
    return 429 '{"code":"429","data": null, "msg":"服务繁忙，请稍后再试"}';
    }
		
	error_page 404 /respon_404.json;
    location = /respon_404.json {
        default_type application/json;
        return 404 '{"code":"404", "data": null, "msg":"Not Found"}';
    }
    
	error_page 500 502 503 504 /respon_500.json;
	location = /respon_500.json {
		default_type application/json;
	    return 500 '{"code":"500","data": null,"msg":"服务器开小差了"}';
	}
}
	
```



## windows 日志切割

通过移动文件然后重命名，每日生成日志



**配置脚本**

```bat
@echo off
rem 取1天之前的日期
echo wscript.echo dateadd("d",-1,date) >%tmp%\tmp.vbs
for /f "tokens=1,2,3* delims=/" %%i in ('cscript /nologo %tmp%\tmp.vbs') do set y=%%i
for /f "tokens=1,2,3* delims=/" %%i in ('cscript /nologo %tmp%\tmp.vbs') do set m=%%j
for /f "tokens=1,2,3* delims=/" %%i in ('cscript /nologo %tmp%\tmp.vbs') do set d=%%k
rem if %m% LSS set m=0%m% if %d% LSS set d=0%d%
echo %y%-%m%-%d%

rem 查看系统正在运行的nginx进程 ::taskkill /F /IM nginx.exe > nul  该行是注释
tasklist /fi "imagename eq nginx.exe"

rem 设置Nginx 位于的盘符
set NGINX_DRIVER=D:

rem 设置 Nginx 的主目录 ::set NGINX_PATH=%NGINX_DRIVER%\nginx  该行是注释
set NGINX_PATH="%NGINX_DRIVER%\soft\work\nginx-1.18.0"

rem 设置 Nginx 的日志目录
set LOG_PATH=%NGINX_PATH%\logs

rem 保留30天日志
set save_days=30

rem 切换到 Nginx 所在的盘符
%NGINX_DRIVER%

rem 切换到logs所在目录
cd %LOG_PATH%

rem 移动文件
move %LOG_PATH%\access.log %LOG_PATH%\access_%y%-%m%-%d%.log
move %LOG_PATH%\error.log %LOG_PATH%\error_%y%-%m%-%d%.log

rem 进入 Nginx 的主目录
cd %NGINX_PATH%

rem 向nginx 发送 reopen 信号以重新打开日志文件，功能与 Linux 平台中的 kill -USR1 一致
nginx -s reopen

rem 删除30天前日志 forfiles /p "%LOGS_PATH%" /s /m *%y%-%m%-%d%.log /d -%save_days% /c "cmd /c del @path"
echo on
```



配置 windows 定时任务

win + R  compmgmt.msc 





## 灰度发布与 ab 测试

https://mp.weixin.qq.com/s/rsYYhbBhxoHM82R1_TKu_g

