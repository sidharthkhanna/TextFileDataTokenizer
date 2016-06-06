import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class tokenizes a text file and keeps a track of words encountered along with the number of occurrences
 */
public class Tokenizer {

    private static final String DELIMITER_REGEX = "\\.|,| |\\?|\\(|\\)|'|\"|!|:|;";

    private File file;
    private boolean caseSensitive;

    /**
     * Creates an instance of Tokenizer
     * @param file File to tokenize
     * @param caseSensitive Set this to true to treat same words with different cases differently
     *                      For eg. if true, "the" and "The" will be treated as two different words
     */
    public Tokenizer(File file, boolean caseSensitive) {
        this.file = file;
        this.caseSensitive = caseSensitive;
    }

    /**
     * Processes the file and returns a map of words
     * and the number of times the word occurs
     *
     * @return Map of unique words with their corresponding number of occurrences
     */
    public List<WordData> tokenize() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        Map<String, Integer> wordMapOccurrences = new HashMap<>();
        do {
            line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            String[] words = extractWords(line);
            addWordsToWordMapOccurrences(wordMapOccurrences, words);

        } while (true);

        return convertWordMapOccurrencesToWordDataList(wordMapOccurrences);
    }

    private String[] extractWords(String line) {
        return line.split(DELIMITER_REGEX);
    }

    private void addWordsToWordMapOccurrences(Map<String, Integer> wordMapOccurrences, String[] words) {
        for (String word : words) {
            if ("".equals(word)) {
                continue;
            }
            if (!caseSensitive) {
                // If we don't care about case, just lower case all words before counting
                word = word.toLowerCase();
            }
            Integer occurrences = wordMapOccurrences.get(word);
            if (occurrences == null) {
                occurrences = 0;
            }
            occurrences++;
            wordMapOccurrences.put(word, occurrences);
        }
    }

    private List<WordData> convertWordMapOccurrencesToWordDataList(Map<String, Integer> wordMapOccurrences) {
        return wordMapOccurrences.entrySet().stream()
                .map(n -> new WordData(n.getKey(), n.getValue()))
                .collect(Collectors.toList());
    }
}
