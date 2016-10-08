package weixin.popular.bean.xmlmessage;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public abstract class XMLMessage {

    private String toUserName;
    private String fromUserName;
    private String msgType;

    protected XMLMessage(String toUserName, String fromUserName, String msgType) {
        super();
        this.toUserName = toUserName;
        this.fromUserName = fromUserName;
        this.msgType = msgType;
    }

    /**
     * 子类自定义XML
     *
     * @return XML
     */
    public abstract String subXML();

    public String toXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>").append("<ToUserName><![CDATA[").append(toUserName).append("]]></ToUserName>")
                .append("<FromUserName><![CDATA[").append(fromUserName).append("]]></FromUserName>")
                .append("<CreateTime>").append(System.currentTimeMillis() / 1000).append("</CreateTime>")
                .append("<MsgType><![CDATA[").append(msgType).append("]]></MsgType>")
                .append(subXML())
                .append("</xml>");
        return sb.toString();
    }

    public boolean outputStreamWrite(OutputStream outputStream) {
        try {
            outputStream.write(toXML().getBytes("utf-8"));
            outputStream.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean outputStreamWrite(OutputStream outputStream, WXBizMsgCrypt bizMsgCrypt) {
        if (bizMsgCrypt != null) {
            try {
                String outputStr = bizMsgCrypt.encryptMsg(toXML(), System.currentTimeMillis() + "", UUID.randomUUID().toString());
                outputStream.write(outputStr.getBytes("utf-8"));
                outputStream.flush();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (AesException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            return outputStreamWrite(outputStream);
        }
    }
}
