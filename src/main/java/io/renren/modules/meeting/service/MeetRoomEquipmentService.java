package io.renren.modules.meeting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.meeting.entity.MeetRoomEquipmentEntity;

import java.util.Map;

/**
 * 
 *
 * @author hejianjie
 * @email 1805399513@qq.com
 * @date 2020-10-19 15:58:11
 */
public interface MeetRoomEquipmentService extends IService<MeetRoomEquipmentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

