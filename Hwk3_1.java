import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

public class Hwk3_1
{
    public static void main(String[] args)
    {
        int numGifts = 5;

        // Create bag
        Servant.bag = createBag(numGifts);
        Servant.numGifts = numGifts;
        Servant.numCards = 0;

        // Create chain
        MyLinkedList chain = new MyLinkedList();
        Servant.chain = chain;

        // Run
        // Create 4 Servants
        Servant[] servants = new Servant[4];
        for (int i=0; i<servants.length; i++)
		{
			servants[i] = new Servant(i+1);
			servants[i].start();
		}

        //while (!Servant.finished)
        {
        }
        System.out.println("The servants have successfully completed the task!");
    }

    static ArrayList<Integer> createBag(int numGifts)
    {
        // Put gifts in bag unordered
        ArrayList<Integer> giftBag = new ArrayList<Integer>();

        for (int i=0; i<numGifts; i++)
        {
            giftBag.add(i+1);
        }
        
        // Randomize gift order
        Collections.shuffle(giftBag);
        System.out.println("Gift bag full");
        return giftBag;
    }
}

class Servant extends Thread
{
    public static ArrayList<Integer> bag;
    public static MyLinkedList chain;
    public static boolean finished;
    public static int numGifts, numCards;
    public int num;

    public Servant (int num)
    {
        this.num = num;
    }

    public void run()
    {
        // Get random num
        Random rand = new Random();
        int randInt;
        // Add, Write, or Check
        while (numCards < numGifts)
        {
           randInt = rand.nextInt(3);
           if (randInt == 0)
           {
                addToChain();
           }
           else if (randInt == 1)
           {
                writeLetter();
           }
           else
           {
                checkChain();
           }
        }
        System.out.println("Servant #" + this.num + " is finished with their duty.");
    }

    void addToChain()
    {
        // Add gift to chain
        if (bag.size() == 0)
        {
            System.out.println("Servant #" + this.num + " saw the bag was empty.\n");
            return;
        }
        int gift = bag.remove(0);
        Node pred = chain.add(gift);
        if (pred.val == Integer.MIN_VALUE)
        {
            System.out.println("Servant #" + this.num + " removed gift #" + gift + " and placed it at the beginning of the chain.\n");
        }
        else if (pred.next.next.val == Integer.MAX_VALUE)
        {
            System.out.println("Servant #" + this.num + " removed gift #" + gift + " and placed it at the end of the chain.\n");
        }
        else
        {
            System.out.println("Servant #" + this.num + " removed gift #" + gift + " and placed it between gifts #" + pred.val + " and #" + pred.next.next.val + ".\n");
        }
    }

    void writeLetter()
    {
        // Remove from list and write thank you
        int gift = chain.remove();
        if (gift == -1)
        {
            System.out.println("Servant #" + this.num + " saw no gifts to remove from the chain.\n");
        }
        else
        {
            System.out.println("Servant #" + this.num + " wrote a letter to guest #" + gift 
                + " and removed their gift from the chain.\n");
            numCards++;
        }
    }

    boolean checkChain()
    {
        // See if gift is in chain
        Random rand = new Random();
        int randGift = rand.nextInt(numGifts);
        boolean giftInChain = chain.contains(randGift);
        if (giftInChain)
        {
            System.out.println("Servant #" + this.num + " did not find gift #"
                + randGift + " in the chain.\n");
        }
        else
        {
            System.out.println("Servant #" + this.num + " found gift #"
                + randGift + " in the chain.\n");
        }
        return giftInChain;
    }
}

class Node
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

class MyLinkedList
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

    // Remove specific index
    // public int remove(int val)
    // {
    //     Node pred, curr;
    //     try 
    //     {
    //         pred = this.head;
    //         pred.lock();
    //         curr = pred.next;
    //         curr.lock();
    //         while (curr.val <= val) // SIZE????
    //         {
    //             if (val == curr.val)
    //             {
    //                 pred.next = curr.next;
    //                 return val;
    //             }
    //             pred.unlock();
    //             pred = curr;
    //             curr = curr.next;
    //             curr.lock();
    //         }
    //         return -1;
    //     } finally {
    //         curr.unlock();
    //         pred.unlock();
    //     }
    // }

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