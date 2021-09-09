package com.example.wx.xiaochengxu.controller;


import com.alipay.api.internal.util.codec.Base64;
import com.example.wx.xiaochengxu.model.AccessToken;
import net.sf.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class QrCodeController {
    private String generateUrl="https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
    private String appid="wxabb1b7910c1927dd";
    private String secret="eb09a046fbc48b6d60ba044844e73d17";

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

    @RequestMapping("/qrCode")
    public Map getminiqrQr() {
      RestTemplate rest = new RestTemplate();
      InputStream inputStream = null;
      OutputStream outputStream = null;
      try {
        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+getAccessToken(appid,secret);
        Map<String,Object> param = new HashMap<>();
       // param.put("scene", sceneStr);
        param.put("page", "pages/index/index");
        param.put("width", 430);
        param.put("auto_color", false);
        Map<String,Object> line_color = new HashMap<>();
        line_color.put("r", 0);
        line_color.put("g", 0);
        line_color.put("b", 0);
        param.put("line_color", line_color);
        log.info("调用生成微信URL接口传参:" + param);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        HttpEntity requestEntity = new HttpEntity(param, headers);
        ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
        log.info("调用小程序生成微信永久小程序码URL接口返回结果:" + entity.getBody());
        byte[] result = entity.getBody();
        log.info(Base64.encodeBase64String(result));
        inputStream = new ByteArrayInputStream(result);

        File file = new File("C:\\Users\\zhangkun\\Pictures\\1.png");
        if (!file.exists()){
            file.createNewFile();
        }
        outputStream = new FileOutputStream(file);
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = inputStream.read(buf, 0, 1024)) != -1) {
            outputStream.write(buf, 0, len);
        }
        outputStream.flush();
    } catch (Exception e) {
          log.error("调用小程序生成微信永久小程序码URL接口异常",e);
    } finally {
        if(inputStream != null){
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(outputStream != null){
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    return null;
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

}
