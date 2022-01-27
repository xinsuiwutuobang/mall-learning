package com.macro.mall.tiny.component;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BaseGlobalInterceptor;
import com.macro.mall.tiny.exception.RetrofitException;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.RealResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * 全局拦截器，给请求添加source头
 * Created by macro on 2022/1/19.
 */
@Component
public class SourceInterceptor extends BaseGlobalInterceptor {
    @Override
    protected Response doIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newReq = request.newBuilder()
                .addHeader("source", "retrofit")
                .build();
        Response proceed = chain.proceed(newReq);
        int code = proceed.code();
        ResponseBody responseBody = proceed.body();
        MediaType mediaType = responseBody.contentType();
        Charset charset = mediaType.charset(StandardCharsets.UTF_8);
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.getBuffer();
        String ret = buffer.clone().readString(charset);
        System.out.println(ret);
        JSONObject jsonObject = JSONUtil.parseObj(ret);
        int retCode = jsonObject.getInt("code");
        if (retCode != 200) {
            String retmessage = jsonObject.getStr("message");
            //String retdata = jsonObject.getStr("data");
            throw new RetrofitException(retmessage);
        }
        return proceed;
    }
}
