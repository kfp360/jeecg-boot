package org.jeecg.modules.demo.mng.mapper;

import java.util.List;
import org.jeecg.modules.demo.mng.entity.ConstrStreet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: constr_street
 * @Author: jeecg-boot
 * @Date:   2022-03-19
 * @Version: V1.0
 */
public interface ConstrStreetMapper extends BaseMapper<ConstrStreet> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<ConstrStreet> selectByMainId(@Param("mainId") String mainId);
}
