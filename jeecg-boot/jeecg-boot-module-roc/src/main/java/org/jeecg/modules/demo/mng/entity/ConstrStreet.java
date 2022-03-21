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
 * @Description: constr_street
 * @Author: jeecg-boot
 * @Date:   2022-03-19
 * @Version: V1.0
 */
@ApiModel(value="constr_street对象", description="constr_street")
@Data
@TableName("constr_street")
public class ConstrStreet implements Serializable {
    private static final long serialVersionUID = 1L;

	/**自增主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "自增主键")
    private java.lang.String id;
	/**施工id*/
    @ApiModelProperty(value = "施工id")
    private java.lang.String constrId;
	/**省*/
	@Excel(name = "省", width = 15)
    @ApiModelProperty(value = "省")
    private java.lang.Integer provinceId;
	/**城市*/
	@Excel(name = "城市", width = 15)
    @ApiModelProperty(value = "城市")
    private java.lang.Integer municipalId;
	/**区域*/
	@Excel(name = "区域", width = 15)
    @ApiModelProperty(value = "区域")
    private java.lang.Integer regionId;
	/**街道*/
	@Excel(name = "街道", width = 15)
    @ApiModelProperty(value = "街道")
    private java.lang.Integer streetId;
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
