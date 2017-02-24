package com.spbm.modules.sys.dao;

import com.spbm.common.persistence.CrudDao;
import com.spbm.common.persistence.annotation.MyBatisDao;
import com.spbm.modules.sys.domain.Log;

/**
 * 日志DAO接口
 */
@MyBatisDao
public interface LogDao extends CrudDao<Log> {

}
