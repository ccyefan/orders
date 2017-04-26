package com.bonc.order.utils;
/**
 * Created by moyong on 16/11/24.
 */

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class GroovyShellJsonExample {

    public static void main(String args[]) {
    	String b = "{\"activity_start_time\":\"2016-12-24 00:00:00.0\",\"activity_end_time\":\"2017-03-09 00:00:00.0\",\"msgreachresult\":\"\",\"msgacceptance\":\"\"}";
    	String wxhs = "{\"keyword2\":{\"value\":\"2017年\"}}";
//        oral();
        //JSON 的测试例子
        String content="{\"errcode\":0,\"content\": {\"mark\":\"0\",\"sequenceId\":\"999\"},\"flowId\":\"545454\",\"workId\":\"666\"}";

//        String content="{ \"resultJson\": {\"provOrderId\":\"22\"},\"msgFlag\":\"0\",\"flowId\":\"545454\",\"workId\":\"666\"}";
        String eventbody="{\"id\":\"88\",\"businessNumber\": {\"aa\":\"22\"},\"md5key\":\"545454\",\"serviceId\":\"888\",\"workId\":\"666\",\"template_id\":\"666\",\"open_id\":\"6666\",\"field_info\":{\"remark\":{\"color\":\"coloryanse\",\"value\":\"尊敬的用户，您好！&#36{省份}欢迎您订购假日流量包，您的产品为&#36{产品分类}，谢谢您的使用！\"},\"keyword1\":{\"color\":\"coloryanse\",\"value\":\"18637179045\"},\"first\":{\"color\":\"coloryanse\",\"value\":\"温馨提示，尊敬的用户，您好！\"},\"keyword2\":{\"color\":\"coloryanse\",\"value\":\"2016年12月24日\"}}}}";
//        String eventbody="{\"flowId\":\"545454\",\"workId\":\"666\"}";
        Binding binding = new Binding();
//        binding.setVariable("p1","http://10.162.20.110/3g/bss/simpleProChange/businessNumber/orderbusinessNumber/key/orderkey/agentPassword/0123456789/mark/O/serviceId/orderserviceId/takeEffectType/NOW/delayEffectDay/0/bewel/11/remark/22");
        binding.setVariable("p1",200);
//        binding.setVariable("p4", content);
        binding.setVariable("p2", b);
        binding.setVariable("p3", eventbody);
        try {
//            String script = "println\"Welcome to $language\"; y = x * 2; z = x * 3; return x ";
//            Object hello = GroovyShellJsonExample.getShell("hello", script, binding);
        	String scriptname="jsontest";
            String script = "import groovy.json.*;" +
                    "def jsonSlurper = new JsonSlurper();" +
                    "def object2 = jsonSlurper.parseText(p2);" +
                    "def object3 = jsonSlurper.parseText(p3);" +
                    "Calendar cal = Calendar.getInstance();"+
                    "int day = cal.get(Calendar.DATE);"+
                    "int month = cal.get(Calendar.MONTH) + 1;"+
            		"int year = cal.get(Calendar.YEAR);"+
            		"def json1 = JsonOutput.toJson([template_id:object3.template_id,touser:object3.open_id,url:''," +
                    "data:[first:[value:object3.field_info.first.value,color:'#173177'],keyword1:[value:object3.field_info.keyword1.value,color:'#173177']," +
                    "keyword2:[value:year+'年'+month+'月'+day+'日',color:'#173177']," +
                    "remark:[value:'恭喜您，订购成功',color:'#173177']]]);"+
                    "def json2 = JsonOutput.toJson([template_id:object3.template_id,touser:object3.open_id,url:''," +
                    "data:[first:[value:object3.field_info.first.value,color:'#173177'],keyword1:[value:object3.field_info.keyword1.value,color:'#173177']," +
                    "keyword2:[value:year+'年'+month+'月'+day+'日',color:'#173177']," +
                    "remark:[value:'订购异常',color:'#173177']]]);"+
                    "if(p2.contains('msgFlag')){if(p1==200&&object2.msgFlag=='0'){return json1;}else{return json2;}}else{"+
                    "if(p1==200&&object2.content.mark=='1'){return json1;}else{return json2;}}";
//                    "def json = JsonOutput.toJson([workid: object3.workid, flowid: object3.flowid,bOrderId:object2.ordersId,serialNumber:object.serialNumber,flowId:object.flowId]);"+
//                    "if(p1=='0'&&object2.msgFlag=='0'||object2.content.mark=='1'){return 0;}else{return 1}";
//                    "return object.businessNumber;";
            String s2 = "import groovy.json.*;def jsonSlurper = new JsonSlurper();def object2 = jsonSlurper.parseText(p2);def object3 = jsonSlurper.parseText(p3);if(p2.contains('msgFlag')){if(p1==200&&object2.msgFlag=='0'){def json1 = JsonOutput.toJson([workid:object3.workId,bOrderId:object2.resultJson.provOrderId,result:'success']); return json1;}else{def json1 = JsonOutput.toJson([workid:object3.workId,bOrderId:'',result:'failed']); return json1;}}else{if(p1==200&&object2.content.mark=='1'){def json1 = JsonOutput.toJson([workid:object3.workId,bOrderId:object2.content.sequenceId,result:'success']); return json1;}else{def json1 = JsonOutput.toJson([workid:object3.workId,bOrderId:'',result:'failed']); return json1;}}";
            String script1 = "import groovy.json.*;def jsonSlurper = new JsonSlurper();def object2 = jsonSlurper.parseText(p2);def object3 = jsonSlurper.parseText(p3);if(p2.contains('msgFlag')){if(p1==200&&object2.msgFlag=='0'){def json1 = JsonOutput.toJson([workid:object3.workId,flowid:object3.flowId]); return json1;}else{return 'failed';}}else{if(p1==200&&object2.content.mark=='1'){def json2 = JsonOutput.toJson([workid:object3.workId,flowid:object3.serviceId]); return json2;}else{return 'failed';}}";
            String script2 = "import groovy.json.*;def jsonSlurper = new JsonSlurper();def object2 = jsonSlurper.parseText(p4);def object3 = jsonSlurper.parseText(p3);return JsonOutput.toJson([workid:object3.id,errcode:object2.errcode]);";
            String time ="import groovy.json.*;def jsonSlurper = new JsonSlurper();def object = jsonSlurper.parseText(p2);def now= new Date();Date start = Date.parse( \"yyyy-MM-dd HH:mm:ss\",  object.activity_start_time);Date end = Date.parse( \"yyyy-MM-dd HH:mm:ss\",  object.activity_end_time);return now.time>start.getTime()&&now.time<end.getTime()&&object.msgreachresult=='';"; 
            Object hello = GroovyShellJsonExample.getShell(scriptname, time, binding);
            System.out.println("(Boolean)hello:"+(Boolean)hello);
//            if((Boolean)hello){
//            	System.out.println("yes");
//            }else{
//            	System.out.println("no");
//            }
//            System.out.println(script);
            System.out.println(hello.toString());
            //生成工单import groovy.json.*;def jsonSlurper = new JsonSlurper();def object2 = jsonSlurper.parseText(p2);def object3 = jsonSlurper.parseText(p3);if(p2.contains('msgFlag')){if(p1==200&&object2.msgFlag=='0'){def json1 = JsonOutput.toJson([workid:object3.workId,flowid:object3.flowId]); return json1;}else{return 'failed';}}else{if(p1==200&&object2.content.mark=='1'){def json2 = JsonOutput.toJson([workid:object3.workId,flowid:object3.serviceId]); return json2;}else{return 'failed';}}
//回写import groovy.json.*;def jsonSlurper = new JsonSlurper();def object2 = jsonSlurper.parseText(p2);def object3 = jsonSlurper.parseText(p3);if(p2.contains('msgFlag')){if(p1==200&&object2.msgFlag=='0'){def json1 = JsonOutput.toJson([workid:object3.workId,bOrderId:object2.resultJson.provOrderId,result:'success']); return json1;}else{def json1 = JsonOutput.toJson([workid:object3.workId,bOrderId:'',result:'failed']); return json1;}}else{if(p1==200&&object2.content.mark=='1'){def json1 = JsonOutput.toJson([workid:object3.workId,bOrderId:object2.content.sequenceId,result:'success']); return json1;}else{def json1 = JsonOutput.toJson([workid:object3.workId,bOrderId:'',result:'failed']); return json1;}}
//wechat
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 为Groovy Script增加缓存
     * 解决Binding的线程安全问题
     *
     * @return
     */
    public static Object getShell(String cacheKey, String script,Binding binding) {

        Map<String, Object> scriptCache = new ConcurrentHashMap<String, Object>();


        Object scriptObject = null;
        try {


            Script shell = null;
            if (scriptCache.containsKey(cacheKey)) {
                shell = (Script) scriptCache.get(cacheKey);
            } else {
                shell = new GroovyShell().parse(script);
                scriptCache.put(cacheKey, shell);
//                shell = cache(cacheKey, script);
            }

            //shell.setBinding(binding);
            //scriptObject = (Object) shell.run();

            scriptObject = (Object) InvokerHelper.createScript(shell.getClass(), binding).run();


            // clear
            binding.getVariables().clear();
            binding = null;

            // Cache
            if (!scriptCache.containsKey(cacheKey)) {
                //shell.setBinding(null);
                scriptCache.put(cacheKey, shell);
            }
        } catch (Throwable t) {
//            t.printStackTrace();
//            System.out.println("groovy script eval error. script: " + script, t);
        	System.out.println("t:"+t);
        }

        return scriptObject;
    }

    /**
     * 传统调用方式；
     */
    private static void oral() {
        Binding binding = new Binding();

        binding.setVariable("x", 10);

        binding.setVariable("language", "Groovy");

        GroovyShell shell = new GroovyShell(binding);

        Object value = shell.evaluate
                ("println\"Welcome to $language\"; y = x * 2; z = x * 3; return x ");

        assert value.equals(10);

        assert binding.getVariable("y").equals(20);

        assert binding.getVariable("z").equals(30);
    }
}
