package io.renren.modules.job.task;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.meeting.entity.MeetEntity;
import io.renren.modules.meeting.service.MeetService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CheckMeet implements ITask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MeetService meetService;
    @Override
    public void run(String params) {
//        logger.debug("TestTask定时任务正在执行，参数为：{}", params);
//        //使用Date创建日期对象
//        Date date = new Date();
//        System.out.println("TestTask定时任务正在执行，当前的日期是------>" + date);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println("格式化后的时间------->" + format.format(date));
//
//        String datestring = format.format(date);
//        QueryWrapper<MeetEntity> qe= new QueryWrapper<>();
//        qe.eq("date",datestring);
//        List<MeetEntity> list= meetService.list(qe);
//
//        for(int i=0;i<list.size();i++)
//        {
//            MeetEntity a=list.get(i);
//            System.out.println(a.getStartTime());
//            System.out.println(a.getStatus());
//            //meetService.update();
//        }
    }
}