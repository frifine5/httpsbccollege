package com.prajnasys.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.prajnasys.common.AesUtil;
import com.prajnasys.common.HttpUtil;
import com.prajnasys.entity.PhoneValidateRequest;
import com.prajnasys.entity.TestConst;

public class MainTest {
	
	public static void main(String[] args) {
		//	testEaD();
		String name = "籍红全";
		String phone = "15935203314";
		String cordno = "440923198109022070";
		PhoneValidateRequest req = new PhoneValidateRequest();
		req.setName(name);
		req.setPhone(phone);
		req.setCardno(cordno);
		Object json = JSON.toJSON(req);
		String content = JSON.toJSONString(json);
			
		try {
			String estr = AesUtil.encryptStr(content, TestConst.KEY);
			System.out.println("加密的content:\n"+estr);
			String str = TestConst.BR_URL+TestConst.INFOVERIFY+"?account="+TestConst.ACCOUNT+"&content="+estr;
			String rstr = HttpUtil.get(str);
			System.out.println("返回数据:\n"+rstr);
			JSONObject obj = JSON.parseObject(rstr);
			String edata = obj.getString("data");
			System.out.println("返回结果中的e(data):\n"+edata);
			String dstr = AesUtil.decryptStr(edata, TestConst.KEY);
			System.out.println("解密:\n"+dstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}

	@Test
	public void testEaD() {
		PhoneValidateRequest req = new PhoneValidateRequest();
		req.setName("测试员");
		req.setPhone("15568956893");
		req.setCardno("440923198109022070");
		Object json = JSON.toJSON(req);
		String content = JSON.toJSONString(json);
		try {
			String estr = AesUtil.encryptStr(content, TestConst.KEY);
			System.out.println(estr);
			String dstr = AesUtil.decryptStr(estr, TestConst.KEY);
			System.out.println(dstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String rts = "699F59E0AEB75989C4D88D9D3CFBD90E15D68FC4446C201EC9A01EB1C9C3B69AC21235FE2BF888CA0EDCAC51201E3BD8B180F51AC22267A16C4264BD6BED020B";
			String dstr = AesUtil.decryptStr(rts, TestConst.KEY);
			System.out.println(dstr);
		} catch (Exception e) {
		}
	}




}
