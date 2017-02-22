package com.spbm.common.security.shiro.config;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.spbm.common.security.shiro.bean.UsernamePasswordToken;
import com.spbm.common.security.shiro.filter.RetryLimitHashedCredentialsMatcher;
import com.spbm.common.utils.SpringContextUtil;
import com.spbm.modules.sys.domain.UserInfo;
import com.spbm.modules.sys.services.UserService;
import com.spbm.modules.sys.utils.UserUtils;

/**
 * 验证用户登录
 * 
 * @author Administrator
 */
@Component("userRealm")
public class SystemAuthorizingRealm extends AuthorizingRealm {
	@Autowired
	private UserService userService;

	public SystemAuthorizingRealm() {
		setName("UserRealm");
//		// 采用MD5加密
//		setCredentialsMatcher(new HashedCredentialsMatcher("md5"));
	}
	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
//		String userName = token.getUsername();
		System.out.println("----->>userInfo="+token.getUsername());
		
		UserInfo userInfo = userService.findByAccount(token.getUsername());
		
		if (userInfo != null) {
			//是否被禁用
			
			//加密方式;
//			byte[] salt = Encodes.decodeHex(userInfo.getPassword().substring(0,16));
			
			//交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
			SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
					userInfo, //用户名
					userInfo.getPassword(), //密码
	                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=account +salt
	                getName()  //realm name
	        );
			return authenticationInfo;
			 //明文: 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
//			SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//					userName, 
//					userInfo.getPassword(), 
//					getName()); 
		}else{
			return null;
		}
	}
	//权限资源角色in
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		UserInfo userinfo = (UserInfo) getAvailablePrincipal(principals);
//		String username = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		userinfo = userService.findByAccount(userinfo.getAccount());
		//add Permission Resources
		info.setStringPermissions(userService.findPermissions(userinfo.getAccount()));
		//add Roles String[Set<String> roles]
		// 添加用户权限
		info.addStringPermission("user");
		//info.setRoles(roles);
		info.addRole("user");
		return info;
	}
	
	
	@Override
	protected void checkPermission(Permission permission, AuthorizationInfo info) {
		authorizationValidate(permission);
		super.checkPermission(permission, info);
	}
	
	@Override
	protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
        		authorizationValidate(permission);
            }
        }
		return super.isPermitted(permissions, info);
	}
	
	@Override
	public boolean isPermitted(PrincipalCollection principals, Permission permission) {
		authorizationValidate(permission);
		return super.isPermitted(principals, permission);
	}
	
	@Override
	protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
            	authorizationValidate(permission);
            }
        }
		return super.isPermittedAll(permissions, info);
	}
	
	/**
	 * 授权验证方法
	 * @param permission
	 */
	private void authorizationValidate(Permission permission){
		// 模块授权预留接口
	}
	
	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private int id; // 编号
		private String loginName; // 登录名
		private String name; // 姓名
		private boolean mobileLogin; // 是否手机登录
		
//		private Map<String, Object> cacheMap;

		public Principal(UserInfo user, boolean mobileLogin) {
			this.id = user.getId();
			this.loginName = user.getAccount();
			this.name = user.getName();
			this.mobileLogin = mobileLogin;
		}

		public int getId() {
			return id;
		}

		public String getLoginName() {
			return loginName;
		}

		public String getName() {
			return name;
		}

		public boolean isMobileLogin() {
			return mobileLogin;
		}

//		@JsonIgnore
//		public Map<String, Object> getCacheMap() {
//			if (cacheMap==null){
//				cacheMap = new HashMap<String, Object>();
//			}
//			return cacheMap;
//		}

		/**
		 * 获取SESSIONID
		 */
		public String getSessionid() {
			try{
				return (String) UserUtils.getSession().getId();
			}catch (Exception e) {
				return "";
			}
		}
		
		@Override
		public String toString() {
			return String.valueOf(id);
		}

	}
	
}