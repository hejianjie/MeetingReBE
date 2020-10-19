package io.renren.modules.meeting.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.meeting.dao.MeetRoomEquipmentDao;
import io.renren.modules.meeting.entity.MeetRoomEquipmentEntity;
import io.renren.modules.meeting.service.MeetRoomEquipmentService;


@Service("meetRoomEquipmentService")
public class MeetRoomEquipmentServiceImpl extends ServiceImpl<MeetRoomEquipmentDao, MeetRoomEquipmentEntity> implements MeetRoomEquipmentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MeetRoomEquipmentEntity> page = this.page(
                new Query<MeetRoomEquipmentEntity>().getPage(params),
                new QueryWrapper<MeetRoomEquipmentEntity>()
        );

        return new PageUtils(page);
    }

}