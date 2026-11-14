import java.io.*;
import java.util.*;

class OrderAnalyzer {
    double minTotal;

    public OrderAnalyzer(double minTotal) {
        this.minTotal = minTotal;
    }

    class Customer{
        String id;
        int count;
        double total;
        double average;

        public Customer(String id, int count, double total, double average) {
            this.id = id;
            this.count = count;
            this.total = total;
            this.average = average;
        }

        public String getId() {
            return id;
        }

        public double getTotal() {
            return total;
        }
    }

    List<Customer> customers =  new ArrayList<>();

    void analyze(InputStream is, OutputStream os) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));



        try {
            String line;
            while ((line = br.readLine())!=null){
                String[] parts = line.trim().split("\\s+");
                String id = parts[0];
                DoubleSummaryStatistics items = Arrays.stream(parts)
                        .skip(1)
                        .mapToDouble(Double::parseDouble)
                        .summaryStatistics();
                double total = items.getSum();
                int count = (int)items.getCount();
                double average = items.getAverage();
                if(total >= minTotal){
                    customers.add(new Customer(id, count, total, average));
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        PrintWriter pw = new PrintWriter(os);
        customers.sort(Comparator.comparingDouble(Customer::getTotal).reversed().thenComparing(Customer::getId));
        for(Customer c: customers) pw.printf("%s %d %.2f %.2f%n", c.getId(), c.count, c.total, c.average);
        pw.flush();
    }
}


public class OrderAnalyzerTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double minTotal = Double.parseDouble(sc.nextLine());
        StringBuilder sb = new StringBuilder();
        while (sc.hasNextLine()) sb.append(sc.nextLine()).append("\n");
        OrderAnalyzer oa = new OrderAnalyzer(minTotal);
        oa.analyze(new ByteArrayInputStream(sb.toString().getBytes()), System.out);
    }
}