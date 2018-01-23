package com.bigdata.logmonitor.service;

import com.bigdata.logmonitor.bean.Rule;

import java.util.List;
import java.util.Map;

public interface RuleService {
    Map<String,List<Rule>> getRuleMap();
}
