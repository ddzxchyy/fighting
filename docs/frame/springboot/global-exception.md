# Spring Boot 全局异常

本文将介绍如何使用 Spring Boot 实现全局异常的捕捉



## 1. 简介

项目中可能会有意想不到的错误出现，如果不捕捉，将直接暴露给用户。本文通过 @RestControllerAdvice 和 @ExceptionHandler 处理全局异常。



## 2. 快速入门

### 2.1 环境准备

添加 web 依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```



### 2.2 配置

配置异常捕捉类

```java
/**
 * 全局异常处理
 *
 * @author jzq
 * @date 2020-10-12
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	// 拦截所有异常, 这里只是为了演示，一般情况下一个方法特定处理一种异常
    @ExceptionHandler(value = Exception.class)/
    public R exceptionHandler(Exception e) {
        return R.error(e.getLocalizedMessage());
    }
}

```



添加测试方法

```java
RequestMapping("/api/test/exception")
@RestController
public class ExceptionTestController {

    @RequestMapping("/illegal")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R IllegalArgumentExceptionTest(){
        throw new IllegalArgumentException("参数错误");
    }

}
```



测试结果如下

```json
{
  "code": 500,
  "msg": "参数错误",
  "data": {
    
  }
}
```



## 3. 进阶

### 3.1 按错误类型捕捉异常

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截所有异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R exceptionHandler(Exception e) {
        return R.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleAccessDeniedException(IllegalArgumentException e) {
        return R.error("参数异常：" + e.getLocalizedMessage());
    }

}
```

当有指定了具体异常的处理方法时，Exception 级别的处理方法将不执行。

通过源码可以知道`ExceptionHandlerMethodResolver.java`中`getMappedMethod`决定了具体被哪个方法处理。

```java
 	@Nullable
    private Method getMappedMethod(Class<? extends Throwable> exceptionType) {
        List<Class<? extends Throwable>> matches = new ArrayList<>();
        //找到可以处理的所有异常信息。mappedMethods 中存放了异常和处理异常的方法的对应关系
        for (Class<? extends Throwable> mappedException : this.mappedMethods.keySet()) {
            if (mappedException.isAssignableFrom(exceptionType)) {
                matches.add(mappedException);
            }
        }
        // 不为空说明有方法处理异常
        if (!matches.isEmpty()) {
            // 按照匹配程度从小到大排序
            matches.sort(new ExceptionDepthComparator(exceptionType));
            // 返回处理异常的方法
            return this.mappedMethods.get(matches.get(0));
        } else {
            return null;
        }
    }
```

从源代码看出：**`getMappedMethod()`会首先找到可以匹配处理异常的所有方法信息，然后对其进行从小到大的排序，最后取最小的那一个匹配的方法(即匹配度最高的那个)。**



### 3.2自定义异常

定义一个类，继承自 `RuntimeException` ，可以看做系统中其他异常类的父类。

```java
@Data
public class BaseRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public BaseRuntimeException(String msg) {
        super(msg);
        this.msg = msg;
    }
    
    public BaseRuntimeException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }
    
    public BaseRuntimeException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

}
```

自定义异常

```java
import java.util.Map;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(Map<String, Object> data) {
        super(ErrorCode.RESOURCE_NOT_FOUND, data);
    }
}
```

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截所有异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R exceptionHandler(Exception e) {
        return R.error(e.getLocalizedMessage());
    }

 	@ExceptionHandler(RRException.class)
	public R handleRRException(RRException e){
		R r = new R();
        r.setCode(e.getCode());
        r.setMsg(e.getMessage());
		
        return r;
	}
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public R handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        R r = new R(request.getRequestURI(), ex);
        return r;
    }

}
```

