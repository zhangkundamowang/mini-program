package com.example.wx.xiaochengxu.enums;

public class SeasonTest {
    public static void main(String[] args) {
        SeasonEnum spring = SeasonEnum.SPRING;
        judge(spring);
    }

    public static void judge(SeasonEnum s) {
        switch(s) {
           case SPRING:
             System.out.println("春天适合踏青。");
             break;
           case SUMMER:
             System.out.println("夏天要去游泳啦。");
             break;
           case FALL:
             System.out.println("秋天一定要去旅游哦。");
             break;
           case WINTER:
             System.out.println("冬天要是下雪就好啦。");
             break;
        }
    }

    public enum SeasonEnum {
        SPRING,
        SUMMER,
        FALL,
        WINTER;
     }


}