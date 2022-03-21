package org.jeecg.modules.demo.mng.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: constr_attach
 * @Author: jeecg-boot
 * @Date:   2022-03-19
 * @Version: V1.0
 */
@ApiModel(value="constr_attach对象", description="constr_attach")
@Data
@TableName("constr_attach")
public class ConstrAttach implements Serializable {
    private static final long serialVersionUID = 1L;

	/**施工附件文件自增id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "施工附件文件自增id")
    private java.lang.String id;
	/**关联主体记录id(施工相关为施工记录id，隐患相关为隐患记录id)*/
    @ApiModelProperty(value = "关联主体记录id(施工相关为施工记录id，隐患相关为隐患记录id)")
    private java.lang.String keyId;
	/**附件类型  IMAGE=疏解图片 ATTACH=施工附件 CLONKIN=施工打卡 REPORT=隐患上报 PROCESS=隐患处理*/
	@Excel(name = "附件类型  IMAGE=疏解图片 ATTACH=施工附件 CLONKIN=施工打卡 REPORT=隐患上报 PROCESS=隐患处理", width = 15)
    @ApiModelProperty(value = "附件类型  IMAGE=疏解图片 ATTACH=施工附件 CLONKIN=施工打卡 REPORT=隐患上报 PROCESS=隐患处理")
    private java.lang.String type;
	/**施工附件url*/
	@Excel(name = "施工附件url", width = 15)
    @ApiModelProperty(value = "施工附件url")
    private java.lang.String attachUrl;
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
}
