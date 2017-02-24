package spbm.test.shiro.credentials;

import java.security.Key;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.hash.Sha384Hash;

import com.spbm.common.security.encrypt.Digests;
import com.spbm.common.utils.Encodes;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	@Override
	public boolean doCredentialsMatch(AuthenticationToken authcToken,
			AuthenticationInfo info) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		Object tokenCredentials = shaEncrypt(String.valueOf(token.getPassword()));
		Object accountCredentials = getCredentials(info);
		// 将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
		return equals(tokenCredentials, accountCredentials);
	}

	// 将传进来密码加密方法
	private static String shaEncrypt(String data) {
		String sha384Hex = new Sha384Hash(data).toBase64();
		System.out.println(data + ":" + sha384Hex);
		return sha384Hex;
	}

	/**
	 * base64进制加密
	 */
	public static String encrytBase64(String inputPwd) {
		byte[] bytes = inputPwd.getBytes();
		String encrytBase64Pwd = Base64.encodeToString(bytes);
		System.out.println(inputPwd + ":" + encrytBase64Pwd);
		return encrytBase64Pwd;
	}
	/** 
     * 16进制加密 
     * 
     * @param password 
     * @return 
     */ 
    public static String encrytHex(String password) { 
        byte[] bytes = password.getBytes(); 
        return Hex.encodeToString(bytes); 
    } 
    /** 
     * 16进制解密 
     * @param cipherText 
     * @return 
     */ 
    public static String decryptHex(String cipherText) { 
        return new String(Hex.decode(cipherText)); 
    } 
    
    public static String generateKey() 
    { 
        AesCipherService aesCipherService=new AesCipherService(); 
        Key key=aesCipherService.generateNewKey(); 
        return Base64.encodeToString(key.getEncoded()); 
    } 
    /** 
     * 对密码进行md5加密,并返回密文和salt，包含在User对象中 
     * @param username 用户名 
     * @param password 密码 
     * @return 密文和salt 
     */ 
    public static void md5Password(String username,String password){ 
//    	SecureRandomNumberGenerator secureRandomNumberGenerator=new SecureRandomNumberGenerator();
//        String salt = secureRandomNumberGenerator.nextBytes().toHex(); 
    	String salt = "8d78869f470951332959580424d4bf4f";
//    	byte[] salt = Digests.generateSalt(SALT_SIZE);
        //组合username,两次迭代，对密码进行加密 
//        String md5Pwd= new Md5Hash(password, ByteSource.Util.bytes(username+salt),2).toBase64();
//        UserInfo user=new UserInfo(); 
//        user.setPassword(md5Pwd); 
//        user.setSalt(salt); 
//        user.setAccount(username); 
//        System.out.println("UserInfoJson:"+JsonUtil.getJson(user));
//        
        return  ; 
    } 
	/**
	 * base64进制解密
	 */
	public static String decryptBase64(String encrytBase64Pwd) {
		String decryptBase64Pwd = Base64.decodeToString(encrytBase64Pwd);
		System.out.println(encrytBase64Pwd + ":" + decryptBase64Pwd);
		return decryptBase64Pwd;
	}

	 
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		String encryptPwd = Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
		System.out.println(plainPassword+";"+encryptPwd + ";" + password);
		return password.equals(encryptPwd);
	}


	// e10adc3949ba59abbe56e057f20f883e
	public static void main(String[] args) {
//		String inputPwd = "132456";
//		String dbPwd = shaEncrypt(inputPwd);
		
		//plainPassword 明文密码  ; password 密文密码
		validatePassword("123456","e10adc3949ba59abbe56e057f20f883e");
		
		//
		md5Password("admin","123456");
//		md5Password("lance","123456");
		
		
	}

}