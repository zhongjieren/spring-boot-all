package com.spbm.modules.sys.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spbm.common.web.BaseController;

@Controller
public class IndexController extends BaseController{
	
	/**
	 * Go Index
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value={"", "${application.adminPath}/", "${application.adminPath}/index"})
	public String index() {
		return "index";
	}
	
	/**
	 * unauthor
	 * @return
	 */ 
	@RequestMapping("${application.adminPath}/unauthor")
	public String unauthor() {
		return "unauthor";
	}
	
	/**
	 * reports
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("${application.adminPath}/reports")
	public String reports() {
		return "reports";
	}
}
