import java.io.*;
import java.util.*;

class ShapesApplication {

    public class Canvas {
        String id;
        int squaresCount;
        int totalPerimeter;

        public Canvas(String id, int squaresCount, int totalPerimeter) {
            this.id = id;
            this.squaresCount = squaresCount;
            this.totalPerimeter = totalPerimeter;
        }

        public int getTotalPerimeter() {
            return totalPerimeter;
        }
    }

    List<Canvas> canvases = new ArrayList<>();



    int readCanvases(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        int totalSquares = 0;
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                String id = parts[0];
                int squaresCount = parts.length - 1;
                int totalPerimeter = Arrays.stream(parts)
                        .skip(1)
                        .mapToInt(Integer::parseInt)
                        .map(sides -> sides * 4)
                        .sum();
                canvases.add(new Canvas(id, squaresCount, totalPerimeter));
                totalSquares += squaresCount;     //add the squaresCount to the totalSquares
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return totalSquares;
    }

    void printLargestCanvasTo(OutputStream os) {
        PrintWriter writer = new PrintWriter(os);
        Optional<Canvas> largest = canvases.stream()
                .max(Comparator.comparingInt(Canvas::getTotalPerimeter));
        largest.ifPresent(canvas -> writer.printf("%s %d %d%n", canvas.id, canvas.squaresCount, canvas.totalPerimeter));

        writer.flush();
    }
}


public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}