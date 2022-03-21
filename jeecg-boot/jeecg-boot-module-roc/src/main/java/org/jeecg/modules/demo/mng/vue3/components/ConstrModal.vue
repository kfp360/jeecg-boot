<template>
  <BasicModal v-bind="$attrs" @register="registerModal" :title="title" @ok="handleSubmit">
      <BasicForm @register="registerForm" ref="formRef"/>
  <!-- 子表单区域 -->
    <a-tabs v-model:activeKey="activeKey" @change="handleChangeTabs">
      <a-tab-pane tab="constr_area" :key="refKeys[0]" :forceRender="true">
        <JVxeTable
          keep-source
          resizable
          :ref="refKeys[0]"
          :loading="constrAreaTable.loading"
          :columns="constrAreaTable.columns"
          :dataSource="constrAreaTable.dataSource"
          :maxHeight="300"
          :rowNumber="true"
          :rowSelection="true"
          :toolbar="true"
          />
      </a-tab-pane>
      <a-tab-pane tab="constr_attach" :key="refKeys[1]" :forceRender="true">
        <JVxeTable
          keep-source
          resizable
          :ref="refKeys[1]"
          :loading="constrAttachTable.loading"
          :columns="constrAttachTable.columns"
          :dataSource="constrAttachTable.dataSource"
          :maxHeight="300"
          :rowNumber="true"
          :rowSelection="true"
          :toolbar="true"
          />
      </a-tab-pane>
      <a-tab-pane tab="constr_street" :key="refKeys[2]" :forceRender="true">
        <JVxeTable
          keep-source
          resizable
          :ref="refKeys[2]"
          :loading="constrStreetTable.loading"
          :columns="constrStreetTable.columns"
          :dataSource="constrStreetTable.dataSource"
          :maxHeight="300"
          :rowNumber="true"
          :rowSelection="true"
          :toolbar="true"
          />
      </a-tab-pane>
    </a-tabs>
  </BasicModal>
</template>

<script lang="ts" setup>
    import {ref, computed, unref,reactive} from 'vue';
    import {BasicModal, useModalInner} from '/@/components/Modal';
    import {BasicForm, useForm} from '/@/components/Form/index';
    import { JVxeTable } from '/@/components/jeecg/JVxeTable'
    import { useJvxeMethod } from '/@/hooks/system/useJvxeMethods.ts'
    import {formSchema,constrAreaColumns,constrAttachColumns,constrStreetColumns} from '../constr.data';
    import {saveOrUpdate,constrAreaList,constrAttachList,constrStreetList} from '../constr.api';
    import { VALIDATE_FAILED } from '/@/utils/common/vxeUtils'
    // Emits声明
    const emit = defineEmits(['register','success']);
    const isUpdate = ref(true);
    const refKeys = ref(['constrArea', 'constrAttach', 'constrStreet', ]);
    const activeKey = ref('constrArea');
    const constrArea = ref();
    const constrAttach = ref();
    const constrStreet = ref();
    const tableRefs = {constrArea, constrAttach, constrStreet, };
    const constrAreaTable = reactive({
          loading: false,
          dataSource: [],
          columns:constrAreaColumns
    })
    const constrAttachTable = reactive({
          loading: false,
          dataSource: [],
          columns:constrAttachColumns
    })
    const constrStreetTable = reactive({
          loading: false,
          dataSource: [],
          columns:constrStreetColumns
    })
    //表单配置
    const [registerForm, {setProps,resetFields, setFieldsValue, validate}] = useForm({
        labelWidth: 150,
        schemas: formSchema,
        showActionButtonGroup: false,
    });
     //表单赋值
    const [registerModal, {setModalProps, closeModal}] = useModalInner(async (data) => {
        //重置表单
        await reset();
        setModalProps({confirmLoading: false,showCancelBtn:data?.showFooter,showOkBtn:data?.showFooter});
        isUpdate.value = !!data?.isUpdate;
        if (unref(isUpdate)) {
            //表单赋值
            await setFieldsValue({
                ...data.record,
            });
             requestSubTableData(constrAreaList, {id:data?.record?.id}, constrAreaTable)
             requestSubTableData(constrAttachList, {id:data?.record?.id}, constrAttachTable)
             requestSubTableData(constrStreetList, {id:data?.record?.id}, constrStreetTable)
        }
        // 隐藏底部时禁用整个表单
       setProps({ disabled: !data?.showFooter })
    });
    //方法配置
    const [handleChangeTabs,handleSubmit,requestSubTableData,formRef] = useJvxeMethod(requestAddOrEdit,classifyIntoFormData,tableRefs,activeKey,refKeys);

    //设置标题
    const title = computed(() => (!unref(isUpdate) ? '新增' : '编辑'));

    async function reset(){
      await resetFields();
      activeKey.value = ref('constrArea');
      constrAreaTable.dataSource = [];
      constrAttachTable.dataSource = [];
      constrStreetTable.dataSource = [];
    }
    function classifyIntoFormData(allValues) {
         let main = Object.assign({}, allValues.formValue)
         return {
           ...main, // 展开
           constrAreaList: allValues.tablesValue[0].tableData,
           constrAttachList: allValues.tablesValue[1].tableData,
           constrStreetList: allValues.tablesValue[2].tableData,
         }
       }
    //表单提交事件
    async function requestAddOrEdit(values) {
        try {
            setModalProps({confirmLoading: true});
            //提交表单
            await saveOrUpdate(values, isUpdate.value);
            //关闭弹窗
            closeModal();
            //刷新列表
            emit('success');
        } finally {
            setModalProps({confirmLoading: false});
        }
    }
</script>

<style lang="less" scoped>

</style>