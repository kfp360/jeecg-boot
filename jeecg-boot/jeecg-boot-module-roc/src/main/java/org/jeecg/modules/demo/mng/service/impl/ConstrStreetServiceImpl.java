package org.jeecg.modules.demo.mng.service.impl;

import org.jeecg.modules.demo.mng.entity.ConstrStreet;
import org.jeecg.modules.demo.mng.mapper.ConstrStreetMapper;
import org.jeecg.modules.demo.mng.service.IConstrStreetService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: constr_street
 * @Author: jeecg-boot
 * @Date:   2022-03-19
 * @Version: V1.0
 */
@Service
public class ConstrStreetServiceImpl extends ServiceImpl<ConstrStreetMapper, ConstrStreet> implements IConstrStreetService {
	
	@Autowired
	private ConstrStreetMapper constrStreetMapper;
	
	@Override
	public List<ConstrStreet> selectByMainId(String mainId) {
		return constrStreetMapper.selectByMainId(mainId);
	}
}
