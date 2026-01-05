package com.incloud.hcp;

import java.math.BigDecimal;

public class TestTemp {
    public static void main(String[] args) {
        String a = "0.0006";
        String b = "0.0005";
        String out = "0.0005";
        BigDecimal outt = new BigDecimal("0.00000005");
        double f = Double.parseDouble(a);
        BigDecimal in = new BigDecimal(a);
        BigDecimal on = new BigDecimal(b);

        if(outt.compareTo(new BigDecimal("0.0")) != 0 ){
            System.out.println("Test");
        }

//        if(in.compareTo(on) < 1){
//            System.out.println("es::"+in.compareTo(on));
//        }else{
//            System.out.println(in.compareTo(on));
//        }


//        System.out.println(String.valueOf(f));
//        System.out.println(String.valueOf(in));
//        System.out.println(f);
//        System.out.println(String.format("%.4f", f));
//        System.out.println(String.format("%.4f", new BigDecimal(f)));
    }
}
