<template>
  <el-row ref="form" :inline="true"  class="basic-form">
    <div v-for="item in searchOptions" class="inline-block">
      <el-col v-if="item.type === 'time'">
        <label class="label-text">{{item.text}}:</label>
        <input style="display:none"/>
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          size="mini"
          align="right"
          unlink-panels
          value-format="yyyy-MM-dd"
          :default-value="item.date"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="timeChange"
          :picker-options="item.timeOption">
        </el-date-picker>
        </el-col>
      <el-col v-if="item.type === 'input'">
        <label class="label-text">{{item.text}}:</label>
        <input style="display:none"/>
        <el-input  auto-complete="new-password" class="basic-input" size="mini" :name="item.name" @change.native="inputChange"></el-input>
      </el-col>
      <el-col v-if="item.type === 'options'">
        <label class="label-text">{{item.text}}:</label>
        <el-select :value="searchSelectData[item.id].value"  :name="item.name" class="basic-select" size="mini" placeholder="请选择" v-on:change="optionChange">
          <el-option
            v-for="type in searchSelectData[item.id].data"
            :key="type.value"
            :label="type.label"
            :value="type.value">
          </el-option>
        </el-select>
      </el-col>
      <el-col v-if="item.type === 'select'">
        <label class="label-text">{{item.text}}:</label>
        <select :name="item.name" class="basic-select" size="mini" placeholder="请选择" @change="optionChange">
          <option value="">全部</option>
          <option
            v-for="type in searchSelectData[item.id]"
            :key="type.value"
            :label="type.label"
            :value="type.value">
          </option>
        </select>
      </el-col>
      <el-col v-if="item.type === 'button'">
        <el-button type="primary" class="basic-btn" @click.native="onClickHandler" :name="item.event" size="mini">{{item.text}}</el-button>
      </el-col>
    </div>
  </el-row>
</template>

<style scoped>
  .inline-block{
    display: inline-block;
  }
  .basic-select{
    width:150px;
    display: inline-block;
    border: 1px solid #ccc;
    border-radius: 5px;
    padding: 5px;
    color: #ccc;
    outline:none;
    font-size:14px;
  }
  .basic-input{
    width:140px;
    margin-left:5px;
  }
  .basic-btn{
    margin-left:5px!important;
  }
  .basic-form{
    margin-top: 5px;
    padding-bottom: 5px;
  }
  .label-text{
    padding-left:20px;
    line-height: normal;
    vertical-align: middle;
    font-size: 14px;
    display: inline-block;
  }
</style>

<script>
  import { mapGetters, mapActions } from 'vuex'
  export default {
    name:"searchOptions",
    props: ['current',"isSearch"],
    computed: {
      ...mapGetters({
        searchOptions: "searchOptions",
        searchSelectData:"searchSelectData"
      }),
    },
    methods: {
      inputChange(e) {
        let srcElement = e.srcElement || e.target, name = srcElement.getAttribute("name"), value = srcElement.value;
        this.$store.dispatch(this.current + '/resetParam', {[name]: value});
      },
      optionChange(e){
        let srcElement = e.srcElement || e.target, name = srcElement.getAttribute("name"), value = srcElement.value,selectedIndex =srcElement .selectedIndex ;
        this.$store.dispatch(this.current + '/resetParam', {[name]: value});
        this.$store.dispatch(this.current + '/resetSelect', {key:name,[name]: value,index:selectedIndex});
      },
      timeChange(e){
        this.$store.dispatch(this.current + '/resetTime',e);
      },
      onClickHandler(e) {
        let srcElement = e.srcElement || e.target, eventName = srcElement.getAttribute("name");
        eventName = !eventName ? srcElement.parentNode.getAttribute("name") : eventName;//兼容element-ui 组合标签
        switch (eventName) {
          case"submit":
            this.onSubmit();
            break;
          case"add":
            this.onAdd();
            break;
          default:
            this.onClickDefault(eventName);
            break;
        }
      },
      onSubmit(){
        this.$store.dispatch(this.current+"/getTableData","search");
      }
    },
    data() {
        return{
          dateRange:[new Date(),new Date()]
        }
    },
    created() {
      if(this.isSearch){
        this.$store.dispatch(this.current + '/getSelectData');
      }else{

      }

    }
  }
</script>
