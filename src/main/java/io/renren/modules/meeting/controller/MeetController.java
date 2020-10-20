package io.renren.modules.meeting.controller;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.meeting.entity.MeetRoomEntity;
import io.renren.modules.meeting.service.MeetRoomService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.meeting.entity.MeetEntity;
import io.renren.modules.meeting.service.MeetService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;


/**
 * @author hejianjie
 * @email 1805399513@qq.com
 * @date 2020-10-19 15:58:11
 */
@RestController
@RequestMapping("meeting/meet")
public class MeetController {
    @Autowired
    private MeetService meetService;
    @Autowired
    private MeetRoomService meetRoomService;
    @Autowired
    private SysUserService sysUserService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("meeting:meet:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = meetService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 表格//维护一个日期为参数的二维list
     */
    @RequestMapping("/table")
    @RequiresPermissions("meeting:meet:list")
    public R table(@RequestBody HashMap<String, String> params) {
        String date = params.get("value");

        SysUserEntity user_now = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();


        List<MeetRoomEntity> room = meetRoomService.list();

        List<Map<String, Object>> table = new ArrayList<>();

        QueryWrapper<MeetEntity> qe = new QueryWrapper<>();
        qe.eq("date", date);

        for (int i = 7; i < 21; i++) {
            Map map = new HashMap();
            for (int j = 0; j < room.size(); j++) {
                if (j != 0) {
                    map.put(room.get(j-1).getRoomName(), room.get(j-1));
                } else {
                    map.put("date", i + ":00-" + (i + 1) + ":00");
                }
            }
            table.add(map);
        }

        List<MeetEntity> chooselist  = meetService.getBaseMapper().selectList(qe);
        for (int i = 0; i < chooselist.size(); i++) {
            for (int j = Integer.parseInt(chooselist.get(i).getStartTime())-7; j < Integer.parseInt(chooselist.get(i).getEndTime())-7; j++) {
                table.get(j).put(chooselist.get(i).getRoomName(),chooselist.get(i));
            }
        }

        return R.ok().put("now_user", user_now).put("room", room).put("table", table);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("meeting:meet:info")
    public R info(@PathVariable("id") Integer id) {
        MeetEntity meet = meetService.getById(id);

        return R.ok().put("meet", meet);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("meeting:meet:save")
    public R save(@RequestBody MeetEntity meet) {
        meetService.save(meet);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("meeting:meet:update")
    public R update(@RequestBody MeetEntity meet) {
        meetService.updateById(meet);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("meeting:meet:delete")
    public R delete(@RequestBody Integer[] ids) {
        meetService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
