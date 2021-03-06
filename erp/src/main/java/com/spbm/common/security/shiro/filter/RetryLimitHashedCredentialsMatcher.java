package com.spbm.common.security.shiro.filter;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Value;

import com.spbm.common.utils.JsonUtil;

public class RetryLimitHashedCredentialsMatcher extends
		HashedCredentialsMatcher {
	
	@Value("${application.retryCount:3}")
	private int retryLimitCount = 3;

	
	private Cache<String, AtomicInteger> passwordRetryCache;

	public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		String username = (String) token.getPrincipal();
		// retry count + 1
		AtomicInteger retryCount = passwordRetryCache.get(username);
		if (retryCount == null) {
			retryCount = new AtomicInteger(0);
			passwordRetryCache.put(username, retryCount);
		} 
		
		if (retryCount.incrementAndGet() > retryLimitCount) {
			// if retry count > 5 throw
			throw new ExcessiveAttemptsException();
		}
		System.out.println("CredentialsMatch token:"+JsonUtil.getJson(token));
		System.out.println("CredentialsMatch info:"+JsonUtil.getJson(info));
		//验证是否正确
		boolean matches = super.doCredentialsMatch(token, info);
		System.out.println("CredentialsMatch Result:"+matches);
		if (matches) {
			// clear retry count
			passwordRetryCache.remove(username);
		}
		return matches;
	} 
}