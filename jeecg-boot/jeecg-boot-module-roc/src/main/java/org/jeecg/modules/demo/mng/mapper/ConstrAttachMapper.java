package org.jeecg.modules.demo.mng.mapper;

import java.util.List;
import org.jeecg.modules.demo.mng.entity.ConstrAttach;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: constr_attach
 * @Author: jeecg-boot
 * @Date:   2022-03-19
 * @Version: V1.0
 */
public interface ConstrAttachMapper extends BaseMapper<ConstrAttach> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<ConstrAttach> selectByMainId(@Param("mainId") String mainId);
}
