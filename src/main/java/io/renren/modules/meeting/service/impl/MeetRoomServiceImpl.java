package io.renren.modules.meeting.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.meeting.dao.MeetRoomDao;
import io.renren.modules.meeting.entity.MeetRoomEntity;
import io.renren.modules.meeting.service.MeetRoomService;


@Service("meetRoomService")
public class MeetRoomServiceImpl extends ServiceImpl<MeetRoomDao, MeetRoomEntity> implements MeetRoomService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MeetRoomEntity> page = this.page(
                new Query<MeetRoomEntity>().getPage(params),
                new QueryWrapper<MeetRoomEntity>()
        );

        return new PageUtils(page);
    }

}