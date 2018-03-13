
//all need
export const setState = state => {
  return state.current;
};
//搜索区域
export const searchOptions = state =>{
  return state[state.current].options.search;
};
export const searchSelectData = state =>{
  return state[state.current].searchSelectData;
};
//通用表格组件响应入口
export const tableData = state => {
  return state[state.current].tableData;
};
export const pageData = state => {
  return state[state.current].pageData;
};
export const options = (state) => {
  return state[state.current].options.table;
};
export const has = (state) => {
  return state[state.current].has;
};
/*通用树组件相应入口*/
export const treeData = state => {
  return state[state.current].treeData;
};
export const treeCheckData = state => {
  return state[state.current].treeCheckData;
};
//弹出窗
export const resentDialog = state => {
  return state[state.current].currentDialog;
};
//通用展示层组件响应入口
export const formData = (state) => {
  return state[state.current].formData;
};
export const formOptions = (state) => {
  console.log(state[state.current].options.form);
  return state[state.current].options.form;
};


//通用响应结果
export const result = state => {
  return state[state.current].result;
};

