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

class MatrixDriver {

     public static void main(String[] args)
     {
          Scanner kbReader = new Scanner(System.in);

          System.out.print("Enter a matrix size n: ");
          int n = kbReader.nextInt();
          int[][] A=new int[n][n];
          int[][] B=new int[n][n];
          int[][] C=new int[n][n];
          MatrixProduct[][] thrd= new MatrixProduct[n][n];

          System.out.println("\nInsert A:");
          for(int i=0;i<n;i++)
          {
            for(int j=0;j<n;j++)
            {
                System.out.print(i+","+j+" = ");
                A[i][j]=kbReader.nextInt();
            }
          }

          System.out.println("\nInsert B:");
          for(int i=0;i<n;i++)
          {
            for(int j=0;j<n;j++)
            {
                System.out.print(i+","+j+" = ");
                B[i][j]=kbReader.nextInt();
            }
          }
/*
          System.out.println("\nA=\n");
          for(int i=0;i<n;i++)
          {
              for(int j=0;j<n;j++)
              {
                  System.out.print(A[i][j] + " ");
              }
              System.out.println();
          }

          System.out.println("\nB=\n");
          for(int i=0;i<n;i++)
          {
              for(int j=0;j<n;j++)
              {
                  System.out.print(B[i][j] + " ");
              }
              System.out.println();
          }
*/
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

          System.out.println("\nResult:");
          for(int i=0;i<n;i++)
          {
              for(int j=0;j<n;j++)
              {
                  System.out.print(C[i][j] + " ");
              }
              System.out.println();
          }
      }
}
