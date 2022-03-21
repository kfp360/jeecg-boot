package org.jeecg.modules.demo.mng.service.impl;

import org.jeecg.modules.demo.mng.entity.ConstrAttach;
import org.jeecg.modules.demo.mng.mapper.ConstrAttachMapper;
import org.jeecg.modules.demo.mng.service.IConstrAttachService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: constr_attach
 * @Author: jeecg-boot
 * @Date:   2022-03-19
 * @Version: V1.0
 */
@Service
public class ConstrAttachServiceImpl extends ServiceImpl<ConstrAttachMapper, ConstrAttach> implements IConstrAttachService {
	
	@Autowired
	private ConstrAttachMapper constrAttachMapper;
	
	@Override
	public List<ConstrAttach> selectByMainId(String mainId) {
		return constrAttachMapper.selectByMainId(mainId);
	}
}
