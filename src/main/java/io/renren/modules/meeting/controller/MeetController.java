package io.renren.modules.meeting.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.meeting.entity.MeetRoomEntity;
import io.renren.modules.meeting.service.MeetRoomService;
import io.renren.modules.meeting.service.ownimpl.Getechart;
import io.renren.modules.sys.entity.SysRoleEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.entity.SysUserRoleEntity;
import io.renren.modules.sys.service.SysRoleService;
import io.renren.modules.sys.service.SysUserRoleService;
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
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private Getechart getechart;

    public void deal() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String datestring = format.format(date);
        QueryWrapper<MeetEntity> qeqe = new QueryWrapper<>();
        qeqe.eq("status", "已申请");
        List<MeetEntity> list = meetService.list(qeqe);

        for (int i = 0; i < list.size(); i++) {
            MeetEntity a = list.get(i);
            Long deal=0l;
            String hour = "";
            if (Integer.parseInt(a.getStartTime()) < 10)
                hour = "0" + a.getStartTime();
            else hour = a.getStartTime();
            String createTime = a.getDate() + " " + hour + ":00:00";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                 deal = sdf.parse(createTime).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Long now= System.currentTimeMillis();

//            System.out.println(deal);
//            System.out.println(now);
            if(deal<now)
            {
                System.out.println("已过期");
                a.setStatus("已归档");
                meetService.saveOrUpdate(a);
            }
            else
            {
                System.out.println("未过期");

            }

            //meetService.update();
        }
    }




    /**
     * Echart图表数据
     */
    @RequestMapping("/echart")
    public R echart() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        c.add(Calendar.DATE, - 7);
        Date d = c.getTime();
        String day = format.format(d);
        System.out.println("过去七天："+day);

        c.setTime(new Date());
        c.add(Calendar.DATE, - 14);
        Date d2 = c.getTime();
        String day2 = format.format(d2);
        System.out.println("过去十四天："+day2);

        //过去一月
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        System.out.println("过去一个月："+mon);

        List<Map<String, Object>> list = new ArrayList<>();
        System.out.println(getechart.getalldata(day));

        return R.ok().put("day7",getechart.getalldata(day)).put("day14",getechart.getalldata(day2)).put("mon",getechart.getalldata(mon));

    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("meeting:meet:list")
    public R list(@RequestParam Map<String, Object> params) {
        deal();
        SysUserEntity user_now = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        QueryWrapper<SysUserRoleEntity> qe = new QueryWrapper<>();
        qe.eq("user_id", user_now.getUserId());

        if (!(user_now.getUsername().equals("admin")) && sysUserRoleService.getOne(qe).getRoleId() != 1) {
            params.put("name", user_now.getThename());
        }
        PageUtils page = meetService.queryPage(params);
        return R.ok().put("page", page);

    }


    /**
     * 历史列表
     * '
     */
    @RequestMapping("/historylist")
    @RequiresPermissions("meeting:meet:list")
    public R historylist(@RequestParam Map<String, Object> params) {
        deal();
        SysUserEntity user_now = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        QueryWrapper<SysUserRoleEntity> qe = new QueryWrapper<>();
        qe.eq("user_id", user_now.getUserId());

        if (!(user_now.getUsername().equals("admin")) && sysUserRoleService.getOne(qe).getRoleId() != 1) {
            params.put("name", user_now.getThename());
        }
        PageUtils page = meetService.queryHistoryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 表单提交
     */
    @RequestMapping("/submit")
    @RequiresPermissions("meeting:meet:list")
    public R submit(@RequestBody HashMap<String, Object> params) throws Exception {
        MeetEntity meetRequest = new MeetEntity();
        meetRequest.setDepartment(params.get("department").toString());
        meetRequest.setUserFrom(params.get("from").toString());
        meetRequest.setRoomUser(params.get("name").toString());
        meetRequest.setRoomName(params.get("room").toString());
        meetRequest.setEquipment(params.get("equipment").toString().replace("[", "").replace("]", ""));
        meetRequest.setUserPhone(params.get("mobile").toString());
        String datenow = params.get("datechoose").toString();
        String datestart = params.get("date1").toString().split(":")[0];
        String dateend = params.get("date2").toString().split(":")[0];
        meetRequest.setDate(datenow);
        meetRequest.setStartTime(datestart);
        meetRequest.setEndTime(dateend);
        meetRequest.setUserNum(Integer.parseInt(params.get("sum").toString()));
        String leaderstr=params.get("leader").toString();
        if(leaderstr.equals(""))
            leaderstr="无";
        meetRequest.setUsers(leaderstr);
        meetRequest.setMeetingTheme(params.get("theme").toString().replace("[", "").replace("]", ""));
        meetRequest.setStatus("已申请");
        if (params.get("note") != null)
            meetRequest.setRemark(params.get("note").toString());
        else
            meetRequest.setRemark("无");
        meetService.save(meetRequest);

        return R.ok();
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
            for (int j = 0; j <= room.size(); j++) {
                if (j != 0) {
                    map.put(room.get(j - 1).getRoomName(), room.get(j - 1));
                } else {
                    map.put("date", i + ":00-" + (i + 1) + ":00");
                }
            }
            table.add(map);
        }

        List<MeetEntity> chooselist = meetService.getBaseMapper().selectList(qe);
        for (int i = 0; i < chooselist.size(); i++) {
            for (int j = Integer.parseInt(chooselist.get(i).getStartTime()) - 7; j < Integer.parseInt(chooselist.get(i).getEndTime()) - 7; j++) {
                table.get(j).put(chooselist.get(i).getRoomName(), chooselist.get(i));
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
