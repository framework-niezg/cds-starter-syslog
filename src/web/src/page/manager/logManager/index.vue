<template>
  <div class="container">
    <SearchVue v-bind:current="currentTable" v-bind:isSearch="isSearch"></SearchVue>
    <TableVue v-bind:current="basic">
      <template slot="caption">
        <div class="clearfix">
          <span>日志</span>
          <el-button v-for="deal in options.basic.tops"
                     :type="deal.type"
                     size="mini"
                     style="float: right;margin-left:5px"
                     @click.native="onTopClickHandler(deal.event)"
                     :name="deal.event">
            {{deal.text}}
          </el-button>
        </div>
      </template>
    </TableVue>
    <br/>
  </div>
</template>
<script>
  import "src/basic"
  import { mapGetters, mapActions } from 'vuex'
  import XHR from "framework/xhr/xhr"
  import Util from "framework/util/util"
  import TableVue from 'framework/components/tableVue'
  import SearchVue from 'framework/components/searchVue'

  export default {
    name: 'logManager',
    data(){
      return{
        isSearch:true,
        currentTable:"logManager",
        basic:"basic"
      }
    },
    components: {
      TableVue,
      SearchVue
    },
    computed: {
      ...mapGetters({
        setState: "setState",
        options:"options"
      }),
      ...mapGetters("logManager",{
        tableSelected:"tableSelected"
      }),
    },
    methods:{

    },
    beforeCreate(){
      this.$store.dispatch("reSetSate","logManager");
      Util.setItem("currentVue",{vue:"logManager"});
    },
    create(){
      console.log(2)
    }
  }
</script>

<style lang="sass" type="text/css">
  @import "static/scss/container";
</style>
