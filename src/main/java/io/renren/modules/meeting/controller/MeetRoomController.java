package io.renren.modules.meeting.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.meeting.entity.MeetMEEntity;
import io.renren.modules.meeting.entity.MeetRoomEquipmentEntity;
import io.renren.modules.meeting.service.MeetMEService;
import io.renren.modules.meeting.service.MeetRoomEquipmentService;
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
 * @author hejianjie
 * @email 1805399513@qq.com
 * @date 2020-10-19 15:58:11
 */
@RestController
@RequestMapping("meeting/meetroom")
public class MeetRoomController {
    @Autowired
    private MeetRoomService meetRoomService;
    @Autowired
    private MeetRoomEquipmentService meetRoomEquipmentService;
    @Autowired
    private MeetMEService meetMEService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("meeting:meetroom:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = meetRoomService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{roomId}")
    @RequiresPermissions("meeting:meetroom:info")
    public R info(@PathVariable("roomId") Integer roomId) {
        MeetRoomEntity meetRoom = meetRoomService.getById(roomId);
        List<MeetRoomEquipmentEntity> eqIdList = meetRoomEquipmentService.list();
        return R.ok().put("meetRoom", meetRoom).put("meetRoom", meetRoom);
    }

    /**
     * 设备列表
     */
    @RequestMapping("/eqlist")
    public R eqlist() {
        List<MeetRoomEquipmentEntity> eqList = meetRoomEquipmentService.list();
        return R.ok().put("eqList", eqList);
    }

    /**
     * 设备检查
     */
    public String equipmentcheck(String equipment) {
        String[] eqlist = equipment.split(",");
        List<String> a = new ArrayList<>();
        String checkedeq = "";
        for (int i = 0; i < eqlist.length; i++) {
            if (eqlist[i].equals(""))
                continue;
            a.add(eqlist[i]);
        }
        for (int i = 0; i < a.size(); i++) {
            checkedeq += a.get(i) + ",";
        }
        if (checkedeq.equals("")) {
            checkedeq = "无";
            return checkedeq;
        } else {
            checkedeq = checkedeq.substring(0, checkedeq.length() - 1);
            return checkedeq;
        }
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("meeting:meetroom:save")
    public R save(@RequestBody MeetRoomEntity meetRoom) {

        String check = equipmentcheck(meetRoom.getEquipment());
        meetRoom.setEquipment(check);

        meetRoomService.save(meetRoom);
        if (!meetRoom.getEquipment().equals("无")) {
            String[] eqlist = meetRoomService.getById(meetRoom.getRoomId()).getEquipment().split(",");
            QueryWrapper<MeetMEEntity> qe = new QueryWrapper<>();
            qe.eq("r_id", meetRoom.getRoomId());
            meetMEService.remove(qe);
            for (int i = 0; i < eqlist.length; i++) {
                QueryWrapper<MeetRoomEquipmentEntity> qe1 = new QueryWrapper<>();
                qe1.eq("name", eqlist[i]);
                MeetMEEntity a = new MeetMEEntity();
                a.setRId(meetRoom.getRoomId());
                a.setEId(meetRoomEquipmentService.getOne(qe1).getId());
                meetMEService.save(a);
            }
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("meeting:meetroom:update")
    public R update(@RequestBody MeetRoomEntity meetRoom) {

        String check = equipmentcheck(meetRoom.getEquipment());
        meetRoom.setEquipment(check);

        meetRoomService.updateById(meetRoom);
        if (!meetRoom.getEquipment().equals("无")) {
            String[] eqlist = meetRoomService.getById(meetRoom.getRoomId()).getEquipment().split(",");
            QueryWrapper<MeetMEEntity> qe = new QueryWrapper<>();
            qe.eq("r_id", meetRoom.getRoomId());
            meetMEService.remove(qe);
            for (int i = 0; i < eqlist.length; i++) {
                if (eqlist[i].equals(""))
                    continue;
                QueryWrapper<MeetRoomEquipmentEntity> qe1 = new QueryWrapper<>();
                qe1.eq("name", eqlist[i]);
                MeetMEEntity a = new MeetMEEntity();
                a.setRId(meetRoom.getRoomId());
                a.setEId(meetRoomEquipmentService.getOne(qe1).getId());
                meetMEService.save(a);
            }
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("meeting:meetroom:delete")
    public R delete(@RequestBody Integer[] roomIds) {
        meetRoomService.removeByIds(Arrays.asList(roomIds));
        for (int i = 0; i < Arrays.asList(roomIds).size(); i++) {
            QueryWrapper<MeetMEEntity> qe = new QueryWrapper<>();
            qe.eq("r_id", Arrays.asList(roomIds).get(i));
            meetMEService.remove(qe);
        }

        return R.ok();
    }

}
