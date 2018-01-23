package com.bigdata.logmonitor.dao;

import com.bigdata.logmonitor.bean.Rule;
import com.bigdata.logmonitor.bean.RuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RuleMapper {
    int countByExample(RuleExample example);

    int deleteByExample(RuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Rule record);

    int insertSelective(Rule record);

    List<Rule> selectByExample(RuleExample example);

    Rule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Rule record, @Param("example") RuleExample example);

    int updateByExample(@Param("record") Rule record, @Param("example") RuleExample example);

    int updateByPrimaryKeySelective(Rule record);

    int updateByPrimaryKey(Rule record);
}