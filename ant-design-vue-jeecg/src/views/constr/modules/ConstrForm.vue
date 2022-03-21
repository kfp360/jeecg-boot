<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <!-- 主表单区域 -->
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="12" >
            <a-form-model-item label="施工许可证编号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="licence">
              <a-input v-model="model.licence" placeholder="请输入施工许可证编号" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12" >
            <a-form-model-item label="施工许可证照片地址" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="licenceUrl">
              <j-image-upload  v-model="model.licenceUrl" ></j-image-upload>
            </a-form-model-item>
          </a-col>
          <a-col :span="12" >
            <a-form-model-item label="施工类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="constrType">
              <j-dict-select-tag type="list" v-model="model.constrType" dictCode="constr_type,type_name,id" placeholder="请选择施工类型" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12" >
            <a-form-model-item label="施工单位" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="companyName">
              <a-input v-model="model.companyName" placeholder="请输入施工单位" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12" >
            <a-form-model-item label="施工原因" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="reason">
              <a-input v-model="model.reason" placeholder="请输入施工原因" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12" >
            <a-form-model-item label="施工位置" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="position">
              <a-input v-model="model.position" placeholder="请输入施工位置" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12" >
            <a-form-model-item label="占道区域" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="occupiedArea">
              <a-input v-model="model.occupiedArea" placeholder="请输入占道区域" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12" >
            <a-form-model-item label="占道时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="occupiedDays"> 
              <a-range-picker
                style="width: 250px"
                v-model="model.occupiedDays" 
                format="YYYY-MM-DD"
                :placeholder="['开始时间', '结束时间']" 
                @change="onDateChange"
              />
            </a-form-model-item>
          </a-col>
          <a-col :span="12" >
            <a-form-model-item label="占道负责人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="liabler">
              <a-input v-model="model.liabler" placeholder="请输入占道负责人" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12" >
            <a-form-model-item label="占道负责人联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="contact">
              <a-input v-model="model.contact" placeholder="请输入占道负责人联系电话" ></a-input>
            </a-form-model-item>
          </a-col>
           <!-- <a-col :span="24" >
            <a-form-model-item label="施工开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="startTime">
              <j-date placeholder="请选择施工开始时间" v-model="model.startTime" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24" >
            <a-form-model-item label="施工结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="endTime">
              <j-date placeholder="请选择施工结束时间" v-model="model.endTime" style="width: 100%" />
            </a-form-model-item>
          </a-col> -->
          <a-col :span="12" >
            <a-form-model-item label="施工状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="status">
              <a-input v-model="model.status" placeholder="请输入施工状态" ></a-input>
            </a-form-model-item>
          </a-col>

          <a-col :span="12" >
            <a-form-model-item label="疏散图片" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="licenceUrl">
              <j-image-upload isMultiple  v-model="model.imageFiles" ></j-image-upload>
            </a-form-model-item>
          </a-col>

          <a-col :span="12" >
            <a-form-model-item label="施工附件" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="licenceUrl"> 
              <j-upload isMultiple  v-model="model.attachFiles" ></j-upload>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
 
  </a-spin>
</template>

<script>

  import { getAction } from '@/api/manage'
  import { JModelMixin } from '@/mixins/JModelMixin.js'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'

  export default {
    name: 'ConstrForm',
    mixins: [JModelMixin],
    components: {
      JFormContainer,
    },
    data() {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        model:{
         },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
           licence: [
              { required: true, message: '请输入施工许可证编号!'},
           ],
           constrType: [
              { required: true, message: '请输入施工类型!'},
           ],
           companyName: [
              { required: true, message: '请输入施工单位!'},
           ],
           reason: [
              { required: true, message: '请输入施工原因!'},
           ],
           position: [
              { required: true, message: '请输入施工位置!'},
           ],
           occupiedArea: [
              { required: true, message: '请输入占道区域!'},
           ],
           occupiedDays: [
              { required: true, message: '请输入占道时间!'},
           ],
           liabler: [
              { required: true, message: '请输入占道负责人!'},
           ],
           contact: [
              { required: true, message: '请输入占道负责人联系电话!'},
           ],
           startTime: [
              { required: true, message: '请输入施工开始时间!'},
           ],
           endTime: [
              { required: true, message: '请输入施工结束时间!'},
           ],
           status: [
              { required: true, message: '请输入施工状态!'},
           ],
        },
  
         
        url: {
          add: "/mng/constr/add",
          edit: "/mng/constr/edit",
          queryById: "/mng/constr/queryById",
          constrArea: {
            list: '/mng/constr/queryConstrAreaByMainId'
          },
          constrAttach: {
            list: '/mng/constr/queryConstrAttachByMainId'
          },
          constrImage: {
            list: '/mng/constr/queryConstrImageByMainId'
          },
          constrStreet: {
            list: '/mng/constr/queryConstrStreetByMainId'
          },
        }
      }
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
    },
    methods: {
      addBefore(){

      },
 
      /** 调用完edit()方法之后会自动调用此方法 */ 
      editAfter() {
        this.$nextTick(() => {
        })
        // 加载子表数据
        if (this.model.id) {
          this.model.imageFiles=""
          this.model.attachFiles=""
          this.model.occupiedDays=[this.model.startTime, this.model.endTime]
          let params = { id: this.model.id }

          let that = this
           // Load Constr 施工附件
          getAction(that.url.constrAttach.list, params).then((res)=>{
            if(res.success){
              this.$nextTick(() => {
                that.model.attachFiles = res.result.map(x=> x.attachUrl).join(',')
                that.$forceUpdate()
              })
              console.dir("++",this.model.attachFiles)
            }else{
              console.log(res.message);
            }
          });
          // Load Constr 疏散图片
          getAction(that.url.constrImage.list, params).then((res)=>{
            if(res.success){
              this.$nextTick(() => {
                that.model.imageFiles = res.result.map(x=> x.attachUrl).join(',')
                that.$forceUpdate()
              })
              console.dir("++",this.model.imageFiles)
            }else{
              console.log(res.message);
            }
          });
        }
      },
      //校验所有一对一子表表单
        validateSubForm(allValues){
            return new Promise((resolve,reject)=>{
              Promise.all([
              ]).then(() => {
                resolve(allValues)
              }).catch(e => {
                if (e.error === VALIDATE_FAILED) {
                  // 如果有未通过表单验证的子表，就自动跳转到它所在的tab
                  this.activeKey = e.index == null ? this.activeKey : this.refKeys[e.index]
                } else {
                  console.error(e)
                }
              })
            })
        },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        // To calculate the time difference of two dates
        let Difference_In_Time = Date.parse(this.model.endTime) - Date.parse(this.model.startTime);
  
        // To calculate the no. of days between two dates
        main.occupiedDays = Difference_In_Time / (1000 * 3600 * 24);
        

        let attachList=[]

        let imageUrls = [] 
        if (main.imageFiles) imageUrls = main.imageFiles.split(',')
        imageUrls.forEach(item => {
             attachList.push({ 
               "type":'IMAGE',
               'attachUrl': item
             })
        })
 
        let attachUrls = []
        if(main.attachFiles) attachUrls = main.attachFiles.split(',')
        attachUrls.forEach(item => {
             attachList.push({ 
               "type":'ATTACH',
               'attachUrl': item
             })
        })
        
 
        return {
          ...main, // 展开
          constrAreaList: [],
          constrAttachList: attachList,
          constrStreetList: [],
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },

      onDateChange: function (value, dateString) {
        console.log("onDateChange",dateString[0],dateString[1]); 
        this.model.startTime =dateString[0]
        this.model.endTime=dateString[1]  
      },
 

    }
  }
</script>

<style scoped>
</style>
