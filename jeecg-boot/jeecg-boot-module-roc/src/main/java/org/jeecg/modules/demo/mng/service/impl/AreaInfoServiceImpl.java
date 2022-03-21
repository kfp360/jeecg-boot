package org.jeecg.modules.demo.mng.service.impl;

import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.mng.entity.AreaInfo;
import org.jeecg.modules.demo.mng.mapper.AreaInfoMapper;
import org.jeecg.modules.demo.mng.service.IAreaInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: area_info
 * @Author: jeecg-boot
 * @Date:   2022-03-20
 * @Version: V1.0
 */
@Service
public class AreaInfoServiceImpl extends ServiceImpl<AreaInfoMapper, AreaInfo> implements IAreaInfoService {

	@Override
	public void addAreaInfo(AreaInfo areaInfo) {
	   //新增时设置hasChild为0
	    areaInfo.setHasChild(IAreaInfoService.NOCHILD);
		if(oConvertUtils.isEmpty(areaInfo.getParentId())){
			areaInfo.setParentId(IAreaInfoService.ROOT_PID_VALUE);
		}else{
			//如果当前节点父ID不为空 则设置父节点的hasChildren 为1
			AreaInfo parent = baseMapper.selectById(areaInfo.getParentId());
			if(parent!=null && !"1".equals(parent.getHasChild())){
				parent.setHasChild("1");
				baseMapper.updateById(parent);
			}
		}
		baseMapper.insert(areaInfo);
	}
	
	@Override
	public void updateAreaInfo(AreaInfo areaInfo) {
		AreaInfo entity = this.getById(areaInfo.getId());
		if(entity==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		String old_pid = entity.getParentId();
		String new_pid = areaInfo.getParentId();
		if(!old_pid.equals(new_pid)) {
			updateOldParentNode(old_pid);
			if(oConvertUtils.isEmpty(new_pid)){
				areaInfo.setParentId(IAreaInfoService.ROOT_PID_VALUE);
			}
			if(!IAreaInfoService.ROOT_PID_VALUE.equals(areaInfo.getParentId())) {
				baseMapper.updateTreeNodeStatus(areaInfo.getParentId(), IAreaInfoService.HASCHILD);
			}
		}
		baseMapper.updateById(areaInfo);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteAreaInfo(String id) throws JeecgBootException {
		//查询选中节点下所有子节点一并删除
        id = this.queryTreeChildIds(id);
        if(id.indexOf(",")>0) {
            StringBuffer sb = new StringBuffer();
            String[] idArr = id.split(",");
            for (String idVal : idArr) {
                if(idVal != null){
                    AreaInfo areaInfo = this.getById(idVal);
                    String pidVal = areaInfo.getParentId();
                    //查询此节点上一级是否还有其他子节点
                    List<AreaInfo> dataList = baseMapper.selectList(new QueryWrapper<AreaInfo>().eq("parent_id", pidVal).notIn("id",Arrays.asList(idArr)));
                    if((dataList == null || dataList.size()==0) && !Arrays.asList(idArr).contains(pidVal)
                            && !sb.toString().contains(pidVal)){
                        //如果当前节点原本有子节点 现在木有了，更新状态
                        sb.append(pidVal).append(",");
                    }
                }
            }
            //批量删除节点
            baseMapper.deleteBatchIds(Arrays.asList(idArr));
            //修改已无子节点的标识
            String[] pidArr = sb.toString().split(",");
            for(String pid : pidArr){
                this.updateOldParentNode(pid);
            }
        }else{
            AreaInfo areaInfo = this.getById(id);
            if(areaInfo==null) {
                throw new JeecgBootException("未找到对应实体");
            }
            updateOldParentNode(areaInfo.getParentId());
            baseMapper.deleteById(id);
        }
	}
	
	@Override
    public List<AreaInfo> queryTreeListNoPage(QueryWrapper<AreaInfo> queryWrapper) {
        List<AreaInfo> dataList = baseMapper.selectList(queryWrapper);
        List<AreaInfo> mapList = new ArrayList<>();
        for(AreaInfo data : dataList){
            String pidVal = data.getParentId();
            //递归查询子节点的根节点
            if(pidVal != null && !"0".equals(pidVal)){
                AreaInfo rootVal = this.getTreeRoot(pidVal);
                if(rootVal != null && !mapList.contains(rootVal)){
                    mapList.add(rootVal);
                }
            }else{
                if(!mapList.contains(data)){
                    mapList.add(data);
                }
            }
        }
        return mapList;
    }
	
	/**
	 * 根据所传pid查询旧的父级节点的子节点并修改相应状态值
	 * @param pid
	 */
	private void updateOldParentNode(String pid) {
		if(!IAreaInfoService.ROOT_PID_VALUE.equals(pid)) {
			Integer count = baseMapper.selectCount(new QueryWrapper<AreaInfo>().eq("parent_id", pid));
			if(count==null || count<=1) {
				baseMapper.updateTreeNodeStatus(pid, IAreaInfoService.NOCHILD);
			}
		}
	}

	/**
     * 递归查询节点的根节点
     * @param pidVal
     * @return
     */
    private AreaInfo getTreeRoot(String pidVal){
        AreaInfo data =  baseMapper.selectById(pidVal);
        if(data != null && !"0".equals(data.getParentId())){
            return this.getTreeRoot(data.getParentId());
        }else{
            return data;
        }
    }

    /**
     * 根据id查询所有子节点id
     * @param ids
     * @return
     */
    private String queryTreeChildIds(String ids) {
        //获取id数组
        String[] idArr = ids.split(",");
        StringBuffer sb = new StringBuffer();
        for (String pidVal : idArr) {
            if(pidVal != null){
                if(!sb.toString().contains(pidVal)){
                    if(sb.toString().length() > 0){
                        sb.append(",");
                    }
                    sb.append(pidVal);
                    this.getTreeChildIds(pidVal,sb);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 递归查询所有子节点
     * @param pidVal
     * @param sb
     * @return
     */
    private StringBuffer getTreeChildIds(String pidVal,StringBuffer sb){
        List<AreaInfo> dataList = baseMapper.selectList(new QueryWrapper<AreaInfo>().eq("parent_id", pidVal));
        if(dataList != null && dataList.size()>0){
            for(AreaInfo tree : dataList) {
                if(!sb.toString().contains(tree.getId())){
                    sb.append(",").append(tree.getId());
                }
                this.getTreeChildIds(tree.getId(),sb);
            }
        }
        return sb;
    }

}
