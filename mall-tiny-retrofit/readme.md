
###HTTP客户端工具Retrofit，你只需声明接口就可发起HTTP请求，无需进行连接、结果解析之类的重复操作
### 未登录，远程接口返回json如下，导致retrofit调用发生如下异常
<p>
@ControllerAdvice
可以通过全局异常处理来重构请求返回结果
</p>
````
{
  "code": 401,
  "data": "Full authentication is required to access this resource",
  "message": "暂未登录或token已经过期"
}
````
````
2022-01-25 17:26:22.253 ERROR 705588 --- [nio-8086-exec-8] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is com.github.lianjiatech.retrofit.spring.boot.exception.RetrofitIOException: Cannot construct instance of `com.macro.mall.tiny.common.api.CommonPage` (although at least one Creator exists): no String-argument constructor/factory method to deserialize from String value ('Full authentication is required to access this resource')
 at [Source: (okhttp3.ResponseBody$BomAwareReader); line: 1, column: 20] (through reference chain: com.macro.mall.tiny.common.api.CommonResult["data"]), request=Request{method=GET, url=http://localhost:8088/brand/list?pageNum=1&pageSize=3, tags={class retrofit2.Invocation=com.macro.mall.tiny.remote.PmsBrandApi.list() [1, 3]}}] with root cause

com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot construct instance of `com.macro.mall.tiny.common.api.CommonPage` (although at least one Creator exists): no String-argument constructor/factory method to deserialize from String value ('Full authentication is required to access this resource')
 at [Source: (okhttp3.ResponseBody$BomAwareReader); line: 1, column: 20] (through reference chain: com.macro.mall.tiny.common.api.CommonResult["data"])

````
通过拦截器获取response对象，从而获取响应结果。
如果响应结果code为非200，则通过抛出自定义异常，
从而避免远程接口返回值类型无法转换指定类型抛出的json异常，
导致无法明确异常根源导致异常混乱。
对于当前应用controller调用，则应该在全局异常处理中加入该异常的处理，
定时任务调用，抛出该自定义异常后，打印相应记录或者记录db即可。

````

https://mp.weixin.qq.com/s?__biz=MzU1Nzg4NjgyMw==&mid=2247497242&idx=1&sn=f433ff14184980096fbb8cc8ff41fe68&chksm=fc2c4a12cb5bc3043ea306be3bed66f961b555275ff9debdff1bd0c13c230804c1d62ba45ef9&scene=178&cur_album_id=1918334279751663626#rd