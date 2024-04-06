import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;
import java.lang.Math;
import java.util.concurrent.atomic.AtomicInteger;

public class TempReport
{
    public int[] hTempArr, lTempArr, vals;
    public int hIntervalEnd, hIntervalTot;

    public TempReport()
    {
        hTempArr = new int[5];
        lTempArr = new int[5];

        for (int i=0; i<5; i++)
        {
            hTempArr[i] = Integer.MIN_VALUE;
            lTempArr[i] = Integer.MAX_VALUE;
        }
        hIntervalEnd = 9;
        hIntervalTot = 0;
        vals = new int[60];
    }
}