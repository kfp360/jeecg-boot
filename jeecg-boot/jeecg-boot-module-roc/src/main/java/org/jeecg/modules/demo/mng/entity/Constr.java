package org.jeecg.modules.demo.mng.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
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

/**
 * @Description: constr
 * @Author: jeecg-boot
 * @Date:   2022-03-19
 * @Version: V1.0
 */
@ApiModel(value="constr对象", description="constr")
@Data
@TableName("constr")
public class Constr implements Serializable {
    private static final long serialVersionUID = 1L;

	/**施工记录自增id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "施工记录自增id")
    private java.lang.String id;
	/**施工许可证编号*/
	@Excel(name = "施工许可证编号", width = 15)
    @ApiModelProperty(value = "施工许可证编号")
    private java.lang.String licence;
	/**施工许可证照片地址*/
	@Excel(name = "施工许可证照片地址", width = 15)
    @ApiModelProperty(value = "施工许可证照片地址")
    private java.lang.String licenceUrl;
	/**施工类型*/
	@Excel(name = "施工类型", width = 15, dictTable = "constr_type", dicText = "type_name", dicCode = "id")
    @Dict(dictTable = "constr_type", dicText = "type_name", dicCode = "id")
    @ApiModelProperty(value = "施工类型")
    private java.lang.Integer constrType;
	/**施工单位*/
	@Excel(name = "施工单位", width = 15)
    @ApiModelProperty(value = "施工单位")
    private java.lang.String companyName;
	/**施工原因*/
	@Excel(name = "施工原因", width = 15)
    @ApiModelProperty(value = "施工原因")
    private java.lang.String reason;
	/**施工位置*/
	@Excel(name = "施工位置", width = 15)
    @ApiModelProperty(value = "施工位置")
    private java.lang.String position;
	/**占道区域*/
	@Excel(name = "占道区域", width = 15)
    @ApiModelProperty(value = "占道区域")
    private java.lang.String occupiedArea;
	/**占道时间*/
	@Excel(name = "占道时间", width = 15)
    @ApiModelProperty(value = "占道时间")
    private java.lang.String occupiedDays;
	/**占道负责人*/
	@Excel(name = "占道负责人", width = 15)
    @ApiModelProperty(value = "占道负责人")
    private java.lang.String liabler;
	/**占道负责人联系电话*/
	@Excel(name = "占道负责人联系电话", width = 15)
    @ApiModelProperty(value = "占道负责人联系电话")
    private java.lang.String contact;
	/**发布人员*/
	@Excel(name = "发布人员", width = 15)
    @ApiModelProperty(value = "发布人员")
    private java.lang.String publishers;
	/**发布联系人电话*/
	@Excel(name = "发布联系人电话", width = 15)
    @ApiModelProperty(value = "发布联系人电话")
    private java.lang.String publishersContact;
	/**发布人部门名称*/
	@Excel(name = "发布人部门名称", width = 15)
    @ApiModelProperty(value = "发布人部门名称")
    private java.lang.String ownerDeptName;
	/**施工开始时间*/
	@Excel(name = "施工开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "施工开始时间")
    private java.util.Date startTime;
	/**施工结束时间*/
	@Excel(name = "施工结束时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "施工结束时间")
    private java.util.Date endTime;
	/**施工状态*/
	@Excel(name = "施工状态", width = 15)
    @ApiModelProperty(value = "施工状态")
    private java.lang.String status;
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
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
}
