import java.util.*;
import java.lang.*;

//static scheduling
class DynamicFactorDriver extends Thread
{
        static int numDigits = 18;
        static long lowerBound = (long)Math.pow(10,numDigits-1);
        static long upperBound = (long)Math.pow(10,numDigits-1) + (long)Math.pow(10,numDigits-1)-1;
        static int numThreads=2;
        static int counter=2;
        static long testNumber;
        static long sK=2;
        long k;

        public void run()
        {
            while (sK*sK<testNumber)
            {
                synchronized(this)
                {
                    k=sK;
                    sK+=8;
                }
                
                for(long i=k; i<k+8; i++)
                {
                  if(testNumber%i==0)
                    counter += 2;
                }
            }

        }

        public static void createTestNumber()
        {
          Random randomGenerator = new Random();
          testNumber = lowerBound + (long)(randomGenerator.nextDouble()*(upperBound-lowerBound));
        }

        public static void main(String args[]) throws Exception
        {
            new DynamicFactorDriver().createTestNumber();
            DynamicFactorDriver[] th =new DynamicFactorDriver[numThreads];
            long t1=System.currentTimeMillis();
            for(int i=0;i<numThreads;i++)
            {
                th[i]=new DynamicFactorDriver();
                th[i].start();
            }

            for(int i=0;i<numThreads;i++)
            {
                th[i].join();
            }
            long t2=System.currentTimeMillis();
            System.out.println("The number of factors of the number " + testNumber + " is " + counter + ".");
            System.out.println("That calculation took " + (t2-t1) + " milliseconds.");
        }
}
