package org.jeecg.modules.demo.mng.service;

import org.jeecg.modules.demo.mng.entity.ConstrArea;
import org.jeecg.modules.demo.mng.entity.ConstrAttach;
import org.jeecg.modules.demo.mng.entity.ConstrStreet;
import org.jeecg.modules.demo.mng.entity.Constr;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: constr
 * @Author: jeecg-boot
 * @Date:   2022-03-19
 * @Version: V1.0
 */
public interface IConstrService extends IService<Constr> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(Constr constr,List<ConstrArea> constrAreaList,List<ConstrAttach> constrAttachList,List<ConstrStreet> constrStreetList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(Constr constr,List<ConstrArea> constrAreaList,List<ConstrAttach> constrAttachList,List<ConstrStreet> constrStreetList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
