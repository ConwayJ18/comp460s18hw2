import java.io.*;
import java.util.*;

class MatrixProduct extends Thread {
    private int[][] A;
    private int[][] B;
    private int[][] C;
    private int n;
    private int threads;
    private int threadNumber;

    public MatrixProduct(int[][] A,int[][] B,int[][] C, int n, int threads, int threadNumber)
    {
       this.A=A;
       this.B=B;
       this.C=C;
       this.n=n;
       this.threads=threads;
       this.threadNumber=threadNumber;
    }

   public void run()
   {
     for (int i = 0; i < n; i+= threads) { // aRow
          for (int j = 0; j < n; j++) { // bColumn
              for (int k = 0; k < n; k++) { // aColumn
                  C[i][j] += A[i][k] * B[k][j];
              }
          }
      }
   }
}

class MatrixDriver {

     public static void main(String[] args)
     {
          //Initialize variables
          int threads = 8;
          int n = 5000;
          int max = 100;
          int min = 0;
          Random rand = new Random();
          int[][] A=new int[n][n];
          int[][] B=new int[n][n];
          int[][] C=new int[n][n];
          MatrixProduct[] thrd = new MatrixProduct[threads];

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
          for(int i=0;i<threads;i++)
          {
             thrd[i] = new MatrixProduct(A, B, C, n, threads, i);
             thrd[i].run();
          }

          for(int i=0;i<threads;i++)
          {
                try
                {
                    thrd[i].join();
                }
                catch(InterruptedException e){}
          }

          //Stop clock
          long endTime = System.nanoTime();

          //Calculate time elapsed
          long timeElapsedInMilliseconds = (endTime - startTime) / 1000000;

          //Print results
          System.out.println("The calculation of the matrix of dimension " + n + " took " + timeElapsedInMilliseconds + " milliseconds.");
      }
}
