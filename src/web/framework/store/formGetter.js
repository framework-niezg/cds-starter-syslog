//通用展示层组件响应入口
export const formData = state => {
  //return state[state.current].tableData;
  return {}
};
export const formOptions = (state) => {
  console.log(state[state.current].options.form);
  return state[state.current].options.form;
};

