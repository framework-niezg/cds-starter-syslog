import * as types from '../mutation-types'
import Util from 'framework/util/util'
import XHR from 'framework/xhr/xhr'
import LogList from "src/collection/logList"
import LogGroupList from "src/collection/logGroupList"
import UserList from "src/collection/userList"

const state = {
  name:"logManager",
  param: {
    createTime:[new Date(new Date().Format("yyyy-MM-dd")).getTime(),new Date(new Date().Format("yyyy-MM-dd")).getTime()],
    logGroup: "",
    logEvent: "",
    operationUser:"",
    query: ["logGroup","logEvent","createTime","operationUser"],
    queryString: [],
    pageIndex: "1",
    limit: 5,
    orderBy: ""
  },
  options:{
    search:[
      {text:"发生时间",id:"createTime",name:"createTime",type:"time",timeOption:{
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      }},
      {text:"日志类别",id:"logGroup",name:"logGroup",type:"select"},
      {text:"日志事件",id:"logEvent",name:"logEvent",type:"select"},
      {text:"操作用户",id:"operationUser",name:"operationUser",type:"select"},
      {text:"查询",id:"logSearch",name:"logSearch",type:"button",event:"submit"},
    ],
    table: {
     basic:{
       type: "index",
       isPageNation: true,
       highlight: true,
       caption:"caption",
       th: [
         {property: "createTime", label: "时间",width:"200"},
         {property: "logGroupDisplayName", label: "日志类别",width:"150"},
         {property: "logEventDisplayName", label: "日志事件",width:"200"},
         {property: "operationUser", label: "操作用户",width:"150"},
         {property: "detail", label: "日志详情"},
       ]
     }
    }
  },
  tableData: {},
  pageData:{
    basic:{
      pageIndex:1,
      limit: 5
    }
  },
  searchSelectData:{
    logGroup:[],
    logEvent:[]
  },
  url: {
    group:"logGroup",
    search: "logs",
    users:"users"
  },
  actions:{
   items:"LOGS_ITEMS",
    param:"PARAM_CHANGE",
    group:"LOG_GROUP",
    select:"SELECT_CHANGE",
    time:"TIME_CHANGE",
    users:"USERS_SUCCESS"
  },
  result: {},
  flag: {}
}

// getters 对数据进行格式化
const getters = {
  tableData:(state) => {
    return state.tableData
  },
  searchSelectData: state => {
    return state.searchSelectData
  },
  result: state => {
    return state.result;
  },
}

const actions = {
  getTableData({dispatch}){
    dispatch("getItems");
  },
  getItems({commit, state}) {
    let param = Util.resetParamEq(Object.assign({},state.param));
    XHR.ajaxGetForArray({
      url: state.url.search,
      data: param
    }, function (data) {
      let parse = {
        type: state.url.search,
        data: data
      };
      data.success ? commit(types[state.actions.items], parse) : commit(types.ERROR, parse);
    })
  },
  getSelectData({commit,state}){
    XHR.get({
      url: state.url.group,
    },(data)=>{
      let parse = {
        type: state.url.group,
        data: data
      };
      data.success ? commit(types[state.actions.group], parse) : commit(types.ERROR, parse);
    })
    XHR.get({
      url: state.url.users,
    },(data)=>{
      let parse = {
        type: state.url.users,
        data: data
      };
      data.success ? commit(types[state.actions.users], parse) : commit(types.ERROR, parse);
    })
  },
  pageChange({dispatch,commit,state},pageIndex){
    commit(types[state.actions.param],{pageIndex:pageIndex});
    dispatch("getItems");
  },
  resetParam({commit,state},data){
    commit(types[state.actions.param],data);
  },
  resetSelect({commit,state},data){
    commit(types[state.actions.select],data);
  },
  resetTime({commit,state},data){
    commit(types[state.actions.time],data);
  },
}
const mutations = {
  //查
  [types[state.actions.items]](state, data) {
    let result = data.data.data;
    if(result.pageIndex){
      state.pageData = {
        basic:{
          limit:result.limit,
          pageIndex: result.pageIndex,
          total: result.total
        }
      }
    }else{
      state.pageData =  null;
    }
    let Logs = new LogList(result.content);
    state.tableData = Object.assign({},state.tableData,{basic:Logs.getModels()});
  },
  //修改参数
  [types[state.actions.param]](state,param){
    console.log(param);
    state.param = Object.assign({},state.param,param);
  },
  [types[state.actions.time]](state,time){
    let a = [];
    time.forEach((t)=>{
      console.log(t,new Date(t));
      a.push(new Date(t).getTime())
    });
    state.param = Object.assign({},state.param,{createTime:a});
  },
  [types[state.actions.users]](state,data){
    if(data.data.data.content.length>0){
      let users = data.data.data.content;
      console.log(users);
      let u = new UserList(users);
      state.searchSelectData = Object.assign({},state.searchSelectData,{operationUser:u.getModels()});
    }else{

    }
  },
  [types[state.actions.select]](state,{key,index}){
    if(key=="logGroup"){
      if(index==0){
        state.searchSelectData = Object.assign({},state.searchSelectData,{logEvent:[]});
        state.param = Object.assign({},state.param,{logEvent:""});
      }else{
        let i = parseInt(index)-1;
        let currentG = state.searchSelectData.logGroup;
        let g = new LogGroupList(currentG[i].logEvents);
        state.searchSelectData = Object.assign({},state.searchSelectData,{logEvent:g.getModels()});
      }
    }else{

    }
    //
  },
  [types[state.actions.group]](state,data){
    console.log(data)
    if(data.data.data.length>0){
      let groups = new LogGroupList(data.data.data);
      state.searchSelectData = Object.assign({},state.searchSelectData,{logGroup:groups.getModels()});
    }else{

    }
  },
  //回调
  [types.SUCCESS](state, data) {
    state.result = data;
  },
  [types.ERROR](state, data) {
    state.result = data;
  }
}

export default {
  namespaced:true,
  state,
  getters,
  actions,
  mutations
}
