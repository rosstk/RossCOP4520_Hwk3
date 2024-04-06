# RossCOP4520_Hwk3
## Problem 1
To run this file you need Hwk3_1.java, MyLinkedList.java, Servant.java, and Node.java. Put them in a folder
together and run at the terminal with 'javac Hwk3_1.java' then 'java Hwk3_1'.
The solution to this problem was generating an array ordered arrayList, and then shuffling it to simulate
an unordered bag. Then, only allowing one servant access to the bag at a time. The servants could also
only remove from the front of the list to avoid confusion about what present belong in front of the other.
What went wrong before is that they maybe forgot to connect the pred and the next.

## Problem 2
To run this file you need Hwk3_2.java, Sensor.java, and TempReport.java. Put them in a folder
together and run at the terminal with 'javac Hwk3_2.java' then 'java Hwk3_2'.
The solution to this problem was having one sensor make the report, while the other sensors
continued recording the data. Also atomic integers allowed the sensors to keep track of the hour
and minute they were recording for. The only lock used is for which thread will make the report. This
gives the sensors the ability to not miss a scheduled interval.
