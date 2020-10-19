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

import io.renren.modules.meeting.entity.MeetMEEntity;
import io.renren.modules.meeting.service.MeetMEService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author hejianjie
 * @email 1805399513@qq.com
 * @date 2020-10-19 15:58:10
 */
@RestController
@RequestMapping("meeting/meetme")
public class MeetMEController {
    @Autowired
    private MeetMEService meetMEService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("meeting:meetme:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = meetMEService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("meeting:meetme:info")
    public R info(@PathVariable("id") Integer id){
		MeetMEEntity meetME = meetMEService.getById(id);

        return R.ok().put("meetME", meetME);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("meeting:meetme:save")
    public R save(@RequestBody MeetMEEntity meetME){
		meetMEService.save(meetME);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("meeting:meetme:update")
    public R update(@RequestBody MeetMEEntity meetME){
		meetMEService.updateById(meetME);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("meeting:meetme:delete")
    public R delete(@RequestBody Integer[] ids){
		meetMEService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
