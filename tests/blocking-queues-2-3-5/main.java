// Inspiration: https://github.com/golang/go/blob/master/test/235.go

// Solve the 2,3,5 problem (print all numbers with 2, 3, or 5 as a factor).
// Multithreaded.
// Instead of Golang channels, we'll use Java blocking queues.

import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class main {

    private static final int CHANNEL_CAPACITY = 100;

    // Golang channel implementation using a Java BlockingQueue.
    static class Channel {
        private final BlockingQueue<Long> queue;

        public Channel(int capacity) {
            queue = new LinkedBlockingQueue<>(capacity);
        }

        public void send(long value) throws InterruptedException {
            queue.put(value);
        }

        public long receive() throws InterruptedException {
            return queue.take();
        }
    }

    // CreateServerThread object.
    // Function startup:
    // * Create a thread receiving an input value and multiply it by the startup factor.
    // * Return the 2 created channels to the caller.
    static class CreateServerThread {

        public static Channel[] startup(long factor) {
            Channel input = new Channel(CHANNEL_CAPACITY);
            Channel output = new Channel(CHANNEL_CAPACITY);

            // Define a channel server based on the initial factor.
            Thread th = new Thread(() -> {
                try {
                    while (true) {
                        long value = input.receive();
                        // Stop request?
                        if (value < 1L)
                            return; // Yes. Conclude this thread.
                        output.send(value * factor);
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            );

            // Start the thread.
            th.start();

            // Return the 2 channels to caller.
            return new Channel[]{input, output};
        }
    }

    // Helper function to find the minimum value in an array of longs.
    static long minimum(long[] values) {
        long minValue = values[0];
        for (int ix = 1; ix < values.length; ix++) {
            if (values[ix] < minValue) {
                minValue = values[ix];
            }
        }
        return minValue;
    }

    // Main entry point.
    public static void main(String[] args) throws InterruptedException {
        int errorCount = 0;
        // 2, 3, 5:
        long[] factors = {2, 3, 5};
        // Number of channel pairs:
        int numFactors = factors.length;
        long[] expectedOutput = {
            2, 3, 4, 5, 6, 8, 9, 10, 12, 15, 16, 18, 20, 24, 25, 27, 30, 32, 36,
            40, 45, 48, 50, 54, 60, 64, 72, 75, 80, 81, 90, 96, 100, 108, 120, 125,
            128, 135, 144, 150, 160, 162, 180, 192, 200, 216, 225, 240, 243, 250,
            256, 270, 288, 300, 320, 324, 360, 375, 384, 400, 405, 432, 450, 480,
            486, 500, 512, 540, 576, 600, 625, 640, 648, 675, 720, 729, 750, 768,
            800, 810, 864, 900, 960, 972, 1000, 1024, 1080, 1125, 1152, 1200, 1215,
            1250, 1280, 1296, 1350, 1440, 1458, 1500, 1536, 1600};

        long mini = 1;
        Channel[] inputs = new Channel[numFactors];
        Channel[] outputs = new Channel[numFactors];
        long[] values = new long[numFactors];

        // For each factor, create a thread associated with a channel pair
        // and initialize its values entry to 1.
        for (int ix = 0; ix < numFactors; ix++) {
            Channel[] channels = CreateServerThread.startup(factors[ix]);
            inputs[ix] = channels[0];
            outputs[ix] = channels[1];
            values[ix] = mini;
        }

        // Process all the expected output.
        long expected;
        for (int jx = 0; jx < expectedOutput.length; jx++) {
            expected = expectedOutput[jx];

            // Send mini to all threads.
            for (int ix = 0; ix < numFactors; ix++) {
                inputs[ix].send(mini);
            }

            // For all threads: if the current value is unchanged, receive a new value.
            for (int ix = 0; ix < numFactors; ix++) {
                if (values[ix] == mini) {
                    values[ix] = outputs[ix].receive();
                }
            }

            // mini = minimum value over all threads.
            mini = minimum(values);
            errorCount += Checkers.checker(String.format("expectedOutput[%d] correct?", jx), expected, mini);
            if (mini != expected) {
                break; // break loop on first error
            }
            
         }

        // Send stop request to all threads.
        for (int ix = 0; ix < numFactors; ix++) {
            inputs[ix].send(0L);
        }

        Checkers.theEnd(0);
    }
}


