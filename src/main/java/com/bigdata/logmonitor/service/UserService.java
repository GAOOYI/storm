package com.bigdata.logmonitor.service;

import com.bigdata.logmonitor.bean.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String,List<User>> getUserMap();
}
