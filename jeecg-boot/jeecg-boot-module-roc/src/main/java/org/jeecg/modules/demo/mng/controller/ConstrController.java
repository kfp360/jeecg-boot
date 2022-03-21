package org.jeecg.modules.demo.mng.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.mng.entity.ConstrArea;
import org.jeecg.modules.demo.mng.entity.ConstrAttach;
import org.jeecg.modules.demo.mng.entity.ConstrStreet;
import org.jeecg.modules.demo.mng.entity.Constr;
import org.jeecg.modules.demo.mng.vo.ConstrPage;
import org.jeecg.modules.demo.mng.service.IConstrService;
import org.jeecg.modules.demo.mng.service.IConstrAreaService;
import org.jeecg.modules.demo.mng.service.IConstrAttachService;
import org.jeecg.modules.demo.mng.service.IConstrStreetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: constr
 * @Author: jeecg-boot
 * @Date:   2022-03-19
 * @Version: V1.0
 */
@Api(tags="constr")
@RestController
@RequestMapping("/mng/constr")
@Slf4j
public class ConstrController {
	@Autowired
	private IConstrService constrService;
	@Autowired
	private IConstrAreaService constrAreaService;
	@Autowired
	private IConstrAttachService constrAttachService;
	@Autowired
	private IConstrStreetService constrStreetService;
	
	/**
	 * 分页列表查询
	 *
	 * @param constr
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "constr-分页列表查询")
	@ApiOperation(value="constr-分页列表查询", notes="constr-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Constr constr,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Constr> queryWrapper = QueryGenerator.initQueryWrapper(constr, req.getParameterMap());
		Page<Constr> page = new Page<Constr>(pageNo, pageSize);
		IPage<Constr> pageList = constrService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param constrPage
	 * @return
	 */
	@AutoLog(value = "constr-添加")
	@ApiOperation(value="constr-添加", notes="constr-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ConstrPage constrPage) {
		Constr constr = new Constr();
		BeanUtils.copyProperties(constrPage, constr);
		constrService.saveMain(constr, constrPage.getConstrAreaList(),constrPage.getConstrAttachList(),constrPage.getConstrStreetList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param constrPage
	 * @return
	 */
	@AutoLog(value = "constr-编辑")
	@ApiOperation(value="constr-编辑", notes="constr-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<?> edit(@RequestBody ConstrPage constrPage) {
		Constr constr = new Constr();
		BeanUtils.copyProperties(constrPage, constr);
		Constr constrEntity = constrService.getById(constr.getId());
		if(constrEntity==null) {
			return Result.error("未找到对应数据");
		}
		constrService.updateMain(constr, constrPage.getConstrAreaList(),constrPage.getConstrAttachList(),constrPage.getConstrStreetList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "constr-通过id删除")
	@ApiOperation(value="constr-通过id删除", notes="constr-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		constrService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "constr-批量删除")
	@ApiOperation(value="constr-批量删除", notes="constr-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.constrService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "constr-通过id查询")
	@ApiOperation(value="constr-通过id查询", notes="constr-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Constr constr = constrService.getById(id);
		if(constr==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(constr);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "constr_area通过主表ID查询")
	@ApiOperation(value="constr_area主表ID查询", notes="constr_area-通主表ID查询")
	@GetMapping(value = "/queryConstrAreaByMainId")
	public Result<?> queryConstrAreaListByMainId(@RequestParam(name="id",required=true) String id) {
		List<ConstrArea> constrAreaList = constrAreaService.selectByMainId(id);
		return Result.OK(constrAreaList);
	}
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "constr_attach通过主表ID查询")
	@ApiOperation(value="constr_attach主表ID查询", notes="constr_attach-通主表ID查询")
	@GetMapping(value = "/queryConstrAttachByMainId")
	public Result<?> queryConstrAttachListByMainId(@RequestParam(name="id",required=true) String id) {
		QueryWrapper<ConstrAttach> entityWrapper = new QueryWrapper();
		entityWrapper.eq("type", "ATTACH");
		entityWrapper.eq("key_id", id);
		List<ConstrAttach> constrAttachList = this.constrAttachService.list(entityWrapper);
		return Result.OK(constrAttachList);
	}

	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "constr_image通过主表ID查询")
	 @ApiOperation(value="constr_image主表ID查询", notes="constr_image-通主表ID查询")
	 @GetMapping(value = "/queryConstrImageByMainId")
	 public Result<?> queryConstrImageListByMainId(@RequestParam(name="id",required=true) String id) {
		 QueryWrapper<ConstrAttach> entityWrapper = new QueryWrapper();
		 entityWrapper.eq("type", "IMAGE");
		 entityWrapper.eq("key_id", id);
		 List<ConstrAttach> constrAttachList = this.constrAttachService.list(entityWrapper);
		 return Result.OK(constrAttachList);
	 }
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "constr_street通过主表ID查询")
	@ApiOperation(value="constr_street主表ID查询", notes="constr_street-通主表ID查询")
	@GetMapping(value = "/queryConstrStreetByMainId")
	public Result<?> queryConstrStreetListByMainId(@RequestParam(name="id",required=true) String id) {
		List<ConstrStreet> constrStreetList = constrStreetService.selectByMainId(id);
		return Result.OK(constrStreetList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param constr
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Constr constr) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<Constr> queryWrapper = QueryGenerator.initQueryWrapper(constr, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<Constr> queryList = constrService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<Constr> constrList = new ArrayList<Constr>();
      if(oConvertUtils.isEmpty(selections)) {
          constrList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          constrList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<ConstrPage> pageList = new ArrayList<ConstrPage>();
      for (Constr main : constrList) {
          ConstrPage vo = new ConstrPage();
          BeanUtils.copyProperties(main, vo);
          List<ConstrArea> constrAreaList = constrAreaService.selectByMainId(main.getId());
          vo.setConstrAreaList(constrAreaList);
          List<ConstrAttach> constrAttachList = constrAttachService.selectByMainId(main.getId());
          vo.setConstrAttachList(constrAttachList);
          List<ConstrStreet> constrStreetList = constrStreetService.selectByMainId(main.getId());
          vo.setConstrStreetList(constrStreetList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "constr列表");
      mv.addObject(NormalExcelConstants.CLASS, ConstrPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("constr数据", "导出人:"+sysUser.getRealname(), "constr"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
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
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<ConstrPage> list = ExcelImportUtil.importExcel(file.getInputStream(), ConstrPage.class, params);
              for (ConstrPage page : list) {
                  Constr po = new Constr();
                  BeanUtils.copyProperties(page, po);
                  constrService.saveMain(po, page.getConstrAreaList(),page.getConstrAttachList(),page.getConstrStreetList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
