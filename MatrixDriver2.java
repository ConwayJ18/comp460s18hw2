import java.io.*;
import java.util.*;

class MatrixProduct extends Thread {
    private int[][] A;
    private int[][] B;
    private int[][] C;
    private int row;
    private int col;
    private int n;

    public MatrixProduct(int[][] A,int[][] B,int[][] C, int row, int col, int n)
    {
       this.A=A;
       this.B=B;
       this.C=C;
       this.row=row;
       this.col=col;
       this.n=n;
    }

   public void run()
   {
       for(int i=0;i<n;i++)
       {
            C[row][col]+= A[row][i]*B[i][col];
       }
   }
}

class MatrixDriver2 {

     public static void main(String[] args)
     {
          //Initialize variables
          int n = 2000;
          int max = 100;
          int min = 0;
          Random rand = new Random();
          int[][] A=new int[n][n];
          int[][] B=new int[n][n];
          int[][] C=new int[n][n];
          MatrixProduct[][] thrd= new MatrixProduct[n][n];

          //Create A
          for(int i=0;i<n;i++)
          {
            for(int j=0;j<n;j++)
            {
                A[i][j]=rand.nextInt((max - min) + 1) + min;
            }
          }

          //Create B
          for(int i=0;i<n;i++)
          {
            for(int j=0;j<n;j++)
            {
                B[i][j]=rand.nextInt((max - min) + 1) + min;
            }
          }

          //Start clock
          long startTime = System.nanoTime();

          //Run calculation
          for(int i=0;i<n;i++)
          {
             for(int j=0;j<n;j++)
              {
                thrd[i][j]=new MatrixProduct(A,B,C,i,j,n);
                thrd[i][j].start();
              }
          }

          for(int i=0;i<n;i++)
          {
              for(int j=0;j<n;j++)
              {
                  try
                  {
                      thrd[i][j].join();
                  }
                  catch(InterruptedException e){}
              }
          }

          //Stop clock
          long endTime = System.nanoTime();

          //Calculate time elapsed
          long timeElapsedInMilliseconds = (endTime - startTime) / 1000000;

          //Print results
          System.out.println("The calculation of the matrix of dimension " + n + " took " + timeElapsedInMilliseconds + " milliseconds.");
      }
}
