package com.spbm.modules.sys.dao;

import org.apache.ibatis.annotations.Select;

import com.spbm.modules.sys.domain.UserInfo;

public interface UserMapper {

	@Select("select * from t_user where account=#{account}")
	UserInfo findByAccount(String account);
}
