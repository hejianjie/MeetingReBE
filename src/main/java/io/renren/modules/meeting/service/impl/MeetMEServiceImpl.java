package io.renren.modules.meeting.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.meeting.dao.MeetMEDao;
import io.renren.modules.meeting.entity.MeetMEEntity;
import io.renren.modules.meeting.service.MeetMEService;


@Service("meetMEService")
public class MeetMEServiceImpl extends ServiceImpl<MeetMEDao, MeetMEEntity> implements MeetMEService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MeetMEEntity> page = this.page(
                new Query<MeetMEEntity>().getPage(params),
                new QueryWrapper<MeetMEEntity>()
        );

        return new PageUtils(page);
    }

}