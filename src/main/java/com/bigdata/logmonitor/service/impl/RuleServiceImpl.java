package com.bigdata.logmonitor.service.impl;

import com.bigdata.logmonitor.bean.*;
import com.bigdata.logmonitor.dao.AppTypeMapper;
import com.bigdata.logmonitor.dao.RuleMapper;
import com.bigdata.logmonitor.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RuleServiceImpl implements RuleService {

    @Autowired
    RuleMapper ruleMapper;
    @Autowired
    AppTypeMapper appTypeMapper;

    @Override
    public Map<String, List<Rule>> getRuleMap() {
        Map<String, List<Rule>> map = new HashMap<>();

        List<Rule> rulelist = ruleMapper.selectByExample(new RuleExample());

        for (Rule rule:rulelist) {
            List<Rule> list = map.get(rule.getAppid() + "");
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(rule);
            map.put(rule.getAppid() + "", list);
        }
        return map;
    }
}
