import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static int[] array = new int[]{2, 1, 5, 6, 9, 6, 4, 1, 0};
    static final int CPUS = 3;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(" sum = " + getSum());
        System.out.println(" >4  = " + getQuantityByMax(4));
        System.out.println(" <3  = " + getQuantityByMin(3));
        System.out.println(" max = " + array[getMax()] + "  id = " + getMax());
        System.out.println(" min = " + array[getMin()] + "  id = " + getMin());
        System.out.println("cSum = " + getCheckSum()); 

    }

    
    // 1
    public static int getSum() throws ExecutionException, InterruptedException {
        int value = 0;
        ExecutorService service = Executors.newFixedThreadPool(CPUS);
        ArrayList<Future<Integer>> tasks = new ArrayList<>();

        int blocksize = array.length / CPUS;
        if (((double) array.length / CPUS) > (array.length / CPUS)) {
            blocksize++;
        }
        AtomicInteger temp = new AtomicInteger(0);
        for (int i = 0; i < CPUS; i++) {
            final int start = blocksize * i;
            final int end = Math.min(blocksize * (i + 1), array.length);
           /* if(i==CPUS-1){
            	end = array.length;
            }
            else{
            	end = blocksize*(i+1);
            }*/
            System.out.println("Start: " + start + "End: " + end);
            tasks.add(service.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                	temp.set(0);
                    for (int j = start; j < end; ++j) {
                        temp.addAndGet(array[j]);
                    }
                    return temp.intValue();
                }
            }));
        }
        for (int i = 0; i < tasks.size(); i++) {
            value += tasks.get(i).get();
            System.out.println(tasks.get(i).get());
           // System.out.println(value);
        }
        service.shutdown();
        return value;
    }

    // 2
    public static int getQuantityByMax(int num) throws ExecutionException, InterruptedException {
        int value = 0;
        ExecutorService service = Executors.newFixedThreadPool(CPUS);
        ArrayList<Future<Integer>> tasks = new ArrayList<>();

        int blocksize = array.length / CPUS;
        if (((double) array.length / CPUS) > (array.length / CPUS)) {
            blocksize++;
        }
        AtomicInteger temp = new AtomicInteger(0);
        for (int i = 0; i < CPUS; i++) {
            final int start = blocksize * i;
            final int end = Math.min(blocksize * (i + 1), array.length);
            tasks.add(service.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                	temp.set(0);
                    for (int q = start; q < end; ++q) {
                        if (array[q] > num) {
                            //temp.getAndIncrement();
                             while (true) {
                                int val = temp.get();
                                int incremented = val + 1;
                                if (temp.compareAndSet(val, incremented)) {
                                    break;
                              }
                            }
                        }
                    }
                    return temp.intValue();
                }
            }));
        }
        for (int i = 0; i < tasks.size(); i++) {
            value = value + tasks.get(i).get();
        }
        service.shutdown();
        return value;
    }

    public static int getQuantityByMin(int num) throws ExecutionException, InterruptedException {
        int value = 0;
        ExecutorService service = Executors.newFixedThreadPool(CPUS);
        ArrayList<Future<Integer>> tasks = new ArrayList<>();

        int blocksize = array.length / CPUS;
        if (((double) array.length / CPUS) > (array.length / CPUS)) {
            blocksize++;
        }
        AtomicInteger temp = new AtomicInteger(0);
        for (int i = 0; i < CPUS; i++) {
            final int start = blocksize * i;
            final int end = Math.min(blocksize * (i + 1), array.length);
            tasks.add(service.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                	temp.set(0);
                    for (int q = start; q < end; ++q) {
                        if (array[q] < num) {
                            //temp.getAndIncrement();
                            while (true) {
                                int val = temp.get();
                                int incremented = val + 1;
                                if (temp.compareAndSet(val, incremented)) {
                                    break;
                                }
                            }
                        }
                    }
                    return temp.intValue();
                }
            }));
        }
        for (int i = 0; i < tasks.size(); i++) {
            value = value + tasks.get(i).get();
        }
        service.shutdown();
        return value;
    }
    
    //3
    // Min
    public static int getMin() throws ExecutionException, InterruptedException {
        int value = 0;
        ExecutorService service = Executors.newFixedThreadPool(CPUS);
        ArrayList<Future<Integer>> tasks = new ArrayList<>();

        int blocksize = array.length / CPUS;
        if (((double) array.length / CPUS) > (array.length / CPUS)) {
            blocksize++;
        }
        for (int i = 0; i < CPUS; i++) {
            final int start = blocksize * i;
            final int end = Math.min(blocksize * (i + 1), array.length);
            AtomicInteger temp = new AtomicInteger(0);
            tasks.add(service.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    temp.set(start);
                    for (int q = start; q < end; ++q) {
                        if (array[q] < array[temp.intValue()]) {
                        	while (true) {
                                int val = q;
                                temp.set(val);
                              }
                           
                        }
                    }
                    return temp.intValue();
                }
            }));
        }
        for (int i = 0; i < tasks.size(); i++) {
            if (array[tasks.get(i).get()] < array[tasks.get(value).get()]) {
                value = i;
            }
        }
        service.shutdown();
        return tasks.get(value).get();
    }

    // Max
    public static int getMax() throws ExecutionException, InterruptedException {
        int value = 0;
        ExecutorService service = Executors.newFixedThreadPool(CPUS);
        ArrayList<Future<Integer>> tasks = new ArrayList<>();

        int blocksize = array.length / CPUS;
        if (((double) array.length / CPUS) > (array.length / CPUS)) {
            blocksize++;
        }
        for (int i = 0; i < CPUS; i++) {
            final int start = blocksize * i;
            final int end = Math.min(blocksize * (i + 1), array.length);
            AtomicInteger temp = new AtomicInteger(0);
            tasks.add(service.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    temp.set(start);
                    for (int q = start; q < end; ++q) {
                        if (array[q] > array[temp.intValue()]) {
                        	while (true) {
                                int val = q;
                                temp.set(val);
                              }
                            }
                    }
                    return temp.intValue();
                }
            }));
        }
        for (int i = 0; i < tasks.size(); i++) {
            if (array[tasks.get(i).get()] > array[tasks.get(value).get()]) {
                value = i;
            }
        }
        service.shutdown();
        return tasks.get(value).get();
    }

    // 4
    public static int getCheckSum() throws ExecutionException, InterruptedException {
        int value = 0;
        ExecutorService service = Executors.newFixedThreadPool(CPUS);
        ArrayList<Future<Integer>> tasks = new ArrayList<>();

        int blocksize = array.length / CPUS;
        if (((double) array.length / CPUS) > (array.length / CPUS)) {
            blocksize++;
        }
        for (int i = 0; i < CPUS; i++) {
            final int start = blocksize * i;
            final int end = Math.min(blocksize * (i + 1), array.length);
            AtomicInteger temp = new AtomicInteger(start);
            tasks.add(service.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    temp.set(start);
                    int tempp;
                    for (int q = start; q < end; ++q) {
                        tempp = temp.get();
                        temp.set(tempp ^ array[q]);
                    }
                    return temp.get();
                }
            }));
        }
        for (int i = 0; i < tasks.size(); i++) {
            value ^= tasks.get(i).get();
        }
        service.shutdown();
        return value;
    }
}