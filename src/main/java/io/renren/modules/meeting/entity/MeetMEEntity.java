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
 * @date 2020-10-19 15:58:10
 */
@Data
@TableName("meet_m_e")
public class MeetMEEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 会议室ID
	 */
	private Integer rId;
	/**
	 * 设备ID
	 */
	private Integer eId;

}
