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

