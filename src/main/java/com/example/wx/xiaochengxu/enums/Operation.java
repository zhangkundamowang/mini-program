package com.example.wx.xiaochengxu.enums;

public enum Operation {
    PLUS{
        @Override
        public double eval(double x, double y) {
            return x + y;
        }
    },

    MINUS{
        @Override
        public double eval(double x, double y) {
            return x - y;
        }
    },

    TIMES{
        @Override
        public double eval(double x, double y) {
            return x * y;
        }
    },

    DIVIDE{
        @Override
        public double eval(double x, double y) {
            return x / y;
        }

    };

    /**
     * 抽象方法，由不同的枚举值提供不同的实现。
     * @param x
     * @param y
     * @return
     */
    public abstract double eval(double x, double y);

    public static void main(String[] args) {
        System.out.println(Operation.PLUS.eval(10, 2));
        System.out.println(Operation.MINUS.eval(10, 2));
        System.out.println(Operation.TIMES.eval(10, 2));
        System.out.println(Operation.DIVIDE.eval(10, 2));
    }
}
