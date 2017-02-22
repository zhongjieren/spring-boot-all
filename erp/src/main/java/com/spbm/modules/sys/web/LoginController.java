package com.spbm.modules.sys.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spbm.common.security.shiro.bean.UsernamePasswordToken;
import com.spbm.common.security.shiro.config.SystemAuthorizingRealm.Principal;
import com.spbm.common.security.shiro.filter.FormAuthenticationFilter;
import com.spbm.common.utils.HttpRequestUtil;
import com.spbm.common.utils.StringUtils;
import com.spbm.modules.sys.utils.UserUtils;

@Controller
public class LoginController {

	
	/**
	 * 管理登录
	 */
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String login() {
		Principal principal = UserUtils.getPrincipal();
		// 如果已经登录，则跳转到管理首页
		if(principal != null && !principal.isMobileLogin()){
//			return "redirect:" + adminPath;
			return "redirect:/index";
		}
		
		return "login";
	}
	
	/**
	 * Go login
	 * @param request
	 * @return
	 */
	@RequestMapping(value="login", method=RequestMethod.POST)
	public String login(HttpServletRequest request, RedirectAttributes rediect) {
		Principal principal = UserUtils.getPrincipal();
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null){
//			return "redirect:" + adminPath;
			return "redirect:/index";
		}

		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		
		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
//			message = "用户或密码错误, 请重试.";
			rediect.addFlashAttribute("errorText", "您的账号或密码输入错误!");
			return "redirect:/login";
		}

//		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
//		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
//		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
//		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
//		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
//		
		
//		String account = request.getParameter("account");
//		String password = request.getParameter("password");
//		String host = HttpRequestUtil.getHostName();
//		System.out.println("Login username:"+username+";password:"+password);
//		UsernamePasswordToken upt = new UsernamePasswordToken(account, password);
//		Subject subject = SecurityUtils.getSubject();
//		try {
//			subject.login(upt);
//		} catch (AuthenticationException e) {
//			e.printStackTrace();
//			rediect.addFlashAttribute("errorText", "您的账号或密码输入错误!");
//			return "redirect:/login";
//		}
		return "redirect:/index";
	}
	
	/**
	 * Exit
	 * @return
	 */
	@RequestMapping("logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/index";
	}
}
