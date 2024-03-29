/**
 * Created by Administrator on 2017/4/7.
 */
import User from "src/models/user";
class UserList{
    constructor(data,flag){
        this.default = data;
        this.models= [];
        this.flag = flag;
        this.reset(JSON.parse(JSON.stringify(this.default)));
    }
    reset(items){
        let _this = this;
        items.map(function(item){
           let simple = new User(item,_this.flag);
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
export default UserList


