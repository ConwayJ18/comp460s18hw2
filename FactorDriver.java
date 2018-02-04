import java.io.*;
import java.util.*;

class FactorChecker extends Thread {

  private int threadNumber;
  private long testNumber;
  private int counter;

  public FactorChecker(int threadNumber, long testNumber)
  {
     this.threadNumber = threadNumber;
     this.testNumber = testNumber;
  }

 public void run()
 {
     for(int k = threadNumber; k <= Math.sqrt(testNumber); k+=8)
     {
         if(testNumber%k == 0)
           counter += 2;
     }
 }

}


class FactorDriver {

    public static void main(String[] args)
    {
        Random randomGenerator = new Random();
        FactorChecker[] thrd= new FactorChecker[8];
        double sum;
        double avg;

        for(int i=12; i<=18; i++)
        {
          sum = 0;
          for(int j=0; j<=10; j++)
          {
            long lowerBound = (long)Math.pow(10,i-1);
            long upperBound = (long)Math.pow(10,i-1) + (long)Math.pow(10,i-1)-1;
            long testNumber = lowerBound + (long)(randomGenerator.nextDouble()*(upperBound-lowerBound));
            int counter = 0;


    		    //Start clock
            long startTime = System.nanoTime();

            //Start calculation
            for(int k = 0; k < 8; k++)
            {
                thrd[k] = new FactorChecker(k+1, testNumber);
                thrd[k].run();
            }

            //Stop clock
            long endTime = System.nanoTime();

            //Calculate time elapsed
            long timeElapsedInMilliseconds = (endTime - startTime) / 1000000;

            //Print results
            System.out.println("The number of factors of the number " + testNumber + " is " + counter + ".");
            System.out.println("That calculation took " + timeElapsedInMilliseconds + " milliseconds.");
            sum += timeElapsedInMilliseconds;
          }
          avg = sum/10;
          System.out.println("The average time taken for a " + i + " digit number is " + avg + " ms.");
        }
    }

}
