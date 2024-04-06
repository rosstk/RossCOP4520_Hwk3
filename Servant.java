import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

public class Servant extends Thread
{
    public static ArrayList<Integer> bag;
    public static MyLinkedList chain;
    public static ReentrantLock addLock = new ReentrantLock();
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
        int gift;
        try
        {
            addLock.lock();

            // Add gift to chain
            if (bag.size() == 0)
            {
                System.out.println("Servant #" + this.num + " saw the bag was empty.\n");
                return;
            }
            gift = bag.remove(0);
        } finally {
            addLock.unlock();
        }
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