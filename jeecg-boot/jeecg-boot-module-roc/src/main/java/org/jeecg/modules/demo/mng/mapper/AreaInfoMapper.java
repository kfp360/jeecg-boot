package org.jeecg.modules.demo.mng.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.mng.entity.AreaInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: area_info
 * @Author: jeecg-boot
 * @Date:   2022-03-20
 * @Version: V1.0
 */
public interface AreaInfoMapper extends BaseMapper<AreaInfo> {

	/**
	 * 编辑节点状态
	 * @param id
	 * @param status
	 */
	void updateTreeNodeStatus(@Param("id") String id,@Param("status") String status);

}
