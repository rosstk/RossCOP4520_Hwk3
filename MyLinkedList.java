import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

public class MyLinkedList
{
    Node head;

    public MyLinkedList()
    {
        head = new Node(Integer.MIN_VALUE);
        head.next = new Node(Integer.MAX_VALUE);
    }

    public Node add(int val)
    {
        Node pred, curr;
        pred = this.head;
        curr = pred.next;
        try
        {
            Node node = new Node(val);
            pred.lock();
            curr.lock();
            while (val >= curr.val)
            {
                pred.unlock();
                pred = curr;
                curr = curr.next;
                curr.lock();
            }
            pred.next = node;
            node.next = curr;
            return pred;
        } finally {
            pred.unlock();
            curr.unlock();
        }   
    }

    // Remove at beginning of list
    public int remove()
    {
        Node pred, curr;
        pred = this.head;
        curr = pred.next;
        try 
        {
            pred.lock();
            curr.lock();
            if (curr.val == Integer.MAX_VALUE)
            {
                return -1;
            }
            pred.next = curr.next;
            return curr.val;
        } finally {
            curr.unlock();
            pred.unlock();
        }
    }

    public boolean contains(int val)
    {
        Node curr = this.head;
        while (curr.val <= val)
        {
            if (curr.val == val)
            {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }
}