import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    private static final List<String> data = Arrays.asList(
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d",
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d",
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d",
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d");


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println(doParallelWork(data));
    }

    public static int doParallelWork(List data) throws ExecutionException, InterruptedException {
        Integer counter = 0;
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < data.size(); i += 3) {
            if (i < data.size()) {
                int u = executorService.submit(new MyCallable(data, i)).get();
                counter = increaseCounter(counter, u);
            }
            if (i + 1 < data.size()) {
                int v = executorService.submit(new MyCallable(data, i + 1)).get();
                counter = increaseCounter(counter, v);
            }
            if (i + 2 < data.size()) {
                int w = executorService.submit(new MyCallable(data, i + 2)).get();
                counter = increaseCounter(counter, w);
            }
        }
        executorService.shutdown();
        return counter;
    }

    public static synchronized int increaseCounter(Integer counter, Integer value) {
        counter += value;
        return counter;
    }

    static class MyCallable implements Callable<Integer> {
        private List arr;
        private final int id;
        private Integer lenght;

        public MyCallable(List arr, int id) {
            this.arr = arr;
            this.id = id;
        }

        @Override
        public Integer call() throws Exception {
            this.lenght = data.get(id).toString().length();
            return this.lenght;
        }
    }
}