//id is at parts[0] , we split with \\s+,
// we compare position by position: IntStream.range(0,n).filter(i->a[i].equals((b[i])).count()
// we sort with Comparator.comparingInt(X::getScore).reversed

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class QuizEvaluator {
    List<String> correctAnswers;

    public QuizEvaluator(List<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    class Student {
        String id;
        int score;


        public Student(String id, int score) {
            this.id = id;
            this.score = score;
        }

        public String getId() {
            return id;
        }

        public int getScore() {
            return score;
        }
    }

    List<Student> students = new ArrayList<>();


    public void evaluateQuizAttempts(InputStream is, OutputStream os) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.trim().split("\\s+");
                String id = parts[0];
                int score = (int) IntStream.range(0, correctAnswers.size())          // how many questions?
                    .filter(i -> parts[i+1].equals(correctAnswers.get(i)))         // student's answer i vs correct answer i
                    .count();

                students.add(new Student(id, score));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        students.sort(Comparator.comparingInt(Student::getScore).reversed());   //descending
        // sort students descending by score

        PrintWriter pw = new PrintWriter(os);
        for (Student student : students) {
            pw.printf("%s %d%n", student.getId(), student.getScore());
        }
        pw.flush();
        // print each as "id score" with a PrintWriter, then flush
    }

}


public class QuizEvaluatorTest {

    public static void wtf(Scanner in) throws FileNotFoundException {
        /*
        Read all lines from scanner and write them to a file input.txt
        * */
        PrintWriter pw = new PrintWriter(new FileOutputStream("input.txt"));
        while (in.hasNextLine()) {
            String line = in.nextLine();
            pw.println(line);
        }

        in.close();
        pw.flush();
    }

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        List<String> correct = Arrays.stream(sc.nextLine().split(";")).collect(Collectors.toList());

        wtf(sc);

        QuizEvaluator qe = new QuizEvaluator(correct);

        qe.evaluateQuizAttempts(new FileInputStream("input.txt"), System.out);
    }
}
