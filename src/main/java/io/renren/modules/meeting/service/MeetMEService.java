package io.renren.modules.meeting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.meeting.entity.MeetMEEntity;

import java.util.Map;

/**
 * 
 *
 * @author hejianjie
 * @email 1805399513@qq.com
 * @date 2020-10-19 15:58:10
 */
public interface MeetMEService extends IService<MeetMEEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

