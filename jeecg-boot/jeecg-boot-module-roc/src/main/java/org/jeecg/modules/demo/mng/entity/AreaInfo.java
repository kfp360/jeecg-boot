package org.jeecg.modules.demo.mng.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: area_info
 * @Author: jeecg-boot
 * @Date:   2022-03-20
 * @Version: V1.0
 */
@Data
@TableName("area_info")
@ApiModel(value="area_info对象", description="area_info")
public class AreaInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**自增主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "自增主键")
    private java.lang.String id;
	/**区域code,街道和区code一致*/
	@Excel(name = "区域code", width = 15)
    @ApiModelProperty(value = "区域code,街道和区code一致")
    private java.lang.String areaCode;
	/**areaName*/
	@Excel(name = "areaName", width = 15)
    @ApiModelProperty(value = "areaName")
    private java.lang.String areaName;
	/**地区等级 1=省 2=市 3=区 4=街道*/
	@Excel(name = "地区等级", width = 15)
    @ApiModelProperty(value = "地区等级 1=省 2=市 3=区 4=街道")
    private java.lang.Integer level;
	/**所属父地区id*/
	@Excel(name = "所属父地区", width = 15)
    @ApiModelProperty(value = "所属父地区id")
    private java.lang.String parentId;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
    private java.lang.String createUserId;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "修改时间")
    private java.util.Date updateTime;
	/**是否有子节点*/
	@Excel(name = "是否有子节点", width = 15, dicCode = "yn")
	@Dict(dicCode = "yn")
    @ApiModelProperty(value = "是否有子节点")
    private java.lang.String hasChild;
}
