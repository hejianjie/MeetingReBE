package io.renren.modules.meeting.service.ownimpl;

import io.renren.modules.meeting.dao.MeetDao;
import io.renren.modules.meeting.entity.MeetEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component("getechart")
public interface Getechart {

    @Select("select room_name as name,COUNT(CASE WHEN date>=#{date} THEN 1 ELSE NULL END) as count from meet  group by room_name")
    public  List<Map<String, Object>> getalldata(@Param("date") String date);
}
