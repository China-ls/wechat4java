package weixin.popular.bean.card.code.checkcode;

import com.google.gson.annotations.SerializedName;
import weixin.popular.bean.BaseResult;

/**
 * 投放卡券－核查code接口－响应参数
 *
 * @author Moyq5
 */
public class CodeCheckCodeResult extends BaseResult {

    /**
     * 已经成功存入的code。
     */
    @SerializedName("exist_code")
    private String[] existCode;

    /**
     * 没有存入的code。
     */
    @SerializedName("not_exist_code")
    private String[] notExistCode;

    /**
     * @return 已经成功存入的code
     */
    public String[] getExistCode() {
        return existCode;
    }

    /**
     * @param existCode 已经成功存入的code
     */
    public void setExistCode(String[] existCode) {
        this.existCode = existCode;
    }

    /**
     * @return 没有存入的code
     */
    public String[] getNotExistCode() {
        return notExistCode;
    }

    /**
     * @param notExistCode 没有存入的code
     */
    public void setNotExistCode(String[] notExistCode) {
        this.notExistCode = notExistCode;
    }
}