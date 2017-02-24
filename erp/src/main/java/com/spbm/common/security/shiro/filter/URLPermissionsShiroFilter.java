package com.spbm.common.security.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.springframework.stereotype.Component;

@Component("urlPermissionsFilter")
public class URLPermissionsShiroFilter extends PermissionsAuthorizationFilter{
//	@Autowired
//	private UserService userService;

//	@Override
//	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
//		String curUrl = getRequestUrl(request);
//		Subject subject = SecurityUtils.getSubject();
//		
//		if(subject.getPrincipal() == null 
//				|| StringUtils.endsWithAny(curUrl, ".js",".css",".html")
//				|| StringUtils.endsWithAny(curUrl, ".jpg",".png",".gif", ".jpeg")
//				|| StringUtils.equals(curUrl, "/unauthor")) {
//			return true;
//		}
////		UserInfo userinfo = (UserInfo)subject.getPrincipal();
//		Principal principal = (Principal)subject.getPrincipal();
//		List<String> urls = userService.findPermissionUrl(principal.getLoginName());
//		
//		return urls.contains(curUrl);
//	}
	
	
	
	
	/**
	 * 获取当前URL+Parameter
	 * @param request	拦截请求request
	 * @return			返回完整URL
	 */
	private String getRequestUrl(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest)request;
		String queryString = req.getQueryString();

		queryString = StringUtils.isBlank(queryString)?"": "?"+queryString;
		return req.getRequestURI()+queryString;
	}
}
