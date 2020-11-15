/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysRoleService;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import org.springframework.util.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.io.InputStream;
import java.util.*;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysUserService sysUserService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String username = (String)params.get("username");
		Long createUserId = (Long)params.get("createUserId");

		IPage<SysUserEntity> page = this.page(
			new Query<SysUserEntity>().getPage(params),
			new QueryWrapper<SysUserEntity>()
					.like(StringUtils.isNotBlank(username),"thename", username)
				.eq(createUserId != null,"create_user_id", createUserId)
		);

		return new PageUtils(page);
	}

	@Override
	public List<String> queryAllPerms(Long userId) {
		return baseMapper.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return baseMapper.queryAllMenuId(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return baseMapper.queryByUserName(username);
	}

	@Override
	@Transactional
	public void saveUser(SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		this.save(user);
		
		//检查角色是否越权
		checkRole(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void update(SysUserEntity user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
		}
		this.updateById(user);
		
		//检查角色是否越权
		checkRole(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	public void deleteBatch(Long[] userId) {
		this.removeByIds(Arrays.asList(userId));
	}

	@Override
	public boolean updatePassword(Long userId, String password, String newPassword) {
		SysUserEntity userEntity = new SysUserEntity();
		userEntity.setPassword(newPassword);
		return this.update(userEntity,
				new QueryWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
	}

	@Override
	public boolean batchImport(String fileName, MultipartFile file) throws Exception {
		System.out.println("1111111");
		boolean notNull = false;
		List<SysUserEntity> userList = new ArrayList<SysUserEntity>();
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			throw new Exception("上传文件格式不正确");
		}
		boolean isExcel2003 = true;
		if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
			isExcel2003 = false;
		}
		InputStream is = file.getInputStream();
		Workbook wb = null;
		if (isExcel2003) {
			wb = new HSSFWorkbook(is);
		} else {
			wb = new XSSFWorkbook(is);
		}
		Sheet sheet = wb.getSheetAt(0);
		if(sheet!=null){
			notNull = true;
		}
		SysUserEntity user;
		System.out.println("sheet"+sheet.getLastRowNum());

		List<Long> sign=new ArrayList<>();
		sign.add(2l);
		for (int r = 1; r <= sheet.getLastRowNum(); r++) {
			Row row = sheet.getRow(r);
			if (row == null){
				continue;
			}

			user = new SysUserEntity();

			if( row.getCell(0).getCellType() !=1){

				throw new Exception("导入失败(第"+(r+1)+"行,姓名请设为文本格式)");
			}
			String name = row.getCell(0).getStringCellValue();
			if(name == null || name.isEmpty()){
				throw new Exception("导入失败(第"+(r+1)+"行,姓名未填写)");
			}


			String dep;
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String phone = row.getCell(1).getStringCellValue();

			if(phone==null || phone.isEmpty()){
				throw new Exception("导入失败(第"+(r+1)+"行,电话未填写)");
			}

			if (row.getCell(2).getCellType() != 1) {
				throw new Exception("导入失败(第" + (r + 1) + "行,部门请设为文本格式)");
			} else {
				dep = row.getCell(2).getStringCellValue();
			}
//用户名
			String username = row.getCell(3).getStringCellValue();
			if(username==null || username.isEmpty()){
				throw new Exception("导入失败(第"+(r+3)+"行,用户名未填写)");
			}

			user.setThename(name);
//			user.setEmail(name); // 姓名
			user.setMobile(phone); //电话
			user.setDepartment(dep);//部门
			user.setStatus(1);//状态
//			UUID uuid = UUID.randomUUID();
			user.setUsername(username); // 用户名
			user.setPassword("123"); // 用户名
//			String salt = RandomStringUtils.randomAlphanumeric(20);
//			user.setSalt(salt);
//			user.setPassword(new Sha256Hash("123", salt).toHex());
			//user.setPassword(new Sha256Hash("123", getUser().getSalt()).toHex(););//密码
			user.setCreateTime(new Date()); //创建时间

			user.setCreateUserId(1l);
			userList.add(user);




//			for(int i=0;i<userList.size();i++)
//			{
				sysUserService.saveUser(user);
//				sysUserRoleService.saveOrUpdate(sysUserService.queryByUserName(userList.get(i).getUsername()).getUserId(), sign);
//			}
			sysUserRoleService.saveOrUpdate(user.getUserId(), sign);
		}
//		notNull = this.saveBatch(userList);
		return true;
	}

	/**
	 * 检查角色是否越权
	 */
	private void checkRole(SysUserEntity user){
		if(user.getRoleIdList() == null || user.getRoleIdList().size() == 0){
			return;
		}
		//如果不是超级管理员，则需要判断用户的角色是否自己创建
		if(user.getCreateUserId() == Constant.SUPER_ADMIN){
			return ;
		}
		
		//查询用户创建的角色列表
		List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

		//判断是否越权
//		if(!roleIdList.containsAll(user.getRoleIdList())){
//			throw new RRException("新增用户所选角色，不是本人创建");
//		}
	}
}