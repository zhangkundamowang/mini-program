package com.example.wx.xiaochengxu.model;

import lombok.Data;

@Data
public class AccessToken {
    private String access_token;//获取到的access_token
    private int expires_in;
}
