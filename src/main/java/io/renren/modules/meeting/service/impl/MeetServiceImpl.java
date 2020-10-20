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
        String key = (String)params.get("key");
        IPage<MeetEntity> page = this.page(
                new Query<MeetEntity>().getPage(params),
                new QueryWrapper<MeetEntity>()
                        .like(StringUtils.isNotBlank(key),"room_user", key)
                        .isNotNull("room_name")

        );

        return new PageUtils(page);
    }

}