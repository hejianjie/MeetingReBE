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

import io.renren.modules.meeting.entity.MeetEntity;
import io.renren.modules.meeting.service.MeetService;
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
@RequestMapping("meeting/meet")
public class MeetController {
    @Autowired
    private MeetService meetService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("meeting:meet:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = meetService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("meeting:meet:info")
    public R info(@PathVariable("id") Integer id){
		MeetEntity meet = meetService.getById(id);

        return R.ok().put("meet", meet);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("meeting:meet:save")
    public R save(@RequestBody MeetEntity meet){
		meetService.save(meet);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("meeting:meet:update")
    public R update(@RequestBody MeetEntity meet){
		meetService.updateById(meet);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("meeting:meet:delete")
    public R delete(@RequestBody Integer[] ids){
		meetService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
