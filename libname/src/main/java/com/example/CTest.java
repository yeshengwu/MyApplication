package com.example;

/**
 * Created by shidu on 17/12/14.
 */

public class CTest {

    public static void main(String[] args) {
        int w[][]={{0,1,0,0,0,0,0},{0,1,3,5,7,9,0},{0,2,4,6,8,1,0},{0,3,4,5,6,7,0},{0,2,3,4,9,1,0}};
//        int e[4][5];
        int e[][] = new int[4][5];

        int i,j;
        for(i=0;i<=4;i++)
        {
            for(j=0;j<=6;j++)
            {
                //            fprintf(fp,"%d  ",w[i][j]);
//                printf("%d ",w[i][j]);
                System.out.print(w[i][j]);
            }
            //        fprintf(fp,"\n");
//            printf("\n");
            System.out.print("\n");
        }
//        printf("before \n");
        System.out.print("before \n");

        for(i=1;i<=4;i++)
        {
            for(j=1;j<=5;j++)
            {
                if(w[i][j]>=w[i][j+1]&&w[i][j]>=w[i][j-1]&&w[i][j]>=w[i+1][j]/*&&w[i-1][j]*/){
                    e[i][j]=1;
                }else {
                    e[i][j]=0;
                }
            }
        }
//    fp=fopen("FFF.txt","w");
        System.out.print(w[0][0]);
        System.out.print("w\n");
        for(i=0;i<=4;i++)
        {
            for(j=0;j<=6;j++)
            {
//            fprintf(fp,"%d  ",w[i][j]);
//                printf("%d ",w[i][j]);
                System.out.print(w[i][j]);
            }
//        fprintf(fp,"\n");
//            printf("\n");
            System.out.print("\n");
        }
//    fprintf(fp,"\n");
//        printf("\n");
        System.out.print("\n");
//        printf("e\n");
        System.out.print("e\n");
        for(i=1;i<=4;i++)
        {
            for(j=1;j<=5;j++)
            {
//            fprintf(fp,"%d  ",e[i][j]);
//                printf("%d ",e[i][j]);
                System.out.print(w[i][j]);
            }
//        fprintf(fp,"\n");
//            printf("\n");
            System.out.print("\n");
        }
    }
}
