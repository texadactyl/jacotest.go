/*
* The Computer Language Benchmarks Game
* https://benchmarksgame-team.pages.debian.net/benchmarksgame/
*
* Based on contribution of Eckehard Berns
* Based on code by Heiner Marxen
* and the ATS version by Hongwei Xi
* convert to Java by The Anh Tran
*
* FIX: The original code conflated two unrelated concepts under a single
* field, "nthreads":
*   1) the size of the permutation to enumerate (i.e. N in "fannkuch(N)")
*   2) the number of worker threads to use
* Currently, leave this number at 9 or 10 due to Jacobin lengthy loop-compute time.
*
* Because "nthreads" was set to 16 and used as the permutation size, the
* program enumerated permutations of 16 elements (16! ~= 2.09 x 10^13),
* which for all practical purposes never finishes. The thread count was
* actually being taken from Runtime.getRuntime().availableProcessors(),
* so "nthreads" as declared was not even controlling thread count.
*
* This version separates the two:
*   - "lenPermutation" is the permutation size (the fannkuch problem size), set to a
*     benchmark-appropriate value (7-12 is typical).
*   - "numThreads" is the actual worker thread count, taken from the
*     number of available processors, same as before.
*/

import java.util.concurrent.atomic.AtomicInteger;

public class fannkuch implements Runnable
{
    // Problem size: length of the permutation to enumerate.
    // This is what the benchmark calls "N" in "Pfannkuchen(N) = ...".
    // Typical benchmark values are 7 through 12.
    private final int lenPermutation = 9; // TODO: >9 is a Jacobin performance issue.

    // Number of worker threads. Independent of the problem size.
    private final int numThreads = Runtime.getRuntime().availableProcessors();

    // Permutation print count.
    private final int ppc = 30;

    private int[] flip_max_arr;
    private static AtomicInteger remain_task = new AtomicInteger(0);
    
    public fannkuch() {
        System.out.format("Length of the permutation to enumerate = %d\n", lenPermutation);
        System.out.format("Permutation print count = %d\n", ppc);
        System.out.format("Number of worker threads, independent of the problem size = %d\n", numThreads);
    }

    public void execute()
    {
        flip_max_arr = new int[lenPermutation];
        int result = fank_game();
        System.out.format("Pfannkuchen(%d) = %d\n", lenPermutation, result);
    }

    private int fank_game()
    {
        Thread[] th = new Thread[numThreads];
        for (int i = 0; i < th.length; i++)
        {
            th[i] = new Thread(this);
            th[i].start();
        }
        System.out.format("fank_game: Thread start count = %d\n", th.length);

        print_ppc_permut();

        System.out.println("fank_game: Finished print_ppc_permut()");
        final long t1 = System.currentTimeMillis();
        for (Thread t : th)
        {
            try {
                System.out.format("fank_game: Try to join %s .....\n", t.getName());
                t.join();
            }
            catch (InterruptedException ie)
            {   }
        }
        final long t2 = System.currentTimeMillis();
        double elapsedSeconds = (double)(t2 - t1) / 1000.0;
        System.out.format("fank_game: All threads joined, elapsed time (seconds): %.3f\n", elapsedSeconds);

        int mx = 0;
        for (int i : flip_max_arr)
            if (mx < i)
                mx = i;
        return mx;
    }

    // In order to divide tasks 'equally' for many threads, permut generation
    // strategy is different than that of original single thread.
    // This function will 'correctly' print first ppc permutations.
    private void print_ppc_permut()
    {
        // declare and initialize
        int[] permutation = new int[lenPermutation];
        for ( int i = 0; i < lenPermutation; i++ )
        {
            permutation[i] = i;
            System.out.print((1 + i));
        }
        System.out.println();

        int[] perm_remain = new int[lenPermutation];
        for ( int i = 1; i <= lenPermutation; i++ )
            perm_remain[i -1] = i;

        int numPermutationsPrinted = 1;
        for ( int pos_right = 2; pos_right <= lenPermutation; pos_right++ )
        {
            int pos_left = pos_right -1;
            do
            {
                // rotate down perm[0..prev] by one
                next_perm(permutation, pos_left);

                if (--perm_remain[pos_left] > 0)
                {
                    if (numPermutationsPrinted++ < ppc)
                    {
                        for (int i = 0; i < lenPermutation; ++i)
                            System.out.print((1 + permutation[i]));
                        System.out.println();
                    }
                    else
                        return;

                    for ( ; pos_left != 1; --pos_left)
                        perm_remain[pos_left -1] = pos_left;
                }
                else
                    ++pos_left;
            } while (pos_left < pos_right);
        }
    }

    public void run()
    {
        int[] permutation = new int [lenPermutation];
        int[] perm_remain = new int [lenPermutation];
        int[] perm_flip = new int [lenPermutation];
        int pos_right;

        while ((pos_right = remain_task.getAndIncrement()) < (lenPermutation - 1))
        {
            int flip_max = 0;

            for (int i = 0; i < lenPermutation - 1; i++)
                permutation[i] = i;

            permutation[pos_right] = (lenPermutation - 1);
            permutation[lenPermutation - 1] = (pos_right);

            for (int i = 1; i <= lenPermutation; i++)
                perm_remain[i - 1] = i;

            int pos_left = lenPermutation - 2;
            while (pos_left < lenPermutation - 1)
            {
                // rotate down perm[0..r] by one
                next_perm(permutation, pos_left);

                if (--perm_remain[pos_left] > 0)
                {
                    for (; pos_left != 1; --pos_left)
                        perm_remain[pos_left - 1] = pos_left;

                    if ((permutation[0] != 0) && (permutation[lenPermutation - 1] != (lenPermutation - 1)))
                    {
                        System.arraycopy(permutation, 0, perm_flip, 0, lenPermutation);
                        int flipcount = count_flip(perm_flip);
                        if (flip_max < flipcount)
                            flip_max = flipcount;
                    }
                }
                else
                    pos_left++;
            }

            // update max_flip foreach flipping position
            flip_max_arr[pos_right] = flip_max;
        }
    }


    // Take a permut array, continuously flipping until first element is '1'
    // Return flipping times
    private static int count_flip(int[] perm_flip)
    {
        Thread.yield();

        // cache first element, avoid swapping perm[0] and perm[k]
        int v0 = perm_flip[0];
        int tmp;

        int flip_count = 0;
        do
        {
            for (int i = 1, j = v0 - 1; i < j; ++i, --j)
            {
                tmp = perm_flip[i];
                perm_flip[i] = perm_flip[j];
                perm_flip[j] = tmp;
            }

            tmp = perm_flip[v0];
            perm_flip[v0] = v0;
            v0 = tmp;

            flip_count++;
        } while (v0 != 0); // first element == '1' ?

        return flip_count;
    }

    // Return next permut, by rotating elements [0 - position] one 'step'
    // next_perm('1234', 2) -> '2314'
    private static void next_perm(int[] permutation, int position)
    {
        int perm0 = permutation[0];

        for (int i = 0; i < position; ++i)
            permutation[i] = permutation[i + 1];
        permutation[position] = perm0;
    }

 }
