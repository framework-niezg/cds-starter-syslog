<template>
  <el-card class="box-card scrollbar">
    <template v-if="current" slot="header">
      <slot :name="options[current].caption"></slot>
    </template>
    <el-table v-if="current" ref="multipleTable" :data="tableData[current]"
              :highlight-current-row="options[current].highlight"
              @cell-click="tableCellClick"
              @selection-change="selectionChange"
              @select-all="selectAll"
              @expand-change="expandChange"
    >
      <el-table-column :type="options[current].type" width="50"></el-table-column>
      <el-table-column v-for="item in options[current].th"
                       :property="item.property"
                       :label="item.label">
      </el-table-column>
      <el-table-column v-if="options[current].deals && options[current].deals.max > 0"
                       :label="options[current].deals.label || '操作'"
                       align="left"
                       :width="options[current].deals.max*60">
        <template scope="scope">
          <template v-for="deal in scope.row.deals">
            <label v-if="deal.type == 'label'" class="deal-label" :name="scope.row.realUrl">
              {{deal.text}}
            </label>
            <label v-else-if="deal.type == 'open'" class="deal-label open-label" :name="scope.row.realUrl">
              {{deal.text}}
            </label>
            <el-button v-else
                       :type="deal.type"
                       size="mini"
                       @click.native="onClickHandler(scope.$index, scope.row,deal.event)"
                       :name="deal.event">
              {{deal.text}}
            </el-button>
          </template>
        </template>
      </el-table-column>
      <el-table-column v-if="options[current].isExpand=='true'" type="expand" width="80">
        <template slot-scope="props">
          <el-form label-position="left" inline class="table-expand">
            <el-form-item v-for="item in options[current].expandLabel" :label="item.label">
              <span>{{ props.row[item.property] }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
    </el-table>
    <el-row v-if="options[current].isPageNation" type="flex" align="middle" :gutter="20" class="page-nation">
      <el-col :span="24" style="text-align: right">
        <el-pagination
          @current-change="handleCurrentPage"
          :current-page="pageData[current].pageIndex"
          layout="prev, pager, next"
          :page-size="pageData[current].limit"
          :total="pageData[current].total">
        </el-pagination>
      </el-col>
    </el-row>
  </el-card>
</template>

<style scope>
  .el-table td, .el-table th {
    padding: 4px 0;
  }

  .page-nation {
    padding: 10px 10px 0 10px;
  }

  .el-pager li {
    border: 1px solid #ccc;
    border-radius: 5px;
    margin: 0 3px;
    min-width: 25px;
    color: #5a5e66;
  }

  .el-pager li.active + li {
    border: 1px solid #ccc;
  }

  .el-pagination .btn-prev, .el-pagination .btn-next {
    border: 1px solid #ccc;
    border-radius: 5px;
    min-width: 25px;
  }
  .el-form-item__content {
    line-height: 40px;
    position: relative;
    font-size: 14px;
    font-weight: 600;
    /* border-bottom: 1px solid #ccc; */
  }
  .el-form--inline .el-form-item__label {
    float: none;
    display: inline-block;
    text-align:right;
    font-weight:600;
  }
  .deal-label {
    font-size: 12px;
    color: #5a5e66;
  }
  .open-label{
    color:#409eff;
    cursor:pointer;
  }
  .table-expand {
    font-size: 0;
  }
  .table-expand label {
    width: 109px;
    color: #99a9bf;
    text-align:right;
  }
  .el-table__expanded-cell[class*=cell] {
    padding: 20px 50px;
    background-color: #f5f5f5;
  }
  .table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 30%;
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
    props: ["current"],
    computed: {
      ...mapGetters({
        tableData: "tableData",
        pageData: "pageData",
        options: "options",
        result: "result",
        has:"has"
      }),
    },
    methods: {
      //表格事件
      tableCellClick: function (row, column, cell, event) {
        if (column.label == '操作') {
          //点击操作栏内的功能按钮
        } else if (column.type == 'selection') {
          row.$info = !row.$selected;//行选中转换
          //this.setSelectionData(row)
        } else {
          //单击表格切换选中状态
          //this.toggleSelection(row);
        }
      },
      //表格行选中单例模式
      toggleSelection(row) {
        if (row) {
          row.$selected = !row.$selected;
          row.$info = row.$selected;
          this.$refs.multipleTable.clearSelection();
          this.$refs.multipleTable.toggleRowSelection(row);
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      expandChange(row,expandedRows){
        console.log(123)
        console.log(row);
        console.log(expandedRows)
      },

      //用户点击单个checkbox
      selectionChange(val) {
        var arr = [];
        val.forEach((item) => {
          arr.push(item.id);
        });
        console.log(arr)
        this.$store.dispatch(this.currentTable + '/tableSelected', {apiTable:arr,["apiTableAll"]:val});
      },
      //用户点击全选
      selectAll(selection) {
        this.selectionChange(selection)
      },
      //用户点击操作栏
      onClickHandler(index, row, type) {
        switch (type) {
          case"change":
            this.handleChange(row);
            break;
          case"reset":
            this.handleReset(row);
            break;
          case"delete":
            this.handleDelete(row);
            break;
          default:
            break;
        }
      },
      clearRows(){
        console.log(this.current)
        let table = this.tableData[this.current];
        console.log(this.current,table)
        table.map((item)=>{
          this.$refs.multipleTable.toggleRowSelection(item,false);
        })
      },
      checkRows(data){
        this.clearRows();
        console.log(this.current)
        let table = this.tableData[this.current];
        if(data.length>0){
          table.map((item)=>{
            data.map(({id})=>{
              console.log(id);
              if(id==item.id){
                this.$refs.multipleTable.toggleRowSelection(item,true);
              }
            })

          })
        }
      },
      handleChange(row) {
        //this.toggleSelection(row);
        this.$store.dispatch(this.currentTable + '/getCurrentData', row);
      },
      //行点击
      trHandler(row) {
        //this.$store.dispatch(this.currentTable+'/trHandler',row);
      },
      //用户点击重置
      handleReset(row) {
        //this.message('info',"功能维护中");
        this.$store.dispatch(this.currentTable + '/resetPass', row);
      },
      handleRowChange(val, old) {
        switch(this.current){
          case"basic":
            this.$store.dispatch(this.currentTable + '/trHandler', val);
            break;
          default:
            //this.$store.dispatch(this.currentTable + '/trHandler', val);
            break;
        }

      },
      //单体删除
      handleDelete(row) {
        this.selectItems = [];
        this.selectItems.push(row.id);
        this.handleConfirm(
          '此操作将永久删除该数据, 是否继续?',
          "提示",
          {confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'},
          this.onDelete);
      },
      handleRemove() {
        var tableData = this.tableData;
        this.selectItems.forEach(function (id) {
          tableData.forEach(function (data) {
            if (id == data.id) {
              tableData.splice(tableData.indexOf(data), 1)
            }
          })
        });
        this.selectItems = [];
      },
      handleConfirm(message = "删除", title = "提示", button = {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }, callBack = this.onDelete) {
        this.$confirm(message, title, button, callBack).then(() => {
          callBack();
        }).catch(() => {
          this.message('info', "已取消删除");
        });
      },
      handleCurrentPage(pageIndex) {
        this.$store.dispatch(this.currentTable + '/pageChange', pageIndex);
      },
      onDelete() {
        this.$store.dispatch(this.currentTable + '/deleteItem', this.selectItems);
      },
      success(type, data) {
        switch (type) {
          case"changeApis":
            this.message('success', data.msg);
            this.$store.dispatch(this.currentTable + '/getCurrentHas');
            break;
          default:
            break;
        }
        //this.$store.dispatch(this.currentTable + '/getItems');

      },
      error(data) {
        this.message('warning', data.msg);
      },
      message(type, msg) {
        this.$message({
          type: type,
          message: msg,
          duration: "5000"
        });
      }
    },
    watch: {
      result: {
        handler: function ({type, data}) {
          data.success ? this.success(type, data) : this.error(data);
        },
        deep: true//对象内部的属性监听，也叫深度监听
      },
      has: {
        handler: function ({apiHas}) {
          console.log(apiHas,apiHas.data)
          apiHas && apiHas.data &&apiHas.data.length>0 ? this.checkRows(apiHas.data):this.clearRows();
        },
        deep: true//对象内部的属性监听，也叫深度监听
      }
    },
    beforeCreate() {
      console.log()
    },
    created() {
      this.currentTable = Util.getItem("currentVue").vue;
      this.$store.dispatch(this.currentTable + '/getTableData',this.current);
    }
  }
</script>
<!--table 组件 api-->

