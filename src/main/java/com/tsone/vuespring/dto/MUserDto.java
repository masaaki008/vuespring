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
}
