import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import {JVxeTypes,JVxeColumn} from '/@/components/jeecg/JVxeTable/types'
//列表数据
export const columns: BasicColumn[] = [
    {
    title: '施工许可证编号',
    align:"center",
    dataIndex: 'licence'
   },
   {
    title: '施工许可证照片地址',
    align:"center",
    dataIndex: 'licenceUrl',
    customRender:render.renderAvatar,
   },
   {
    title: '施工类型',
    align:"center",
    dataIndex: 'constrType_dictText'
   },
   {
    title: '施工单位',
    align:"center",
    dataIndex: 'companyName'
   },
   {
    title: '施工原因',
    align:"center",
    dataIndex: 'reason'
   },
   {
    title: '施工位置',
    align:"center",
    dataIndex: 'position'
   },
   {
    title: '占道区域',
    align:"center",
    dataIndex: 'occupiedArea'
   },
   {
    title: '占道时间',
    align:"center",
    dataIndex: 'occupiedDays'
   },
   {
    title: '占道负责人',
    align:"center",
    dataIndex: 'liabler'
   },
   {
    title: '占道负责人联系电话',
    align:"center",
    dataIndex: 'contact'
   },
   {
    title: '发布人员',
    align:"center",
    dataIndex: 'publishers'
   },
   {
    title: '发布联系人电话',
    align:"center",
    dataIndex: 'publishersContact'
   },
   {
    title: '发布人部门名称',
    align:"center",
    dataIndex: 'ownerDeptName'
   },
   {
    title: '施工开始时间',
    align:"center",
    dataIndex: 'startTime',
    customRender:({text}) =>{
      return !text?"":(text.length>10?text.substr(0,10):text)
    },
   },
   {
    title: '施工结束时间',
    align:"center",
    dataIndex: 'endTime',
    customRender:({text}) =>{
      return !text?"":(text.length>10?text.substr(0,10):text)
    },
   },
   {
    title: '施工状态',
    align:"center",
    dataIndex: 'status'
   },
   {
    title: '创建时间',
    align:"center",
    sorter: true,
    dataIndex: 'createTime',
    customRender:({text}) =>{
      return !text?"":(text.length>10?text.substr(0,10):text)
    },
   },
   {
    title: '修改时间',
    align:"center",
    sorter: true,
    dataIndex: 'updateTime',
    customRender:({text}) =>{
      return !text?"":(text.length>10?text.substr(0,10):text)
    },
   },
   {
    title: '创建人',
    align:"center",
    sorter: true,
    dataIndex: 'createBy'
   },
   {
    title: '更新人',
    align:"center",
    sorter: true,
    dataIndex: 'updateBy'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '施工许可证编号',
    field: 'licence',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入施工许可证编号!'},
          ];
     },
  },
  {
    label: '施工许可证照片地址',
    field: 'licenceUrl',
     component: 'JImageUpload',
     componentProps:{
      }
  },
  {
    label: '施工类型',
    field: 'constrType',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"constr_type,type_name,id"
     }
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入施工类型!'},
          ];
     },
  },
  {
    label: '施工单位',
    field: 'companyName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入施工单位!'},
          ];
     },
  },
  {
    label: '施工原因',
    field: 'reason',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入施工原因!'},
          ];
     },
  },
  {
    label: '施工位置',
    field: 'position',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入施工位置!'},
          ];
     },
  },
  {
    label: '占道区域',
    field: 'occupiedArea',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入占道区域!'},
          ];
     },
  },
  {
    label: '占道时间',
    field: 'occupiedDays',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入占道时间!'},
          ];
     },
  },
  {
    label: '占道负责人',
    field: 'liabler',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入占道负责人!'},
          ];
     },
  },
  {
    label: '占道负责人联系电话',
    field: 'contact',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入占道负责人联系电话!'},
          ];
     },
  },
  {
    label: '施工开始时间',
    field: 'startTime',
    component: 'DatePicker',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入施工开始时间!'},
          ];
     },
  },
  {
    label: '施工结束时间',
    field: 'endTime',
    component: 'DatePicker',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入施工结束时间!'},
          ];
     },
  },
  {
    label: '施工状态',
    field: 'status',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入施工状态!'},
          ];
     },
  },
];
//子表单数据
//子表表格配置
export const constrAreaColumns: JVxeColumn[] = [
    {
      title: '施工类型(POINT=点施工,LINE=线路施工,POLYGON=面)',
      key: 'areaType',
       type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
      validateRules: [{ required: true, message: '${title}不能为空' }],
    },
    {
      title: '施工位置经度',
      key: 'longitude',
       type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
      validateRules: [{ required: true, message: '${title}不能为空' }],
    },
    {
      title: '施工位置纬度',
      key: 'latitude',
       type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
      validateRules: [{ required: true, message: '${title}不能为空' }],
    },
    {
      title: '施工路径坐标单位',
      key: 'route',
      type: JVxeTypes.textarea,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
  ]
export const constrAttachColumns: JVxeColumn[] = [
    {
      title: '附件类型  IMAGE=疏解图片 ATTACH=施工附件 CLONKIN=施工打卡 REPORT=隐患上报 PROCESS=隐患处理',
      key: 'type',
       type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
      validateRules: [{ required: true, message: '${title}不能为空' }],
    },
    {
      title: '施工附件url',
      key: 'attachUrl',
       type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
      validateRules: [{ required: true, message: '${title}不能为空' }],
    },
  ]
export const constrStreetColumns: JVxeColumn[] = [
    {
      title: '省',
      key: 'provinceId',
      type: JVxeTypes.select,
      options:[],
      dictCode:"",
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
      validateRules: [{ required: true, message: '${title}不能为空' }],
    },
    {
      title: '城市',
      key: 'municipalId',
      type: JVxeTypes.select,
      options:[],
      dictCode:"",
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
      validateRules: [{ required: true, message: '${title}不能为空' }],
    },
    {
      title: '区域',
      key: 'regionId',
      type: JVxeTypes.selectMultiple,
      options:[],
      dictCode:"",
      width:"250px",
      placeholder: '请输入${title}',
      defaultValue:'',
      validateRules: [{ required: true, message: '${title}不能为空' }],
    },
    {
      title: '街道',
      key: 'streetId',
      type: JVxeTypes.selectMultiple,
      options:[],
      dictCode:"",
      width:"250px",
      placeholder: '请输入${title}',
      defaultValue:'',
      validateRules: [{ required: true, message: '${title}不能为空' }],
    },
  ]
