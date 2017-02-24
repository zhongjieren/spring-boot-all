package com.spbm.modules.sys.dao;

import com.spbm.common.persistence.TreeDao;
import com.spbm.common.persistence.annotation.MyBatisDao;
import com.spbm.modules.sys.domain.Office;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
}
