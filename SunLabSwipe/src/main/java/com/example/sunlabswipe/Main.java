package com.example.sunlabswipe;

import javafx.application.Application;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws SQLException {
        ScanSystem.connect();
        Scanner scanner=new Scanner(System.in);
        while (true){
            System.out.println("Enter Type of Access (Authorized or Swipe): ");
            String access=scanner.next();
            if(access.equals("Authorized")){
                new Thread(() -> Application.launch(SunLabApp.class)).start();
                break;
            }else if(access.equals("Swipe")){
                System.out.println("Please Enter ID Number: ");
                Pattern p=Pattern.compile("%A\\d{9}");
                String id=scanner.next();
                Matcher m=p.matcher(id);
                System.out.println("Type 'IN' or 'OUT': ");
                String inside=scanner.next();
                Timestamp ts=new Timestamp(System.currentTimeMillis());
                if(m.find()){
                    if((inside.equals("IN")||inside.equals("OUT"))){
                        ScanSystem.addAccess(Integer.parseInt(id.substring(m.start()+2,m.end())),ts,inside);
                    }else{
                        System.out.println("No Access");
                    }
                }else{
                    System.out.println("No Access");
                }
                break;
            }else{
                System.out.println("Please Retry Access type");
            }
        }
    }
}
