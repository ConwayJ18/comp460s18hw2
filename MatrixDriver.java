import java.util.*;
import java.util.concurrent.TimeUnit;

class MatrixDriver extends Thread {
    private static int[][] A;
    private static int[][] B;
    private static int[][] C;
    private static int n;
    private static int threads;
    private int threadNumber;

    public MatrixDriver(int threadNumber)
    {
       this.threadNumber=threadNumber;
    }

   public void run()
   {
       for (int i = threadNumber; i < n; i+= threads) { // aRow

            for (int j = 0; j < n; j++) { // bColumn
                for (int k = 0; k < n; k++) { // aColumn
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
   }


   public static void main(String[] args)
   {
        threads = 8;
        n = 10000;
        int max = 100;
        int min = 0;
        Random rand = new Random();
        A=new int[n][n];
        B=new int[n][n];
        C=new int[n][n];
        MatrixDriver[] thrd = new MatrixDriver[threads];

        //Create A
        for(int i=0;i<n;i++)
        {
          for(int j=0;j<n;j++)
          {
              A[i][j]=rand.nextInt((max - min) + 1) + min;
          }
        }
        System.out.println("Matrix A generated.");

        //Create B
        for(int i=0;i<n;i++)
        {
          for(int j=0;j<n;j++)
          {
             B[i][j]=rand.nextInt((max - min) + 1) + min;
          }
        }
        System.out.println("Matrix B generated.");

        //Start clock
        long startTime = System.currentTimeMillis();

        //Run calculation
        for(int i=0;i<threads;i++)
        {
           thrd[i] = new MatrixDriver(i);
           thrd[i].start();
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
        long endTime = System.currentTimeMillis();

        //Calculate time elapsed
        long timeElapsedSeconds = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);

        //Print results
        System.out.println("The calculation of the matrix of dimension " + n + " took " + timeElapsedSeconds + " seconds.");
    }
}
