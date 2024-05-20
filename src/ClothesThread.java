import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class ClothesThread implements Runnable{
    public File f;
    public final PriorityQueue<Product> container;

    public ClothesThread(File f, PriorityQueue<Product> container) {
        this.f = f;
        this.container = container;
    }

    @Override
    public void run() {
        try (final Scanner input = new Scanner(f)){
            while(input.hasNext()) {
                Product p = new Product(input.next(), input.next(), input.nextDouble());
                synchronized (container) {
                    container.add(p);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
