package com.example.wx.xiaochengxu.controller;

import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@RestController
public class XcxController {

    /*
     * 登陆公众平台测试账号系统   测试公众号消息推送接口
     */
    @GetMapping("/push")
    public void push() {
        //配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId("wxabb1b7910c1927dd");
        wxStorage.setSecret("eb09a046fbc48b6d60ba044844e73d17");
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);

        //推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser("oeAC66bZTd6yFKSTAFw6mzHTS88Q")//要推送的用户openid
                .templateId("nIWyXb2UFVG-KUGd6T3PdPr11uoBdFrgwJBK_rsLhMc")//模版id
                .url("https://30paotui.com/")//点击模版消息要访问的网址
                .build();
        //如果是正式版发送模版消息，这里需要配置你的模板数据
        templateMessage.addData(new WxMpTemplateData("first", "测试参数1", "#FF00FF"))
                .addData(new WxMpTemplateData("keyword1", "测试参数2", "#A9A9A9"))
                .addData(new WxMpTemplateData("keyword2", "测试参数3", "#FF00FF"));
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }

    }
}
