import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
    {
    title: '区域code,街道和区code一致',
    align:"center",
    sorter: true,
    dataIndex: 'areaCode'
   },
   {
    title: 'areaName',
    align:"center",
    sorter: true,
    dataIndex: 'areaName'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: 'areaName',
    field: 'areaName',
    component: 'Input',
  },
  {
    label: '所属父地区id',
    field: 'parentId',
    component: 'InputNumber',
  },
];
