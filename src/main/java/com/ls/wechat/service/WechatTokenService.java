package com.ls.wechat.service;

import com.ls.wechat.entity.wx.WechatToken;

public interface WechatTokenService  {

    WechatToken initWechatTokenWithCache();

    WechatToken initWechatToken();

    WechatToken getWechatToken();

}
