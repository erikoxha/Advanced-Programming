import java.io.*;
import java.util.*;


class ShapesApplication {

    class Canvas {
        String id;
        int squaresCount;
        int totalPerimeter;

        public Canvas(int squaresCount, int totalPerimeter, String id) {
            this.squaresCount = squaresCount;
            this.totalPerimeter = totalPerimeter;
            this.id = id;
        }

        public int getTotalPerimeter() {
            return totalPerimeter;
        }
    }

    private List<Canvas> canvases;

    public ShapesApplication() {
        canvases = new ArrayList<>();
    }


    int readCanvases(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        int totalSquares = 0;
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");   // split the whitespace
                String id = parts[0];   // first line as canvas id
                int squaresCount = parts.length - 1;

                int totalPerimeter = Arrays.stream(parts)
                        .skip(1)
                        .mapToInt(Integer::parseInt) // turn remaining to integers
                        .map(side -> side * 4)
                        .sum();
                canvases.add(new Canvas(squaresCount, totalPerimeter, id));
                totalSquares += squaresCount;
            }

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return totalSquares;
    }

    void printLargestCanvasTo(OutputStream os) {
        Optional<Canvas> largest = canvases.stream()
                .max(Comparator.comparingInt(Canvas::getTotalPerimeter));

        PrintWriter writer = new PrintWriter(os);
        largest.ifPresent(canvas ->
                writer.printf("%s %d %d%n", canvas.id, canvas.squaresCount, canvas.totalPerimeter));

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