import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;
import java.lang.Math;
import java.util.concurrent.atomic.AtomicInteger;

public class Hwk3_2
{
    public static void main(String[] args)
    {
        int numSensors = 8;

        Scanner s = new Scanner(System.in);
        System.out.print("How many hours are you recording: ");
        int numHours = s.nextInt();

        // Create sensors
        Sensor[] sensors = new Sensor[numSensors];
        Sensor.tempReps.add(new TempReport());
        Sensor.numHours = numHours;
        for (int i=0; i<numSensors; i++)
        {
            sensors[i] = new Sensor(i+1);
            sensors[i].start();
        }
    }
}