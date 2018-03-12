/**
 * @param a means b
 * Created by Administrator on 2016/11/14.
 * lastUser: Administrator
 * Date: 2016/11/14
 * Time: 16:02
 */
var fs = require( 'fs' );
var path = require( 'path' );

class FS{
    constructor(modules){
        this.modules = modules || ["test"]
        this.startCreateFiles();
    }
    startCreateFiles(){
      let _this = this;
      this.modules.map((module)=>{
        console.log("======开始创建----"+module+"模块流程文件");
       // _this.createEntry(module);
        _this.createPage(module);

      })
    }
  createEntry(module){
    console.log("------创建----"+module+"入口");
    new Entry(module);
  }
  createPage(module){
    console.log("------创建----"+module+"Page");
    new Page(module);
  }
}
class Entry{
  constructor(module){
    this.module = module;
    this.file = path.resolve(__dirname,"../src/entry/"+module+".js");
    this.moduleArray = module.split("/");
    this.name = this.moduleArray[this.moduleArray.length-1];
    this.initTemplate();
  }
  initTemplate(){
    var reg = /\b(\w)|\s(\w)/g; //  \b判断边界\s判断空格
    this.className = this.name.replace(reg,function(m){
      return m.toUpperCase()
    });
    this.date = new Date();
    this.mome = "/**\n" +
      " * @param a means b\n" +
      " * Created by Administrator.\n" +
      " * lastUser: Administrator\n" +
      " * Date: "+ this.date.getFullYear()+"/"+this.date.getMonth()+"/"+this.date.getDay() +"\n" +
      " * Time: "+ this.date.getHours()+":"+this.date.getMinutes() +"\n" +
      " */"
    this.template =this.mome + "\r\nimport Entry from 'src/entry'\n" +
      "import "+ this.className +" from 'page/"+ this.module +"/index'\n" +
      "new Entry({module:"+ this.className +"})";
    this.createFile()
  }
  createFile(){
    NFS.create("CREATE_FILE",this.file,this.template);
  }
}
class Page{
  constructor(module){
    this.module = module;
    this.file = path.resolve(__dirname,"../src/page/"+module+"/index.vue");
    this.initTemplate();
  }
  initTemplate(){
    this.date = new Date();
    this.mome = "/**\n" +
      " * @param a means b\n" +
      " * Created by Administrator.\n" +
      " * lastUser: Administrator\n" +
      " * Date: "+ this.date.getFullYear()+"/"+this.date.getMonth()+"/"+this.date.getDay() +"\n" +
      " * Time: "+ this.date.getHours()+":"+this.date.getMinutes() +"\n" +
      " */"
    this.template = this.mome + "\r<template>"+ "\n" +"<div class='container'>"+ "\n" +"</div>"+ "\n" +"</template>"+
                   "\r<style>"+ "\n" +"</style>"+
                   "\r<script>"+
                    "\n import 'src/basic'"+
                    "\n </script>"


   /* export default {
      components: {
        Test
      },
      props: [],
      compute: {},
      data() {
        return {
          msg: '这个是Home模板页'
        }
      },
      methods: {},
    }
    </script>";*/
    this.createFile()
  }
  createFile(){
    NFS.create("CREATE_FILE",this.file,this.template);
  }
}
class NFS {
  static create(flag,filePath,fileContent){
    switch(flag){
      case"CREATE_FILE":
          NFS.insertContentToFile(filePath,fileContent)
        break;
      default:
        break;
    }
  }
  static insertContentToFile(filePath,fileContent){
    fs.appendFile(filePath, fileContent, function(err){
        err ? console.log("fail " + err): console.log("写入文件ok");
    });
  }
}
module.exports = FS
