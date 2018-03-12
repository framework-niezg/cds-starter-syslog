/**
 * @param a means b
 * Created by Administrator on 2016/12/29.
 * lastLog: Administrator
 * Date: 2016/12/29
 * Time: 12:52
 */
class Log {
  constructor(data) {
    this.default = {
     name:"",
      displayName:"",
      logEvents:[]
    };
    this.model = {};
    this.setDefault(data);
  }

  setDefault(data) {
    this.defaultData = Object.assign({}, this.default, data);
  }

  getModel() {
    for (let key in this.defaultData) {
      switch (key) {
        case"name":
          this.model.value = this.defaultData[key] ;
        case"displayName":
          this.model.label = this.defaultData[key] ;
        default:
          this.model[key] = this.defaultData[key] == "0" ? this.defaultData[key] : (this.defaultData[key] ? this.defaultData[key] : "");
          break;
      }
    }
    return this.model;
  }

  getFilesIds(files) {
    let ids = [];
    files.map(function (file) {
      let {id} = file;
      ids.push(id);
    })
    return ids;
  }

  static DEFAULT() {
    return new Log().default;
  }
}

export default Log
