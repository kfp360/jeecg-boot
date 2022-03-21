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
 * @Description: constr_area
 * @Author: jeecg-boot
 * @Date:   2022-03-19
 * @Version: V1.0
 */
@ApiModel(value="constr_area对象", description="constr_area")
@Data
@TableName("constr_area")
public class ConstrArea implements Serializable {
    private static final long serialVersionUID = 1L;

	/**施工位置自增id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "施工位置自增id")
    private java.lang.String id;
	/**施工记录id*/
    @ApiModelProperty(value = "施工记录id")
    private java.lang.String constrId;
	/**施工类型(POINT=点施工,LINE=线路施工,POLYGON=面)*/
	@Excel(name = "施工类型(POINT=点施工,LINE=线路施工,POLYGON=面)", width = 15)
    @ApiModelProperty(value = "施工类型(POINT=点施工,LINE=线路施工,POLYGON=面)")
    private java.lang.String areaType;
	/**施工位置经度*/
	@Excel(name = "施工位置经度", width = 15)
    @ApiModelProperty(value = "施工位置经度")
    private java.lang.Double longitude;
	/**施工位置纬度*/
	@Excel(name = "施工位置纬度", width = 15)
    @ApiModelProperty(value = "施工位置纬度")
    private java.lang.Double latitude;
	/**施工路径坐标单位*/
	@Excel(name = "施工路径坐标单位", width = 15)
    @ApiModelProperty(value = "施工路径坐标单位")
    private java.lang.String route;
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
