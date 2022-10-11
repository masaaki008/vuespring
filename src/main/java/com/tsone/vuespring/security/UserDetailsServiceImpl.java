package com.tsone.vuespring.security;

import com.tsone.vuespring.dto.MUserDto;
import com.tsone.vuespring.repository.MUserRepository;
import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MUserRepository userRepository;

    public UserDetailsServiceImpl(MUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mailAddress) throws UsernameNotFoundException {
        try {
            MUserDto user = this.userRepository.findByMailAddress(mailAddress);
            return new User(user.getMailAddress(), user.getPassword(), new ArrayList<>());
        } catch (Exception e) {
            throw new UsernameNotFoundException("メールアドレスもしくはパスワードが違います");
        }
    }
}
