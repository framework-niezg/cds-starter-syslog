<template>
  <el-card class="box-card scrollbar" :body-style="bodyStyle ? bodyStyle :{backgroundColor:'#fff'}">
    <template slot="header">
      <slot name="caption"></slot>
    </template>
    <el-form :inline="true">
      <el-form-item v-for="item in formOptions[current]" :label="item.label">
        <el-input  v-if="item.length=='large'" :disabled="item.disabled ? item.disabled : true " :value="formData[current][item.property]" :size="item.length ? item.length: 'large'" style="width:296px"></el-input>
        <el-input  v-else :disabled="item.disabled ? item.disabled : true " :value="formData[current][item.property]" :size="item.length ? item.length: 'large'"></el-input>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<style scope>
  .el-form-item{
    margin-bottom:0;
    margin-right:0;
  }
.el-form-item__label{
  min-width:83px;
  margin-bottom: 4px;
  margin-right:0;
}
.el-form--inline .el-form-item__label {
  font-weight: 600;
  font-size: 14px;
}
</style>

<script>
  import {mapGetters, mapActions} from 'vuex'
  import Util from "framework/util/util"

  export default {
    data() {
      return {
        currentRow: 0
      }
    },
    props: ["bodyStyle","current"],
    computed: {
      ...mapGetters({
        formData: "formData",
        formOptions:"formOptions"
      })
    },
    methods: {},
    beforeCreate() {
      //console.log(this.currentForm)
    },
    created() {
      this.currentForm = Util.getItem("currentVue").vue;
      console.log(this.currentForm);
      this.$store.dispatch(this.currentForm + '/getFormData',this.current);
    },
    mounted(){

    }
  }
</script>
<!--table 组件 api-->

