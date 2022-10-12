package com.tsone.vuespring.security;

import com.tsone.vuespring.dto.MUserDto;
import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;

public class LoginUser extends User {

    private MUserDto user;

    public MUserDto getUser() {
        return this.user;
    }

    public LoginUser(MUserDto user) {
        super(user.getMailAddress(), user.getPassword(), new ArrayList<>());
        this.user = user;
    }
}
