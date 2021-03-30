package com.xtjun.xpForwardSms.common.utils;

import com.xtjun.xpForwardSms.common.constant.Const;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String TITLE = "{{title}}";
    private static final String CONTENT = "{{content}}";
    private static final String REGEX = "(http(s)?://[^/]*/[^/]*)/";
    private static final String TOKEN = "access_token";

    public static boolean custGet(String url, String title, String content) {
        try {
            content = URLEncoder.encode(content, "UTF-8");
            url = url.replace(TITLE, title).replace(CONTENT, content);
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body() != null ? response.body().string() : "";
            XLog.d("cust get successfully: " + result);
            return true;
        } catch (IOException e) {
            XLog.e("cust get error: " + e.getMessage());
            return false;
        }
    }

    public static boolean custPost(String url, String type, String body, String title, String content) {
        try {
            body = body.replace(TITLE, title).replace(CONTENT, content);
            RequestBody requestBody;
            if (Const.POST_TYPE_JSON.equals(type)) {
                requestBody = RequestBody.create(MediaType.parse("application/json"), body);
            } else {
                Map<String, String> jsonMap = JsonUtils.fromJsonToMap(body);
                FormBody.Builder builder = new FormBody.Builder();
                for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }
                requestBody = builder.build();
            }

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body() != null ? response.body().string() : "";
            XLog.d("custom post successfully: " + result);
            return true;
        } catch (IOException e) {
            XLog.e("custom post error: " + e.getMessage());
            return false;
        }
    }

    public static boolean getBark(String url, String title, String content) {
        Matcher m = Pattern.compile(REGEX).matcher(url);
        if (m.find()) {
            url = m.group(1);
        } else {
            return false;
        }
        try {
            content = URLEncoder.encode(content, "UTF-8");
            url = url + "/" + title + "/" + content;
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body() != null ? response.body().string() : "";
            XLog.d("getBark successfully: " + result);
            return true;
        } catch (IOException e) {
            XLog.e("getBark error: " + e.getMessage());
            return false;
        }
    }

    public static boolean postDingTalk(String key, String title, String content) {
        try {
            String url = "https://oapi.dingtalk.com/robot/send?access_token=" + key;
            RequestBody requestBody = RequestBody
                    .create(MediaType.parse("application/json"),
                            "{\"msgtype\":\"text\",\"text\":{\"content\": \"" + title + "\\n" + content + "\"}}"
                    );

            Request request = new Request.Builder().url(url).post(requestBody).build();
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body() != null ? response.body().string() : "";
            XLog.d("postDingTalk successfully: " + result);
            return true;
        } catch (IOException e) {
            XLog.e("postDingTalk error: " + e.getMessage());
            return false;
        }
    }


    public static String getWxcpToken(String corpid, String corpsecret) {
        try {
            String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpid + "&corpsecret=" + corpsecret;
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();//发送请求
            Map<String, String> resultMap = JsonUtils.fromJsonToMap(response.body() != null ? response.body().string() : "");
            XLog.d("getWxcpToken successfully: " + resultMap);
            return resultMap.get(TOKEN);
        } catch (IOException e) {
            XLog.e("getWxcpToken error: " + e.getMessage());
            return null;
        }
    }

    public static boolean postWxcpMsg(String token, String agentid,String touser,String title ,String content) {
        try {
            String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + token;
            RequestBody requestBody = RequestBody
                    .create(MediaType.parse("application/json"),
                            "{\"touser\" : \"" + touser + "\",\"msgtype\" : \"text\",\"agentid\" : " + agentid + ",\"text\" : {\"content\" : \"" + title + "\\n" + content + "\"}}"
                    );

            Request request = new Request.Builder().url(url).post(requestBody).build();
            Response response = client.newCall(request).execute();//发送请求
            String result = response.body() != null ? response.body().string() : "";
            XLog.d("postWxcpMsg successfully: " + result);
            return true;
        } catch (IOException e) {
            XLog.e("postWxcpMsg error: " + e.getMessage());
            return false;
        }
    }
}
