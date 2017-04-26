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
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class GroovyShellJsonExample5 {

    public static void main(String args[]) {

//        oral();
        //JSON 的测试例子

        String json="{\"activity_start_time\":\"2016-11-11 00:00:00\",\"activity_end_time\":\"2016-12-29 00:00:00\"}";
        Binding binding = new Binding();
        binding.setVariable("p1","200");
        binding.setVariable("p2", json);

        try {
//            String script = "println\"Welcome to $language\"; y = x * 2; z = x * 3; return x ";
//            Object hello = GroovyShellJsonExample.getShell("hello", script, binding);
        	String scriptname="jsontest";
            String script = "import groovy.json.*;" +
                    "def jsonSlurper = new JsonSlurper();" +
                    "def object = jsonSlurper.parseText(p2);" +
                    "def now= new Date();"+
                    "Calendar cal = Calendar.getInstance();"+
                    "int day = cal.get(Calendar.DATE);"+
                    "int month = cal.get(Calendar.MONTH) + 1;"+
            		"int year = cal.get(Calendar.YEAR);"+
                    "Date start = Date.parse( \"yyyy-MM-dd HH:mm:ss\",  object.activity_start_time);"+
                    "Date end = Date.parse( \"yyyy-MM-dd HH:mm:ss\",  object.activity_end_time);"+
                    "return now.time>start.getTime()&&now.time<end.getTime();";
//                    "return month";
//            File script = new ClassPathResource("/groovy/flume-rule.groovy").getFile();
            Object hello = GroovyShellJsonExample5.getShell(scriptname, script, binding);
            Date date=new Date();
            System.out.println(script);
            System.out.println(hello);
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
            t.printStackTrace();
            //System.out.println("groovy script eval error. script: " + script, t);
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
