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
			System.out.println(estr);
			Map<String, String> queryParas = new HashMap<>();
//			queryParas.put("Account", TestConst.ACCOUNT);
//			queryParas.put("Key", TestConst.KEY);
			queryParas.put("content", estr);
			String rt = HttpUtil.authGet(TestConst.BR_URL+TestConst.INFOVERIFY, 
				queryParas, TestConst.ACCOUNT, TestConst.KEY);
			System.out.println("1:\n"+rt);
		}catch(Exception e){
			
		}
		try {
			String estr = AesUtil.encryptStr(content, TestConst.KEY);
			System.out.println(estr);
			String str = TestConst.BR_URL+TestConst.INFOVERIFY+"?account="+TestConst.ACCOUNT+"&content="+estr;
//			System.out.println("\n"+str);
//			String str = "http://58.250.152.93:18084/brapi/services/infoVerify?account=gmxa01&content=7F85B1E25555735CB4674F979E5FA9901FAA7286E4FFE971DAF156877A965F2E39EE793A2A23CAE71B80D11BB9325E0A025572486B36701B18131E5A419688612A8247A6AB31E483CB959510B38BBAF3";
//						  http://58.250.152.93:18084/brapi/services/infoVerify?account=gmxa01&7F85B1E25555735CB4674F979E5FA9901FAA7286E4FFE971DAF156877A965F2E39EE793A2A23CAE71B80D11BB9325E0A025572486B36701B18131E5A419688612A8247A6AB31E483CB959510B38BBAF3

			
			String rstr = HttpUtil.get(str);
			System.out.println("2:\n"+rstr);
			JSONObject obj = JSON.parseObject(rstr);
			String eee = obj.getString("data");
			System.out.println(eee);
			String dstr = AesUtil.decryptStr(eee, TestConst.KEY);
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
