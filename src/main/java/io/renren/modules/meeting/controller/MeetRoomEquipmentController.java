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

import io.renren.modules.meeting.entity.MeetRoomEquipmentEntity;
import io.renren.modules.meeting.service.MeetRoomEquipmentService;
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
@RequestMapping("meeting/meetroomequipment")
public class MeetRoomEquipmentController {
    @Autowired
    private MeetRoomEquipmentService meetRoomEquipmentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("meeting:meetroomequipment:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = meetRoomEquipmentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("meeting:meetroomequipment:info")
    public R info(@PathVariable("id") Integer id){
		MeetRoomEquipmentEntity meetRoomEquipment = meetRoomEquipmentService.getById(id);

        return R.ok().put("meetRoomEquipment", meetRoomEquipment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("meeting:meetroomequipment:save")
    public R save(@RequestBody MeetRoomEquipmentEntity meetRoomEquipment){
		meetRoomEquipmentService.save(meetRoomEquipment);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("meeting:meetroomequipment:update")
    public R update(@RequestBody MeetRoomEquipmentEntity meetRoomEquipment){
		meetRoomEquipmentService.updateById(meetRoomEquipment);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("meeting:meetroomequipment:delete")
    public R delete(@RequestBody Integer[] ids){
		meetRoomEquipmentService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
