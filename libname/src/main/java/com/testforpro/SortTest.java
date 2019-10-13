package com.testforpro;

public class SortTest {


    public static void main(String[] args) {

//        int[] a = new int[]{2, 3, 6, 5,1};
        int[] a = new int[]{3, 4, 1, 5,2,0};
//        int[] bubble = bubbleSort(a);
//        for (int item : bubble) {
//            System.out.println(item);
//        }

        for (int j = 0; j < a.length; j++) {
            System.out.println(j);
        }
        System.out.println("1/2 = "+1/2);
        System.out.println("--------------");

        int[] bubble2 = bubbleSort2(a);
        for (int item : bubble2) {
            System.out.println(item);
        }

//        binarySearch(bubble2,3);
        binarySearch(bubble2,7);
//        binarySearch(bubble2,5);

    }

    public static int[] bubbleSort(int[] a) {
        if (a == null || a.length == 0) return null;

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }

        return a;
    }

    public static int[] bubbleSort2(int[] a) {
        if (a == null || a.length == 0) return null;

        // 2, 3, 6, 5,1  测试不通过
//        for (int i = 0; i < a.length; i++) {
//            for (int j = i+1; j < a.length -1; j++) {
//                if (a[i] > a[j]) {
//                    int temp = a[i];
//                    a[i] = a[j];
//                    a[j] = temp;
//                }
//            }
//        }

        for (int i = 0; i < a.length -1 ; i++) {
            for (int j = i+1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }

        return a;
    }

    // 前提： 保证从小到大有序，数组存储
    public static int binarySearch(int[] a, int b){

        if (a == null || a.length == 0) return -1;
        for (int item : a) {
            System.out.println("search item = "+item);
        }

        int low = 0;
        int high = a.length -1;

        while (low <= high) {
            System.out.println("while loop");
            int mid = (low+high)/2;
            if (b==a[mid]){
                System.out.println("search  target. "+" low = "+low+" high = "+high+" mid = "+mid);
                return mid;
            } else if (b<a[mid]){
                high = mid -1;
                System.out.println("search in low part: "+"low = "+low+ " high = "+high+" mid = "+mid);
            } else {
                low = mid + 1;
                System.out.println("search in high part: "+"low = "+low+ " high = "+high+" mid = "+mid);
            }
        }
        System.out.println("search no");
        return -1;
    }


}
