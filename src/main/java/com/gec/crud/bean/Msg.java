package com.gec.crud.bean;
/**
 * 	通用的返回类
 * 		功能：1.带有请求是否成功的状态码
 * 
 *
 */

import java.util.HashMap;
import java.util.Map;

public class Msg {
	
		//状态码 100 -成功 200 -失败
		private int code;
		//提示信息
		private String msg;
		//把数据用哈希Map封装起来，经@ReponseBody变成json数据
		private Map<String, Object> extend=new HashMap<String, Object>();
		
		public static Msg success() { //创建
			Msg result=new Msg();
			result.code=100;
			result.setMsg("处理成功");
			return result;
		}
		
		public static Msg fail() { //往Map 里put数据
			Msg result=new Msg();
			result.code=200;
			result.setMsg("处理失败");
			return result;
		}
		
		public Msg add(String key,Object value) {
			this.getExtend().put(key, value);
			return this;
		}
		
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public Map<String, Object> getExtend() {
			return extend;
		}
		public void setExtend(Map<String, Object> extend) {
			this.extend = extend;
		}
		
		
}
