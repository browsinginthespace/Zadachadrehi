import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {


    public static void main(String[] args) {
        PriorityQueue<Product> pq = new PriorityQueue<>(Comparator.comparingDouble(p -> p.price));
        ClothesThread r1 = new ClothesThread(new File("src/calvinklein.txt"), pq);
        ClothesThread r2 = new ClothesThread(new File("src/guess.txt"), pq);
        ClothesThread r3 = new ClothesThread(new File("src/trussardi.txt"), pq);


        try (ExecutorService es = Executors.newCachedThreadPool()) {
            es.execute(r1);
            es.execute(r2);
            es.execute(r3);

            es.shutdown();
            es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        try(PrintWriter out = new PrintWriter("src/out.txt")) {
            for(int i = 0; i < 10 && !pq.isEmpty(); i++) {
                out.println(pq.poll().name);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}