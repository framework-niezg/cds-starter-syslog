/**
 * Created by Administrator on 2017/4/7.
 */
import LogGroup from "src/models/LogGroup";
class LogGroupList{
    constructor(data){
        this.default = data;
        this.models= [];
        this.reset(JSON.parse(JSON.stringify(this.default)));
    }
    reset(items){
        let _this = this;
        items.map(function(item){
           let simple = new LogGroup(item);
            _this.models.push(simple.getModel());
       })
    }
    getModels(){
        return this.models;
    }
    getModel(i){
        return this.models[i]
    }
}
export default LogGroupList


