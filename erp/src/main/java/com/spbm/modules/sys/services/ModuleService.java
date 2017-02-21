package com.spbm.modules.sys.services;

import java.util.List;

import com.spbm.modules.sys.domain.ModuleInfo;

public interface ModuleService {
	/**
	 * 获取角色模块
	 * @param userId
	 * @return
	 */
	List<ModuleInfo> findModuleByUserId(int userId);
}
