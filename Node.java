import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

public class Node
{
    public Node next;
    public int val;
    ReentrantLock nodeLock;

    public Node(int val)
    {
        this.val = val;
        nodeLock = new ReentrantLock();
    }

    public void lock()
    {
        nodeLock.lock();
    }

    public void unlock()
    {
        nodeLock.unlock();
    }
}