## 环境变量
### idea
创建 http-client.env.json 文件
```json
{
  "development": {
    "host": "localhost:8090",
    "id-value": 12345,
    "username": "",
    "password": "",
    "my-var": "my-dev-value"
  },

  "production": {
    "host": "example.com",
    "id-value": 6789,
    "username": "",
    "password": "",
    "my-var": "my-prod-value"
  }
}
```

###  vscode
```json
"rest-client.environmentVariables": {
    "$shared": {
        "version": "v1"
    },
    "local": {
        "version": "v2",
        "host": "http://localhost:8000",
        "token": "tokentokentokentoken1"
    },
    "prod": {
        "host": "http://api.xxxxxx.com",
        "token": "tokentokentoken2"
    }
}

```
$shared 中的变量表示在所有环境设置中都可以使用的

设置后可通过 `Ctrl + Alt + E`（`Cmd + option + E`）切换环境

系统变量
REST Client 提供了一些自带的系统变量，方便我们直接使用（这里由于我没有使用过 Azure 所以跳过了 Azure 相关的变量，大家可以参考文档使用）

{{$guid}}: 生成一个 UUID

{{$randomInt min max}}: 生成随机整数

{{$timestamp [offset option]}}: 生成时间戳，可以使用类似 {{$timestamp -3 d}} 生成3天前的时间戳，或是使用 {{$timestamp 2 h}} 这样的形式生成2小时后的时间戳

{{$datetime rfc1123|iso8601 [offset option]}}: 生成日期字符串
