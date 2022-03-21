import {defHttp} from '/@/utils/http/axios';
import {Modal} from 'ant-design-vue';

enum Api {
  list = '/mng/constr/list',
  save='/mng/constr/add',
  edit='/mng/constr/edit',
  deleteOne = '/mng/constr/delete',
  deleteBatch = '/mng/constr/deleteBatch',
  importExcel = '/mng/constr/importExcel',
  exportXls = '/mng/constr/exportXls',
  constrAreaList = '/mng/constr/queryConstrAreaByMainId',
  constrAttachList = '/mng/constr/queryConstrAttachByMainId',
  constrStreetList = '/mng/constr/queryConstrStreetByMainId',
}
/**
 * 导出api
 * @param params
 */
export const getExportUrl = Api.exportXls;

/**
 * 导入api
 */
export const getImportUrl = Api.importExcel;
/**
 * 查询子表数据
 * @param params
 */
export const constrAreaList = Api.constrAreaList;
/**
 * 查询子表数据
 * @param params
 */
export const constrAttachList = Api.constrAttachList;
/**
 * 查询子表数据
 * @param params
 */
export const constrStreetList = Api.constrStreetList;
/**
 * 列表接口
 * @param params
 */
export const list = (params) =>
  defHttp.get({url: Api.list, params});

/**
 * 删除单个
 */
export const deleteOne = (params,handleSuccess) => {
  return defHttp.delete({url: Api.deleteOne, params}, {joinParamsToUrl: true}).then(() => {
    handleSuccess();
  });
}
/**
 * 批量删除
 * @param params
 */
export const batchDelete = (params, handleSuccess) => {
  Modal.confirm({
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({url: Api.deleteBatch, data: params}, {joinParamsToUrl: true}).then(() => {
        handleSuccess();
      });
    }
  });
}
/**
 * 保存或者更新
 * @param params
 */
export const saveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.edit : Api.save;
  return defHttp.post({url: url, params});
}
