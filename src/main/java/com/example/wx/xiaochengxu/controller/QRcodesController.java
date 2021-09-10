package com.example.wx.xiaochengxu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;


public class QRcodesController {

    private static String appid="wx8074527d7a1ffe14";
    private static String secret="e3823c3b860ec0678d727c69996d2953";

    public static void main(String[] args) throws Exception {
        String accessToken = postToken();
        System.out.println("获取的accessToken:" + accessToken);
        getminiqrQr("0/17613", accessToken);
    }

    public static String postToken() throws Exception {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
        URL url = new URL(requestUrl);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 设置通用的请求属性
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        // 得到请求的输出流对象
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes("");
        out.flush();
        out.close();
        // 建立实际的连接
        connection.connect();
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = null;
        if (requestUrl.contains("nlp"))
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));
        else
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String result = "";
        String getLine;
        while ((getLine = in.readLine()) != null) {
            result += getLine;
        }
        in.close();
        JSONObject jsonObject = JSON.parseObject(result);
        String accesstoken = jsonObject.getString("access_token");
        return accesstoken;
    }

    /**
     * 生成带参小程序二维码
     *
     * @param sceneStr    参数
     * @param accessToken token
     */
    public static Map<String, Object> getminiqrQr(String sceneStr, String accessToken) {
        Map<String, Object> retMap = null;
        try {
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");// 提交模式
            httpURLConnection.setRequestProperty("Content-Type", "application/x-javascript; charset=UTF-8");
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", sceneStr);
            paramJson.put("page", "pages/index/main");
            paramJson.put("width", 430);
            paramJson.put("auto_color", true);

            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            /*ByteArrayOutputStream os = new ByteArrayOutputStream();*/
            OutputStream os = new FileOutputStream(new File("C:\\Users\\zhangkun\\Pictures\\test.jpg"));
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
//            // 上传到oss
//            String fileName = CurrentTimeMillisId.next() + ".jpg";
//            String urls = createFilePath(fileName, sourceType);
//            InputStream is = new ByteArrayInputStream(os.toByteArray());
//            String miniCodeUrl = ossFileService.uploadFile(is, urls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retMap;
    }

}
