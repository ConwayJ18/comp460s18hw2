import java.io.*;
import java.util.*;
/**
 * Counts the number of factors of a large testNumber
 * using 8 different threads.
 *
 * @author Jess Conway
 * @version 02/05/2018
 */
class FactorChecker extends Thread {

  private long threadNumber;
  private long testNumber;
  private int threads;
  private int counter;

  public FactorChecker(long threadNumber, long testNumber, int threads)
  {
     this.threadNumber = threadNumber;
     this.testNumber = testNumber;
     this.threads = threads;
  }

  public void run()
  {
     for(long k = threadNumber; k*k <= testNumber; k+=threads)
     {
          if(testNumber%k == 0)
            counter += 2;
     }
  }

  public int getCounter()
  {
     return counter;
  }

}

class FactorDriver {

    public static void main(String[] args)
    {
        Random randomGenerator = new Random();
        int threads = 8;
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
            for(int k = 0; k < threads; k++)
            {
                thrd[k] = new FactorChecker(k+1, testNumber, threads);
                thrd[k].start();
            }

            for(int k=0;k<threads;k++)
            {
                  try
                  {
                      thrd[k].join();
                  }
                  catch(InterruptedException e){}
                  counter += thrd[k].getCounter();
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
