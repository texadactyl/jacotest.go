/* 
    Cached thread pool: scales dynamically, maximizes parallelism for short-lived tasks, but can spawn many threads if task count is high.
*/

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class main {

    static class WorkerTask implements Callable<String> {
        private final int id;

        WorkerTask(int id) {
            this.id = id;
        }

        @Override
        public String call() throws Exception {
            long startTime = System.currentTimeMillis();
            System.out.printf("Task %d started on %s at %d ms\n",
                    id, Thread.currentThread().getName(), startTime);

            long duration = 500 + (long) (Math.random() * 1000);
            Thread.sleep(duration);

            long endTime = System.currentTimeMillis();
            long elapsed = endTime - startTime;

            System.out.printf("Task %d finished on %s after %d ms (at %d ms)\n",
                    id, Thread.currentThread().getName(), elapsed, endTime);

            return String.format("Task %d result (elapsed %d ms)", id, elapsed);
        }
    }

    public static void main(String[] args) {
        int numTasks = 5;

        ExecutorService executor = Executors.newCachedThreadPool();
        CompletionService<String> completionService = new ExecutorCompletionService<>(executor);

        long globalStart = System.currentTimeMillis();
        System.out.printf("Submitting %d tasks to a cached thread pool\n", numTasks);

        // Submit tasks
        for (int i = 1; i <= numTasks; i++) {
            completionService.submit(new WorkerTask(i));
        }

        // Collect results as they complete
        for (int i = 0; i < numTasks; i++) {
            try {
                Future<String> future = completionService.take();
                String result = future.get();
                System.out.printf("Result received: %s%n", result);
            } catch (InterruptedException | ExecutionException e) {
                System.out.printf("*** ERROR (InterruptedException): %s\n", e.getMessage());
            }
        }

        executor.shutdown();
        long globalEnd = System.currentTimeMillis();
        System.out.printf("All tasks completed. Executor shut down.\n");
        System.out.printf("Total elapsed time: %d ms%n", (globalEnd - globalStart));
    }
}

