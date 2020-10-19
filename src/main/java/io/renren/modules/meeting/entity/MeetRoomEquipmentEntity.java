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
@TableName("meet_room_equipment")
public class MeetRoomEquipmentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	@TableId
	private Integer id;
	/**
	 * 设备名称
	 */
	private String name;
	/**
	 * 设备信息
	 */
	private String message;
	/**
	 * 预留字段1
	 */
	private String data1;
	/**
	 * 预留字段2
	 */
	private String data2;

}
