//package org.lilacseeking.course.app.Utils;
//
//import org.red5.server.adapter.ApplicationAdapter;
//import org.red5.server.api.IConnection;
//
//public class Red5Util extends ApplicationAdapter {
//    @Override
//    public boolean appConnect(IConnection conn, Object[] params) {
//        System.out.println("连接");
//        return true;
//    }
//
//    public String change(String str) {
//        System.out.println("客户端调用服务器");
//        return str.toUpperCase();
//    }
//
//    public Boolean appStart(){
//        return true;
//    }
//
//    public void appStop(){
//
//    }
//}
