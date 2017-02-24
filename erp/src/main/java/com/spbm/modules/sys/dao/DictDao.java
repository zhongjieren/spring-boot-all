package com.spbm.modules.sys.dao;

import java.util.List;

import com.spbm.common.persistence.CrudDao;
import com.spbm.common.persistence.annotation.MyBatisDao;
import com.spbm.modules.sys.domain.Dict;

/**
 * 字典DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);
	
}
