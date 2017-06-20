package com.lance.easyui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/sosservices")
public class DemoController {

	@RequestMapping(value="/sms/testget")
	@ResponseBody
	public ResponseModel<Boolean> testget(HttpServletRequest request) {
		ResponseModel<Boolean> res = new ResponseModel<Boolean>();
		System.out.println("sms testget Start");
		res.setResponseCode("200");
		res.setResponseMsg("success");
		
		return res;
	}
	
	@RequestMapping(value="/sms/sendPhoneCode", method=RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> sendPhoneCode(@RequestBody DealerAccountModel dealerAccountModel ,HttpServletRequest request) {
		ResponseModel<Boolean> res = new ResponseModel<Boolean>();
		System.out.println("sms sendPhoneCode Start");
		System.out.println("sms sendPhoneCode Param:"+JsonUtil.getJson(dealerAccountModel));
		res.setResponseCode("200");
		res.setResponseMsg("success");
		
		return res;
	}
	
	@RequestMapping(value="/sms/checkPhoneCode", method=RequestMethod.POST)
	@ResponseBody
	public ResponseModel<Boolean> checkPhoneCode(@RequestBody DealerAccountModel dealerAccountModel ,HttpServletRequest request) {
		ResponseModel<Boolean> res = new ResponseModel<Boolean>();
		System.out.println("sms checkPhoneCode Start");
		System.out.println("sms checkPhoneCode Param:"+JsonUtil.getJson(dealerAccountModel));
		
		res.setResponseCode("200");
		res.setResponseMsg("success");
		
		return res;
	}
}
