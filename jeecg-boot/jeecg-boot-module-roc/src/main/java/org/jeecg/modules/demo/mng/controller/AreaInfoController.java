package org.jeecg.modules.demo.mng.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.mng.entity.AreaInfo;
import org.jeecg.modules.demo.mng.service.IAreaInfoService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: area_info
 * @Author: jeecg-boot
 * @Date:   2022-03-20
 * @Version: V1.0
 */
@Api(tags="area_info")
@RestController
@RequestMapping("/mng/areaInfo")
@Slf4j
public class AreaInfoController extends JeecgController<AreaInfo, IAreaInfoService>{
	@Autowired
	private IAreaInfoService areaInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param areaInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "area_info-分页列表查询")
	@ApiOperation(value="area_info-分页列表查询", notes="area_info-分页列表查询")
	@GetMapping(value = "/rootList")
	public Result<?> queryPageList(AreaInfo areaInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		String hasQuery = req.getParameter("hasQuery");
        if(hasQuery != null && "true".equals(hasQuery)){
            QueryWrapper<AreaInfo> queryWrapper =  QueryGenerator.initQueryWrapper(areaInfo, req.getParameterMap());
            List<AreaInfo> list = areaInfoService.queryTreeListNoPage(queryWrapper);
            IPage<AreaInfo> pageList = new Page<>(1, 10, list.size());
            pageList.setRecords(list);
            return Result.OK(pageList);
        }else{
            String parentId = areaInfo.getParentId();
            if (oConvertUtils.isEmpty(parentId)) {
                parentId = "0";
            }
            areaInfo.setParentId(null);
            QueryWrapper<AreaInfo> queryWrapper = QueryGenerator.initQueryWrapper(areaInfo, req.getParameterMap());
            // 使用 eq 防止模糊查询
            queryWrapper.eq("parent_id", parentId);
            Page<AreaInfo> page = new Page<AreaInfo>(pageNo, pageSize);
            IPage<AreaInfo> pageList = areaInfoService.page(page, queryWrapper);
            return Result.OK(pageList);
        }
	}

	 /**
      * 获取子数据
      * @param areaInfo
      * @param req
      * @return
      */
	@AutoLog(value = "area_info-获取子数据")
	@ApiOperation(value="area_info-获取子数据", notes="area_info-获取子数据")
	@GetMapping(value = "/childList")
	public Result<?> queryPageList(AreaInfo areaInfo,HttpServletRequest req) {
		QueryWrapper<AreaInfo> queryWrapper = QueryGenerator.initQueryWrapper(areaInfo, req.getParameterMap());
		List<AreaInfo> list = areaInfoService.list(queryWrapper);
		IPage<AreaInfo> pageList = new Page<>(1, 10, list.size());
        pageList.setRecords(list);
		return Result.OK(pageList);
	}

    /**
      * 批量查询子节点
      * @param parentIds 父ID（多个采用半角逗号分割）
      * @return 返回 IPage
      * @param parentIds
      * @return
      */
	@AutoLog(value = "area_info-批量获取子数据")
    @ApiOperation(value="area_info-批量获取子数据", notes="area_info-批量获取子数据")
    @GetMapping("/getChildListBatch")
    public Result getChildListBatch(@RequestParam("parentIds") String parentIds) {
        try {
            QueryWrapper<AreaInfo> queryWrapper = new QueryWrapper<>();
            List<String> parentIdList = Arrays.asList(parentIds.split(","));
            queryWrapper.in("parent_id", parentIdList);
            List<AreaInfo> list = areaInfoService.list(queryWrapper);
            IPage<AreaInfo> pageList = new Page<>(1, 10, list.size());
            pageList.setRecords(list);
            return Result.OK(pageList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error("批量查询子节点失败：" + e.getMessage());
        }
    }
	
	/**
	 *   添加
	 *
	 * @param areaInfo
	 * @return
	 */
	@AutoLog(value = "area_info-添加")
	@ApiOperation(value="area_info-添加", notes="area_info-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AreaInfo areaInfo) {
		areaInfoService.addAreaInfo(areaInfo);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param areaInfo
	 * @return
	 */
	@AutoLog(value = "area_info-编辑")
	@ApiOperation(value="area_info-编辑", notes="area_info-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<?> edit(@RequestBody AreaInfo areaInfo) {
		areaInfoService.updateAreaInfo(areaInfo);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "area_info-通过id删除")
	@ApiOperation(value="area_info-通过id删除", notes="area_info-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		areaInfoService.deleteAreaInfo(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "area_info-批量删除")
	@ApiOperation(value="area_info-批量删除", notes="area_info-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.areaInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "area_info-通过id查询")
	@ApiOperation(value="area_info-通过id查询", notes="area_info-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		AreaInfo areaInfo = areaInfoService.getById(id);
		if(areaInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(areaInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param areaInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AreaInfo areaInfo) {
		return super.exportXls(request, areaInfo, AreaInfo.class, "area_info");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
		return super.importExcel(request, response, AreaInfo.class);
    }

}
