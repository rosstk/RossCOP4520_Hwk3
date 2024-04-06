import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

public class Hwk3_1
{
    public static void main(String[] args)
    {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter the number of gifts: ");
        int numGifts = s.nextInt();

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

