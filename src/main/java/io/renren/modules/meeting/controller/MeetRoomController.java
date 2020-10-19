package io.renren.modules.meeting.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.meeting.entity.MeetRoomEntity;
import io.renren.modules.meeting.service.MeetRoomService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author hejianjie
 * @email 1805399513@qq.com
 * @date 2020-10-19 15:58:11
 */
@RestController
@RequestMapping("meeting/meetroom")
public class MeetRoomController {
    @Autowired
    private MeetRoomService meetRoomService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("meeting:meetroom:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = meetRoomService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{roomId}")
    @RequiresPermissions("meeting:meetroom:info")
    public R info(@PathVariable("roomId") Integer roomId){
		MeetRoomEntity meetRoom = meetRoomService.getById(roomId);

        return R.ok().put("meetRoom", meetRoom);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("meeting:meetroom:save")
    public R save(@RequestBody MeetRoomEntity meetRoom){
		meetRoomService.save(meetRoom);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("meeting:meetroom:update")
    public R update(@RequestBody MeetRoomEntity meetRoom){
		meetRoomService.updateById(meetRoom);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("meeting:meetroom:delete")
    public R delete(@RequestBody Integer[] roomIds){
		meetRoomService.removeByIds(Arrays.asList(roomIds));

        return R.ok();
    }

}
