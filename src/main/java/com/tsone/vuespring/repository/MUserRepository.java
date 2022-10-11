package com.tsone.vuespring.repository;

import com.tsone.vuespring.dto.MUserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MUserRepository {
    MUserDto findByMailAddress(String mailAddress);
}
