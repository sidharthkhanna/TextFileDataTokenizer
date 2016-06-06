import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Stores the proper words as present in the inut file
 * and exposes methods to identify proper words in a string
 */
public class ProperWordsHelper {

    private Set<String> properWords;

    public ProperWordsHelper(File properWordsFile) throws IOException {
        readProperWords(properWordsFile);
    }

    private void readProperWords(File properWordsFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(properWordsFile));
        String line;
        properWords = new HashSet<>();
         do {
             line = reader.readLine();
             if (line == null) {
                 break;
             }
             if ("".equals(line.trim())) {
                 continue;
             }
             properWords.add(line);
         } while (true);
        reader.close();
    }

    public List<String> extractProperWords(String line) {
        return properWords.stream()
                .filter(properWord -> line.indexOf(properWord) >= 0)
                .collect(Collectors.toList());
    }
}
