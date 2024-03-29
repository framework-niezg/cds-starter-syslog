/**
 * Created by Administrator on 2016/8/15.
 */

import SImmutable from "seamless-immutable";

let final = {

  URL: {
    //同用
    token:"token",
    //*******************************************************用户管理 users***********************************************************//
    userInfos:"users",
    users: "users",//查询所有用户信息
    deleteUser: "users/{id}",//删除某用户信息
    addUser: "users",//新增用户
    changeUser: "users/{id}",//修改用户
    resetUserPass:"users/{id}/resetPassword",//
    USER:"users/current",
    //*******************************************************角色管理 roles***********************************************************//
    roles: "roles",//查询所有角色信息
    deleteRole: "roles/{id}",//删除角色信息
    addRole: "roles",//新增角色信息
    //*******************************************************菜单信息***********************************************************//
    menus: "menus/cascade",//所有菜单信息
    roleHasMenu: "roles/{id}/menus",//查询某角色下的已有菜单
    roleMenuChange: "roles/{id}",//修改菜单
    //*******************************************************api管理***********************************************************//
    apis:"wzyg/apis/enabled ",//可用api列表
    changeApis:"users/{id}/apis",//
    apiHas:"users/{id}/apis",
    //*******************************************************日志管理***********************************************************//
    logGroup:"logGroups",
    logs:"syslogs"
    //*******************************************************资质信息***********************************************************//

  },
  /*
   * 字典表
   */
  dictionaries: {},
  /*
   * 附件地址
   * */
  FILE: {}
};

let Final = SImmutable(final);

export default final;
