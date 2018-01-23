package com.bigdata.logmonitor.service.impl;


import com.bigdata.logmonitor.bean.App;
import com.bigdata.logmonitor.bean.AppExample;
import com.bigdata.logmonitor.bean.User;
import com.bigdata.logmonitor.dao.AppMapper;
import com.bigdata.logmonitor.dao.UserMapper;
import com.bigdata.logmonitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    AppMapper appMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public Map<String, List<User>> getUserMap() {
        Map<String, List<User>> map = new HashMap<>();
        List<App> apps = appMapper.selectByExample(new AppExample());

        for (App app:apps) {
            String userId = app.getUserid();
            List<User> users = map.get(app.getId());
            if (users == null){
                users = new ArrayList<>();
            }
            String[] split = userId.split(",");
            for (String id:split) {
                users.add(userMapper.selectByPrimaryKey(Long.valueOf(id)));
            }
            map.put(app.getId() + "", users);
        }
        return map;
    }
}
