import { VALIDATE_FAILED} from '@/components/jeecg/JVxeTable/utils/vxeUtils.js'
import { httpAction, getAction } from '@/api/manage'

export const JModelMixin = {
  data() {
    return {
      title: '操作',
      visible: false,
      confirmLoading: false,
      scrolling: true,
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      }
    }
  },
  methods: {

 

    /** 当点击新增按钮时调用此方法 */
    add() {
      if (typeof this.addBefore === 'function') this.addBefore()
      // 默认新增空数据
      let rowNum = this.addDefaultRowNum
      if (typeof rowNum !== 'number') {
        rowNum = 1
        console.warn('由于你没有在 data 中定义 addDefaultRowNum 或 addDefaultRowNum 不是数字，所以默认添加一条空数据，如果不想默认添加空数据，请将定义 addDefaultRowNum 为 0')
      }
 
      if (typeof this.addAfter === 'function') this.addAfter(this.model)
      this.edit(this.model)
    },
    /** 当点击了编辑（修改）按钮时调用此方法 */
    edit(record) {
      if (typeof this.editBefore === 'function') this.editBefore(record)
      this.visible = true
      this.model = Object.assign({}, record)
      if (typeof this.editAfter === 'function') this.editAfter(this.model)
    },
    /** 关闭弹窗，并将所有JVxeTable实例回归到初始状态 */
    close() {
      this.visible = false
 
      this.$emit('close')
    },

    /** 发起请求，自动判断是执行新增还是修改操作 */
    request(formData) {
      let url = this.url.add, method = 'post'
      if (this.model.id) {
        url = this.url.edit
        method = 'put'
      }
      this.confirmLoading = true
      console.log("formData===>",formData);
      httpAction(url, formData, method).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
          this.$emit('ok')
          this.close()
        } else {
          this.$message.warning(res.message)
        }
      }).finally(() => {
        this.confirmLoading = false
      })
    },

    /* --- handle 事件 --- */

 
    /** 关闭按钮点击事件 */
    handleCancel() {
      this.close()
    },
    /** 确定按钮点击事件 */
    handleOk() {
 
      // 触发表单验证
      let that = this
      this.$refs.form.validate(valid => {
        if (!valid) return false
        if (typeof that.classifyIntoFormData !== 'function') {
              throw that.throwNotFunction('classifyIntoFormData')
          }
          let formData = that.classifyIntoFormData(that.$refs.form.model)
            // 发起请求
          return that.request(formData)
      }) 
 
    },
//校验所有子表表单
    validateSubForm(allValues){
      return new Promise((resolve) => {
        resolve(allValues)
      })
    },
    /* --- throw --- */

    /** not a function */
    throwNotFunction(name) {
      return `${name} 未定义或不是一个函数`
    },

    /** not a array */
    throwNotArray(name) {
      return `${name} 未定义或不是一个数组`
    }

  }
}