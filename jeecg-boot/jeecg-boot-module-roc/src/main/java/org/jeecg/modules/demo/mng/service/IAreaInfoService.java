package org.jeecg.modules.demo.mng.service;

import org.jeecg.modules.demo.mng.entity.AreaInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.exception.JeecgBootException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;

/**
 * @Description: area_info
 * @Author: jeecg-boot
 * @Date:   2022-03-20
 * @Version: V1.0
 */
public interface IAreaInfoService extends IService<AreaInfo> {

	/**根节点父ID的值*/
	public static final String ROOT_PID_VALUE = "0";
	
	/**树节点有子节点状态值*/
	public static final String HASCHILD = "1";
	
	/**树节点无子节点状态值*/
	public static final String NOCHILD = "0";

	/**新增节点*/
	void addAreaInfo(AreaInfo areaInfo);
	
	/**修改节点*/
	void updateAreaInfo(AreaInfo areaInfo) throws JeecgBootException;
	
	/**删除节点*/
	void deleteAreaInfo(String id) throws JeecgBootException;

	/**查询所有数据，无分页*/
    List<AreaInfo> queryTreeListNoPage(QueryWrapper<AreaInfo> queryWrapper);

}
