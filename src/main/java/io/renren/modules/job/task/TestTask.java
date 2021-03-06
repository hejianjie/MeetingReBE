/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.meeting.entity.MeetEntity;
import io.renren.modules.meeting.service.MeetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 测试定时任务(演示Demo，可删除)
 * <p>
 * testTask为spring bean的名称
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component("testTask")
public class TestTask implements ITask {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MeetService meetService;

    //	 0 58 0/1 * * ?   //每小时的58运行一次
    @Override
    public void run(String params) {
        logger.debug("TestTask定时任务正在执行，参数为：{}", params);
        //使用Date创建日期对象
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
//           //meetService.update();
//        }

    }


}
