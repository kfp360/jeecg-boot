package org.jeecg.modules.demo.mng.service;

import org.jeecg.modules.demo.mng.entity.ConstrAttach;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: constr_attach
 * @Author: jeecg-boot
 * @Date:   2022-03-19
 * @Version: V1.0
 */
public interface IConstrAttachService extends IService<ConstrAttach> {

	public List<ConstrAttach> selectByMainId(String mainId);
}
