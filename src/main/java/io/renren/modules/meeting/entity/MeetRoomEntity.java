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
@TableName("meet_room")
public class MeetRoomEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	@TableId
	private Integer roomId;
	/**
	 * 会议室
	 */
	private String roomName;
	/**
	 * 所属区域
	 */
	private String roomArea;
	/**
	 * 会议室地点
	 */
	private String location;
	/**
	 * 设备
	 */
	private String equipment;
	/**
	 * 容纳人数
	 */
	private Integer capacity;
	/**
	 * 会议室负责人
	 */
	private String roomLeader;
	/**
	 * 负责人联系方式
	 */
	private String roomPhone;
	/**
	 * 预留字段1
	 */
	private String data1;
	/**
	 * 预留字段2
	 */
	private String data2;

}
