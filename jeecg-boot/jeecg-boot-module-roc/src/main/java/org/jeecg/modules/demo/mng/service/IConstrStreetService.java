package org.jeecg.modules.demo.mng.service;

import org.jeecg.modules.demo.mng.entity.ConstrStreet;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: constr_street
 * @Author: jeecg-boot
 * @Date:   2022-03-19
 * @Version: V1.0
 */
public interface IConstrStreetService extends IService<ConstrStreet> {

	public List<ConstrStreet> selectByMainId(String mainId);
}
