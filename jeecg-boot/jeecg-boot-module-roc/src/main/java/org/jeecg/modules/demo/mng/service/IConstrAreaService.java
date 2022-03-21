package org.jeecg.modules.demo.mng.service;

import org.jeecg.modules.demo.mng.entity.ConstrArea;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: constr_area
 * @Author: jeecg-boot
 * @Date:   2022-03-19
 * @Version: V1.0
 */
public interface IConstrAreaService extends IService<ConstrArea> {

	public List<ConstrArea> selectByMainId(String mainId);
}
