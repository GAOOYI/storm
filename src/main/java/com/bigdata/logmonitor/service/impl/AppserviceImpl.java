package com.bigdata.logmonitor.service.impl;


import com.bigdata.logmonitor.bean.App;
import com.bigdata.logmonitor.bean.AppExample;
import com.bigdata.logmonitor.dao.AppMapper;
import com.bigdata.logmonitor.service.AppService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:SqlMapConfig.xml"})
public class AppserviceImpl implements AppService {

    @Autowired
    private AppMapper appMapper;

    @Override
    public List<App> getAll() {
        return appMapper.selectByExample(new AppExample());
    }

    @Test
    public void testdao(){
        List<App> apps = appMapper.selectByExample(new AppExample());
        for (App app:apps){
            System.out.println(app.toString());
        }
    }





}
