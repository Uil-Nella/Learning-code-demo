///*
//* Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
//* Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
//* Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
//* Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
//* Vestibulum commodo. Ut rhoncus gravida arcu.
//*/
//
//package org.bugkillers.learning.memcache;
//
//import net.spy.memcached.AddrUtil;
//import net.spy.memcached.MemcachedClient;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//
///**
//* Created by liuxinyu on 15/4/7.
//*/
//public class SpyMemcached {
//    public static void main(String[] args) {
//        try{
//            System.out.println("输入新的键值：");
//            BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
//            String key = null;
//            key = reader.readLine();
//            System.out.print("Enter the new value : ");
//            String value = null;
//            value = reader.readLine();
//            MemcachedClient cache = new MemcachedClient(AddrUtil.getAddresses("127.0.0.1:11211"));
//
//            // read the object from memory
//            System.out.println("Get Object before set :"+ cache.get(key)  );
//
//            // set a new object
//            cache.set(key, 0, value );
//
//            System.out.println("Get Object after set :"+ cache.get(key)  );
//
//        }catch (Exception e){
//            Logger.getLogger(SpyMemcached.class.getName()).log(Level.SEVERE, null, e);
//            System.exit(0);
//
//
//        }
//        System.exit(0);
//
//    }
//}
