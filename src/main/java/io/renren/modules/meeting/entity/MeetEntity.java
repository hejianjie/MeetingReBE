package io.renren.modules.meeting.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author hejianjie
 * @email 1805399513@qq.com
 * @date 2020-10-19 15:58:11
 */
@Data
@TableName("meet")
public class MeetEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	@TableId
	private Integer id;
	/**
	 * 使用单位（例:软件学院）
	 */
	private String department;
	/**
	 * 会议室预约人
	 */
	private String roomUser;
	/**
	 * 预约人隶属部门
	 */
	private String userFrom;
	/**
	 * 会议室名称
	 */
	private String roomName;
	/**
	 * 设备
	 */
	private String equipment;
	/**
	 * 预约人联系方式
	 */
	private String userPhone;
	/**
	 * 会议日期
	 */
	private String date;
	/**
	 * 会议开始时间
	 */
	private String startTime;
	/**
	 * 会议结束时间
	 */
	private String endTime;
	/**
	 * 参数人数
	 */
	private Integer userNum;
	/**
	 * 参会人员
	 */
	private String users;
	/**
	 * 会议主题
	 */
	private String meetingTheme;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 预约状态
	 */
	private String status;
	/**
	 * 预留字段1
	 */
	private String data1;
	/**
	 * 预留字段2
	 */
	private String data2;

}
