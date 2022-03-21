package org.jeecg.modules.demo.mng.service.impl;

import org.jeecg.modules.demo.mng.entity.ConstrArea;
import org.jeecg.modules.demo.mng.mapper.ConstrAreaMapper;
import org.jeecg.modules.demo.mng.service.IConstrAreaService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: constr_area
 * @Author: jeecg-boot
 * @Date:   2022-03-19
 * @Version: V1.0
 */
@Service
public class ConstrAreaServiceImpl extends ServiceImpl<ConstrAreaMapper, ConstrArea> implements IConstrAreaService {
	
	@Autowired
	private ConstrAreaMapper constrAreaMapper;
	
	@Override
	public List<ConstrArea> selectByMainId(String mainId) {
		return constrAreaMapper.selectByMainId(mainId);
	}
}
