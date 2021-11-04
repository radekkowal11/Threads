import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MultiThreadsTest {

    private static List<Integer> integerList = new ArrayList<>(Collections.nCopies(1000, 1));

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();

        List<FutureTask<Integer>> futureTasks = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Callable<Integer> callable = new SumCalculator(i * 10, i * 10 + 10);

            FutureTask<Integer> futureTask = new FutureTask<>(callable);
            Thread thread = new Thread(futureTask);

            futureTasks.add(futureTask);
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();

        }

        int sum = 0;
        for (FutureTask<Integer> futureTask : futureTasks) {
            sum += futureTask.get();
        }

        long time = System.currentTimeMillis() - start;

        System.out.println(sum);
        System.out.println("time = " + time);
        System.out.println("Koniec main");

    }

    private static class SumCalculator implements Callable<Integer> {
        private int from;
        private int to;

        public SumCalculator(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public Integer call() throws Exception {
            int suma = 0;
            for (int i = from; i < to; i++) {
                suma += integerList.get(i);
                Thread.sleep(1);
            }

            return suma;
        }
    }
}


