package com.effective;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by shidu on 17/10/26.
 */

public class Test {
    public static void main(String[] args) {
        //effective 19
//        Long sum = 0l; //8.276
        long sum = 0l; //0.906
        long t1 = System.currentTimeMillis();
        System.out.println("sum start | " + t1);
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        System.out.println(sum + " | cost = " + (System.currentTimeMillis() - t1) / 1000.0);

        //
        Point p = new Point(1, 2);
        ColorPoint cp = new ColorPoint(1, 2, Color.black);
        System.out.println("p.equals(cp) = " + p.equals(cp));
        System.out.println("cp.equals(p) = " + cp.equals(p));

        Integer a[] = {1,2};
        Arrays.sort(new Integer[]{1, 2, 3}, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        });

//        BigInteger flipbit
//        Properties properties = new Properties();
//        properties.get("");
//        properties.getProperty("");

        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
//        String[] strings = {"1","2","4","3"};
        String[] strings = {"1"};
        list.toArray(strings);
        System.out.println("strings length = " + strings.length);
        for (String item: strings){
            System.out.println("item = " + item);
        }

//        Object[] objects = new Long[1];
//        objects[0] = "I don't fit in"; //ArrayStoreException
//        List<Object> ol = new ArrayList<Long>(); //编译不过
//        ol.add("I don't fit in");

//        E[]

        Class clazz = String.class;
        System.out.println("clazz = " + clazz);

//        Annotation annotation = new Annotation() {
//            @Override
//            public Class<? extends Annotation> annotationType() {
//                return null;
//            }
//        };

        System.out.println(1.03 - .42); //0.6100000000000001
        System.out.println(0.7 - .30); //0.39999999999999997
        howManyCandies1();

        String rating = null;
//        if (rating.equals("") || rating == null) {
        if (rating == null || rating.equals("")  ) {
//        if ((rating == "" || rating == null)) {
            System.out.println("evan equals ? = ");
        } else {
            System.out.println("equals else");
        }

        float fRating = 0;
        try {
            fRating = Float.parseFloat(rating);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        System.out.println("fRating = "+fRating);
    }

    // Broken - uses floating point for monetary calculation!
    public static void howManyCandies1() {
        double funds = 1.00;
        int itemsBought = 0;
        for (double price = .10; funds >= price; price += .10) {
            funds -= price;
            System.out.println("funds = "+funds);
            itemsBought++;
        }
        System.out.println(itemsBought + " items bought.");
        System.out.println("Change: $" + funds);
    }

}
