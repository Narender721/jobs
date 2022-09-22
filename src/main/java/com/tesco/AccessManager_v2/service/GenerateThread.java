//package com.tesco.AccessManager_v2.service;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.server.HandlerFunction;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import reactor.core.publisher.Mono;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Service
//public class GenerateThread {
//    public HandlerFunction<ServerResponse> threadDump() throws Exception {
//        ProcessBuilder builder = new ProcessBuilder("jps", "-l");
////        ProcessBuilder builder = new ProcessBuilder("jstack", "-l","19412");
//        builder.redirectErrorStream(false);
//        Process p = builder.start();
//        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
//        String line;
//        while (true) {
//            line = r.readLine();
//            if (line == null) {
//                // System.out.println("Null Value");
//                break;}
////            System.out.println(line);//Display each line of  data
//            // System.out.println("Divider");
//            Pattern pro = Pattern.compile("\"([^\"]*)\"");
//            Matcher m = pro.matcher(line);
//            while (m.find()) {
////                return m.group(1);
//                System.out.println(m.group(1));
//            }
//
//        }
//        return (HandlerFunction<ServerResponse>) ServerResponse.ok();
//    }
//}