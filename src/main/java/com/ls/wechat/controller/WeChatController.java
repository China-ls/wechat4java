package com.ls.wechat.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequestMapping("/wechat")
@Controller
public class WeChatController {
    private Logger LOG = LoggerFactory.getLogger(WeChatController.class);

    @Value("#{wx.token}")
    private String weixinToken;

    @RequestMapping(value = "/msg/recieve", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String recieve(
            @ModelAttribute("signature") String signature,
            @ModelAttribute("timestamp") String timestamp,
            @ModelAttribute("nonce") String nonce,
            @ModelAttribute("echostr") String echostr,
            HttpServletRequest req
    ) {
        try {
            //首次请求申请验证,返回echostr
            if (!StringUtils.isEmpty(echostr)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Aquire token check first.[{}]", echostr);
                }
                return echostr;
            }

            //验证请求签名
            if (!signature.equals(SignatureUtil.generateEventMessageSignature(weixinToken, timestamp, nonce))) {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("The request signature is invalid", weixinToken, timestamp, nonce);
                }
            } else {
                //转换XML
                ServletInputStream inputStream = req.getInputStream();
                EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class, inputStream);

                //创建回复
                XMLMessage xmlTextMessage = new XMLTextMessage(
                        eventMessage.getFromUserName(),
                        eventMessage.getToUserName(),
                        String.format("你好 %s，类型：%s，内容：%s", eventMessage.getToUserName(), eventMessage.getMsgType(), eventMessage.getContent()));
                return xmlTextMessage.toXML();
            }
        } catch (IOException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Wechat msg error.[signature:{},timestamp:{},nonce:{},echostr:{}]", signature, timestamp, nonce, echostr);
            }
        }
        return "";
    }

    /*@RequestMapping(value = "/encrptmsg/recieve", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String recieveEncrpt(
            @ModelAttribute("signature") String signature,
            @ModelAttribute("timestamp") String timestamp,
            @ModelAttribute("nonce") String nonce,
            @ModelAttribute("echostr") String echostr,
            @ModelAttribute("encrypt_type") String encrypt_type,
            @ModelAttribute("msg_signature") String msg_signature,
            HttpServletRequest req,
            HttpServletResponse resp
    ) {
        try {
            WXBizMsgCrypt wxBizMsgCrypt = null;
            //加密方式
            boolean isAes = "aes".equals(encrypt_type);
            if (isAes) {
                try {
                    wxBizMsgCrypt = new WXBizMsgCrypt(encodingToken, encodingAesKey, appId);
                } catch (AesException e) {
                    e.printStackTrace();
                }
            }

            //首次请求申请验证,返回echostr
            if (!StringUtils.isEmpty(echostr)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Aquire token check first.[{}]", echostr);
                }
                return echostr;
            }

            //验证请求签名
            if (!signature.equals(SignatureUtil.generateEventMessageSignature(weixinToken, timestamp, nonce))) {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("The request signature is invalid", weixinToken, timestamp, nonce);
                }
            } else {
                //转换XML
                ServletInputStream inputStream = req.getInputStream();
                EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class, inputStream);

                //创建回复
                XMLMessage xmlTextMessage = new XMLTextMessage(
                        eventMessage.getFromUserName(),
                        eventMessage.getToUserName(),
                        String.format("你好 %s，类型：%s，内容：%s", eventMessage.getToUserName(), eventMessage.getMsgType(), eventMessage.getContent()));
                return xmlTextMessage.toXML();
            }
        } catch (IOException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Wechat msg error.[signature:{},timestamp:{},nonce:{},echostr:{}]", signature, timestamp, nonce, echostr);
            }
        }
        return "";
    }*/

}
