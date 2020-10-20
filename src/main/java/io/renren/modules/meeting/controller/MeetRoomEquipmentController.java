package io.renren.modules.meeting.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.meeting.entity.MeetMEEntity;
import io.renren.modules.meeting.entity.MeetRoomEntity;
import io.renren.modules.meeting.service.MeetMEService;
import io.renren.modules.meeting.service.MeetRoomService;
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
 * @author hejianjie
 * @email 1805399513@qq.com
 * @date 2020-10-19 15:58:11
 */
@RestController
@RequestMapping("meeting/meetroomequipment")
public class MeetRoomEquipmentController {
    @Autowired
    private MeetRoomEquipmentService meetRoomEquipmentService;
    @Autowired
    private MeetMEService meetMEService;
    @Autowired
    private MeetRoomService meetRoomService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("meeting:meetroomequipment:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = meetRoomEquipmentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("meeting:meetroomequipment:info")
    public R info(@PathVariable("id") Integer id) {
        MeetRoomEquipmentEntity meetRoomEquipment = meetRoomEquipmentService.getById(id);

        return R.ok().put("meetRoomEquipment", meetRoomEquipment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("meeting:meetroomequipment:save")
    public R save(@RequestBody MeetRoomEquipmentEntity meetRoomEquipment) {
        meetRoomEquipmentService.save(meetRoomEquipment);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("meeting:meetroomequipment:update")
    public R update(@RequestBody MeetRoomEquipmentEntity meetRoomEquipment) {
        String name = meetRoomEquipmentService.getById(meetRoomEquipment.getId()).getName();
        meetRoomEquipmentService.updateById(meetRoomEquipment);

        QueryWrapper<MeetMEEntity> qe = new QueryWrapper<>();
        qe.eq("e_id", meetRoomEquipment.getId());
        List<MeetMEEntity> melist = meetMEService.list(qe);
        for (int i = 0; i < melist.size(); i++) {
            MeetRoomEntity room = meetRoomService.getById(melist.get(i).getRId());
            String eq = room.getEquipment();
            room.setEquipment(eq.replace(name, meetRoomEquipment.getName()));
            meetRoomService.updateById(room);
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("meeting:meetroomequipment:delete")
    public R delete(@RequestBody Integer[] ids) {

        for (int i = 0; i < Arrays.asList(ids).size(); i++) {

            QueryWrapper<MeetMEEntity> qe = new QueryWrapper<>();
            qe.eq("e_id", Arrays.asList(ids).get(i));
            List<MeetMEEntity> melist = meetMEService.list(qe);
            String name = meetRoomEquipmentService.getById(Arrays.asList(ids).get(i)).getName();

            for (int j = 0; j < melist.size(); j++) {
                MeetRoomEntity room = meetRoomService.getById(melist.get(j).getRId());
                String eqstring = room.getEquipment();
                if (eqstring.equals(name))
                    room.setEquipment("无");
                else if (eqstring.indexOf(',' + name) != -1) {
                    room.setEquipment(eqstring.replace("," + name, ""));
                } else if (eqstring.indexOf(name + ',') != -1) {
                    room.setEquipment(eqstring.replace(name + ",", ""));
                }
                meetRoomService.updateById(room);

//                String[] eqlist = eqstring.split(",");
//                String target = "";
//                System.out.println(target+"   ==="+j);
//                if (eqlist.length == 1) {
//                    if (eqlist[0].equals(name))
//                    {
//                        room.setEquipment("无");
//                        meetRoomService.updateById(room);
//                    }
//                    continue;
//                } else {
//                    System.out.println("!!!!!++++++++");
//                    System.out.println(target);
//                    for (int k = 0; k < eqlist.length; k++) {
//                        System.out.println(eqlist.length);
//                        System.out.println(eqstring);
//                        System.out.println(eqlist[k] + "  " + k);
//                        System.out.println("+++++++++");
//                        if (k == 0){
//                            if (eqlist[k].equals(name))
//                                continue;
//                            else {
//                                target=eqlist[k];
//                            }
//                        }
//                        else {
//                            if (eqlist[k].equals(name))
//                                continue;
//                            else {
//                                target = target + ',' + eqlist[k];
//                            }
//                        }
//                    }
//                    System.out.println(target);
//                    System.out.println("+++++++++!!!");
//                }
//                System.out.println(target);
//                System.out.println("+++++++++!!!");
//                room.setEquipment(target);
//                meetRoomService.updateById(room);
            }
            meetMEService.remove(qe);
        }
        meetRoomEquipmentService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
