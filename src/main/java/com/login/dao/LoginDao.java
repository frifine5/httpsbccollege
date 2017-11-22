package com.login.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.login.entity.LoginEntity;

public interface LoginDao {

	@Select("SELECT SERID, NAME, PWD FROM LOGIN WHERE NAME = #{name} AND PWD = #{pwd}")
	@Results({ 
		@Result(property = "serId", column = "SERID"), 
		@Result(property = "name", column = "NAME"),
		@Result(property = "pwd", column = "PWD"), 
		})
	LoginEntity findRecord(LoginEntity condition);

	@Update("UPDATE LOGIN SET NAME = #{name}, PWD = #{pwd} WHERE SERID = #{serId}")
	int updateRecord(LoginEntity condition);

	@Insert("INSERT INTO LOGIN(NAME, PWD) VALUES(#{name}, #{pwd})")
	int insertRecord(LoginEntity record);

	@Select("SELECT SERID FROM LOGIN WHERE NAME = #{name}")
	@Results({ 
		@Result(property = "serId", column = "SERID"), 
		@Result(property = "name", column = "NAME"),
		@Result(property = "pwd", column = "PWD"), 
		})
	List<LoginEntity> nameIsExist(String name);
	
	@Delete("DELETE FROM LOGIN WHERE SERID = #{serId}")
	int deleteByPK(int serId);
	
}
