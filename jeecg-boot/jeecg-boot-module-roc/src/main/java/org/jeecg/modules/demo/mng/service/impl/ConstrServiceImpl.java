package org.jeecg.modules.demo.mng.service.impl;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.demo.mng.entity.Constr;
import org.jeecg.modules.demo.mng.entity.ConstrArea;
import org.jeecg.modules.demo.mng.entity.ConstrAttach;
import org.jeecg.modules.demo.mng.entity.ConstrStreet;
import org.jeecg.modules.demo.mng.mapper.ConstrAreaMapper;
import org.jeecg.modules.demo.mng.mapper.ConstrAttachMapper;
import org.jeecg.modules.demo.mng.mapper.ConstrStreetMapper;
import org.jeecg.modules.demo.mng.mapper.ConstrMapper;
import org.jeecg.modules.demo.mng.service.IConstrService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: constr
 * @Author: jeecg-boot
 * @Date:   2022-03-19
 * @Version: V1.0
 */
@Service
public class ConstrServiceImpl extends ServiceImpl<ConstrMapper, Constr> implements IConstrService {

	@Autowired
	private ConstrMapper constrMapper;
	@Autowired
	private ConstrAreaMapper constrAreaMapper;
	@Autowired
	private ConstrAttachMapper constrAttachMapper;
	@Autowired
	private ConstrStreetMapper constrStreetMapper;
	
	@Override
	@Transactional
	public void saveMain(Constr constr, List<ConstrArea> constrAreaList,List<ConstrAttach> constrAttachList,List<ConstrStreet> constrStreetList) {

		LoginUser sysUser =  (LoginUser)SecurityUtils.getSubject().getPrincipal();

		constr.setPublishers(sysUser.getRealname());
		constr.setPublishersContact(sysUser.getPhone());
		constr.setOwnerDeptName(sysUser.getDepartIds());

		constrMapper.insert(constr);
		if(constrAreaList!=null && constrAreaList.size()>0) {
			for(ConstrArea entity:constrAreaList) {
				//外键设置
				entity.setConstrId(constr.getId());
				constrAreaMapper.insert(entity);
			}
		}
		if(constrAttachList!=null && constrAttachList.size()>0) {
			for(ConstrAttach entity:constrAttachList) {
				//外键设置
				entity.setKeyId(constr.getId());
				constrAttachMapper.insert(entity);
			}
		}
		if(constrStreetList!=null && constrStreetList.size()>0) {
			for(ConstrStreet entity:constrStreetList) {
				//外键设置
				entity.setConstrId(constr.getId());
				constrStreetMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(Constr constr,List<ConstrArea> constrAreaList,List<ConstrAttach> constrAttachList,List<ConstrStreet> constrStreetList) {
		constrMapper.updateById(constr);
		
		//1.先删除子表数据
		constrAreaMapper.deleteByMainId(constr.getId());
		constrAttachMapper.deleteByMainId(constr.getId());
		constrStreetMapper.deleteByMainId(constr.getId());
		
		//2.子表数据重新插入
		if(constrAreaList!=null && constrAreaList.size()>0) {
			for(ConstrArea entity:constrAreaList) {
				//外键设置
				entity.setConstrId(constr.getId());
				constrAreaMapper.insert(entity);
			}
		}
		if(constrAttachList!=null && constrAttachList.size()>0) {
			for(ConstrAttach entity:constrAttachList) {
				//外键设置
				entity.setKeyId(constr.getId());
				constrAttachMapper.insert(entity);
			}
		}
		if(constrStreetList!=null && constrStreetList.size()>0) {
			for(ConstrStreet entity:constrStreetList) {
				//外键设置
				entity.setConstrId(constr.getId());
				constrStreetMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		constrAreaMapper.deleteByMainId(id);
		constrAttachMapper.deleteByMainId(id);
		constrStreetMapper.deleteByMainId(id);
		constrMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			constrAreaMapper.deleteByMainId(id.toString());
			constrAttachMapper.deleteByMainId(id.toString());
			constrStreetMapper.deleteByMainId(id.toString());
			constrMapper.deleteById(id);
		}
	}
	
}
