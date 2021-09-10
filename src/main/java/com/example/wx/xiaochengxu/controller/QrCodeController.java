package com.example.wx.xiaochengxu.controller;

import com.example.wx.xiaochengxu.model.AccessToken;
import net.sf.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * 小程序飒飒的smallprogram    二维码
 *  原文链接：https://blog.csdn.net/qq_36537546/article/details/88914643
 */
@Slf4j
@RestController
public class QrCodeController {
    private String generateUrl="https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
    private static String appid="wx8074527d7a1ffe14";
    private static String secret="e3823c3b860ec0678d727c69996d2953";

    public static void main(String[] args) {
        PostMiniqrQr();
    }

    public static void PostMiniqrQr() {
        try{
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+getAccessToken(appid,secret));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true); // 打开写入属性
            httpURLConnection.setDoInput(true); // 打开读取属性
            httpURLConnection.setRequestMethod("POST");// 提交方式
            // 不得不说一下这个提交方式转换！！真的坑。。改了好长时间！！一定要记得加响应头
            httpURLConnection.setRequestProperty("Content-Type", "application/x-javascript; charset=UTF-8");// 设置响应头
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", "0/17613"); // 你要放的内容
            paramJson.put("path", "pages/index/index");
            paramJson.put("width", 430); // 宽度
            paramJson.put("auto_color", true);
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            //创建一个空文件
            OutputStream os = new FileOutputStream(new File("C:\\Users\\zhangkun\\Pictures\\test.jpg"));
            //ByteArrayOutputStream os = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1)
            {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
            // 上传云储存
            //InputStream is = new ByteArrayInputStream(os.toByteArray());
            //retMap = UploadUtils.upload(is);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    /**
     *
     * 获取微信access_token
     */
    public static AccessToken getAccessToken(String appid, String secret) {
        AccessToken token = new AccessToken();
        // 访问微信服务器
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret="
                + secret;
        try {
            URL getUrl=new URL(url);
            HttpURLConnection http=(HttpURLConnection)getUrl.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);

            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] b = new byte[size];
            is.read(b);

            String message = new String(b, "UTF-8");
            JSONObject json = JSONObject.fromObject(message);
            token.setAccess_token(json.getString("access_token"));
            token.setExpires_in(new Integer(json.getString("expires_in")));
            System.out.println(token);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }


//    @RequestMapping("/qrCode")
//    public void generateQrCode(){
//        RestTemplate rest = new RestTemplate();
//        InputStream inputStream = null;
//        OutputStream outputStream = null;
//        try {
//            String url =  generateUrl+getAccessToken(appid,secret);
//            Map<String,Object> param = new HashMap<>();
//            param.put("page", "pages/index/index");
//            param.put("width", 430);
//            param.put("auto_color", false);
//            Map<String,Object> line_color = new HashMap<>();
//            line_color.put("r", 0);
//            line_color.put("g", 0);
//            line_color.put("b", 0);
//            param.put("line_color", line_color);
//            log.info("调用生成微信URL接口传参:" + param);
//            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//            HttpEntity requestEntity = new HttpEntity(param, headers);
//            ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
//            log.info("调用小程序生成微信永久小程序码URL接口返回结果:" + entity.getBody());
//            byte[] result = entity.getBody();
//            log.info(Base64.encodeBase64String(result));
//            inputStream = new ByteArrayInputStream(result);
//
//            File file = new File("C:\\Users\\zhangkun\\Pictures\\1.png");
//            if (!file.exists()){
//                file.createNewFile();
//            }
//            outputStream = new FileOutputStream(file);
//            int len = 0;
//            byte[] buf = new byte[1024];
//            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
//                outputStream.write(buf, 0, len);
//            }
//            outputStream.flush();
//        } catch (Exception e) {
//            log.error("调用小程序生成微信永久小程序码URL接口异常",e);
//        } finally {
//            if(inputStream != null){
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(outputStream != null){
//                try {
//                    outputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }



}
