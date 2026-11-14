import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class SkillsMatcher {
    Set<String> requiredSkills;


    public SkillsMatcher(List<String> requiredSkills) {
        this.requiredSkills = new HashSet<>(requiredSkills);
    }

    class Candidate {
        String id;
        int matches;

        public Candidate(String id, int matches) {
            this.id = id;
            this.matches = matches;
        }

        public String getId() {
            return id;
        }

        public int getMatches() {
            return matches;
        }
    }

    List<Candidate> candidates = new ArrayList<>();

    public void evaluateCandidates(InputStream is, OutputStream os) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                String id = parts[0];
                int matches = (int) Arrays.stream(parts)
                        .skip(1)
                        .distinct()
                        .filter(requiredSkills::contains)
                        .count();
                candidates.add(new Candidate(id, matches));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        PrintWriter pw = new PrintWriter(os);

        candidates.sort(Comparator.comparing(Candidate::getMatches).reversed());

        for (Candidate candidate : candidates) {
            pw.printf("%s %d%n", candidate.getId(), candidate.getMatches());
        }
        pw.flush();
    }


}


public class SkillsMatcherTest {

    public static void wtf(Scanner in) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream("input.txt"));
        while (in.hasNextLine()) {
            pw.println(in.nextLine());
        }
        pw.flush();
        pw.close();
        in.close();
    }

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);


        List<String> required = Arrays.stream(sc.nextLine().split(";"))
                .collect(Collectors.toList());


        wtf(sc);

        SkillsMatcher matcher = new SkillsMatcher(required);


        matcher.evaluateCandidates(new FileInputStream("input.txt"), System.out);
    }
}
