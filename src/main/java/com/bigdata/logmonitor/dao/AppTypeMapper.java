package com.bigdata.logmonitor.dao;

import com.bigdata.logmonitor.bean.AppType;
import com.bigdata.logmonitor.bean.AppTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppTypeMapper {
    int countByExample(AppTypeExample example);

    int deleteByExample(AppTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppType record);

    int insertSelective(AppType record);

    List<AppType> selectByExample(AppTypeExample example);

    AppType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppType record, @Param("example") AppTypeExample example);

    int updateByExample(@Param("record") AppType record, @Param("example") AppTypeExample example);

    int updateByPrimaryKeySelective(AppType record);

    int updateByPrimaryKey(AppType record);
}