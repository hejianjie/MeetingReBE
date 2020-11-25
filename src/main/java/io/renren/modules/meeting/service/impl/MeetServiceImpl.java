package io.renren.modules.meeting.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.meeting.dao.MeetDao;
import io.renren.modules.meeting.entity.MeetEntity;
import io.renren.modules.meeting.service.MeetService;


@Service("meetService")
public class MeetServiceImpl extends ServiceImpl<MeetDao, MeetEntity> implements MeetService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        String name = (String) params.get("name");
        String date = (String) params.get("date");
        System.out.println(date);
        IPage<MeetEntity> page = this.page(
                new Query<MeetEntity>().getPage(params),
                new QueryWrapper<MeetEntity>()
                        .like("room_user", name)
                        .eq("status", "已申请")
                        .like("date", date)
                        .and(wrapper ->
                                wrapper.like(StringUtils.isNotBlank(key), "room_name", key)
                                        .or()
                                        .like("room_user", key))

        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryHistoryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        String name = (String) params.get("name");
        String date = (String) params.get("date");
        IPage<MeetEntity> page = this.page(
                new Query<MeetEntity>().getPage(params),
                new QueryWrapper<MeetEntity>()
                        .like("room_user", name)
                        .eq("status", "已归档")
                        .like("date", date)
                        .and(wrapper ->
                                wrapper.like(StringUtils.isNotBlank(key), "room_name", key)
                                        .or()
                                        .like("room_user", key))
        );

        return new PageUtils(page);
    }

}