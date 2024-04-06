import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;
import java.lang.Math;
import java.util.concurrent.atomic.AtomicInteger;

public class Sensor extends Thread
{
    public static boolean startReport;
    public static AtomicInteger hour = new AtomicInteger();
    public static AtomicInteger minute = new AtomicInteger();
    public static ReentrantLock report = new ReentrantLock();
    public static ArrayList<TempReport> tempReps = new ArrayList<TempReport>();
    public static int numHours;

    public int num;

    public Sensor(int num)
    {
        this.num = num;
    }

    public void run()
    {
        while (hour.get() < numHours)
        {
            if (minute.get() == 60)
            {
                startReport = true;
                try
                {
                    report.lock();
                    if (startReport)
                    {
                        startReport = false;
                        tempReps.add(new TempReport());
                        minute = new AtomicInteger();
                        makeReport(hour.getAndIncrement());
                    }
                    
                } finally {
                    report.unlock();
                }
            }
            storeTemp();
        }
    }

    void makeReport(int hour)
    {
        TempReport rep = tempReps.get(hour);

        for (int i=5; i<60; i++)
        {
            Arrays.sort(rep.hTempArr);
            Arrays.sort(rep.lTempArr);

            if (i<5)
            {
                rep.hTempArr[i] = rep.vals[i];
                rep.lTempArr[i] = rep.vals[i];
            }

            if (i>=6)
            {
                if (rep.vals[i] > rep.hTempArr[0])
                {
                    rep.hTempArr[0] = rep.vals[i];
                }
                if (rep.vals[i] < rep.lTempArr[4])
                {
                    rep.lTempArr[4] = rep.vals[i];
                }
            }

            if (i == 9)
            {
                for (int j=9; j>0; j--)
                {
                    rep.hIntervalTot 
                        += Math.abs(rep.vals[i-j] - rep.vals[i-j+1]);
                }
            }

            if (i>=10)
            {
                int diff = 0;
                for (int j=9; j>0; j--)
                {
                    diff += Math.abs(rep.vals[i-j] - rep.vals[i-j+1]);
                }
                if (diff > rep.hIntervalTot)
                {
                    rep.hIntervalTot = diff;
                    rep.hIntervalEnd = i;
                }
            }
        }

        System.out.println("\nSensor " + this.num + ", hour " + (hour+1) + " report:");
        System.out.print("The highest temperatures of the hour: " + rep.hTempArr[0]);
        for (int i=0; i<4; i++)
        {
            System.out.print(", " + rep.hTempArr[i]);
        }

        System.out.print("\nThe lowest temperatures of the hour: " + rep.lTempArr[4]);
        for (int i=3; i>=0; i--)
        {
            System.out.print(", " + rep.lTempArr[i]);
        }
        System.out.print("\nFrom minute " + (rep.hIntervalEnd-9+1) + " to " + (rep.hIntervalEnd+1));
        System.out.print(", the temperature fluctuated a total of " + rep.hIntervalTot + " degrees.\n");
    }

    void storeTemp()
    {
        int curHour = hour.get();
        int curMin = minute.getAndIncrement();
        if (curMin >= 60)
        {
            while (curHour == hour.get()){}
            curHour = hour.get();
            curMin = minute.getAndIncrement();
        }

        // Get temp (-100F to 70F)
        Random rand = new Random();
        int randInt = rand.nextInt(171) - 100;

        // Store in correct place
        tempReps.get(curHour).vals[curMin%60] = randInt;
        System.out.println("\nSensor " + this.num + " recorded " + randInt + " at minute " + (curMin+1) + ".");
    }
}