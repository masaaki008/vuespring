package com.tsone.vuespring.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class MUserDto implements Serializable {

    private static final long serialVersionUID = -6613576724960488022L;

    private String mailAddress;

    private String password;

    private String userName;

    private int permission;

    /**
     * json形式にした文字列を返却
     *
     * @return
     */
    public String toJsonString() {
        return
            "{" +
                "\"mailAddress\":" + "\"" + this.mailAddress + "\"," +
                "\"password\":" + "\"" + this.password + "\"," +
                "\"userName\":" + "\"" + this.userName + "\"," +
                "\"permission\":" + this.permission +
                "}";
    }

}
