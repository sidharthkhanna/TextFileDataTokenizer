import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class tokenizes a text file and keeps a track of words encountered along with the number of occurrences
 */
public class Tokenizer {

    private static final String DELIMITER_REGEX = "\\.|,| |\\?|\\(|\\)|'|\"|!|:|;";

    private File file;
    private ProperWordsHelper properWordsHelper;

    /**
     * Creates an instance of Tokenizer
     */
    public Tokenizer(File file, ProperWordsHelper properWordsHelper) {
        this.file = file;
        this.properWordsHelper = properWordsHelper;
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
        Map<String, WordData> wordDataMap = new HashMap<>();
        do {
            line = bufferedReader.readLine();
            if (line == null) {
                break;
            }

            List<String> properWords = properWordsHelper.extractProperWords(line);
            ProperWordsAndRemainingLines properWordsAndRemainingLines =
                    buildProperWordsDataAndGetRemainingSubLines(line, properWords);

            List<String> words = new ArrayList<>();
            for (String remainingLine : properWordsAndRemainingLines.remainingLines) {
                for (String word : extractWords(remainingLine)) {
                    words.add(word);
                }
            }

            List<WordData> wordDataList = words.stream()
                    .map(word -> new WordData(word, 1, false))
                    .collect(Collectors.toList());

            addWordDataListToWordDataMap(wordDataMap, wordDataList);
            addWordDataListToWordDataMap(wordDataMap, properWordsAndRemainingLines.properWordDataList);

        } while (true);

        return new ArrayList<>(wordDataMap.values());
    }

    private String[] extractWords(String line) {
        return line.split(DELIMITER_REGEX);
    }

    /**
     * This function splits the line into sub strings based on the proper words found
     * in the line. This is done so that in the next iteration where we extract the words,
     * we don't count the proper words twice
     */
    private ProperWordsAndRemainingLines buildProperWordsDataAndGetRemainingSubLines(
            String line, List<String> properWords) {

        ProperWordsAndRemainingLines properWordsAndRemainingLines = new ProperWordsAndRemainingLines();
        properWordsAndRemainingLines.properWordDataList = new ArrayList<>();

        List<String> subLines = new ArrayList<>();
        subLines.add(line);
        for (String properWord : properWords) {
            List<String> newSubLines = new ArrayList<>();
            for (String subLine : subLines) {
                for (String newSubLine : subLine.split(Pattern.quote(properWord))) {
                    newSubLines.add(newSubLine);
                }
            }
            int occurrences = newSubLines.size() - subLines.size();
            properWordsAndRemainingLines.properWordDataList.add(
                    new WordData(properWord, occurrences, true));
            subLines = newSubLines;
        }
        properWordsAndRemainingLines.remainingLines = subLines;
        return properWordsAndRemainingLines;
    }

    private void addWordDataListToWordDataMap(Map<String, WordData> wordDataMap, List<WordData> wordDataList) {
        for (WordData wordData : wordDataList) {
            String word = wordData.getWord();
            if ("".equals(word)) {
                continue;
            }
            WordData existingWordData = wordDataMap.get(word);
            if (existingWordData == null) {
                wordDataMap.put(word, wordData);
            } else {
                existingWordData.incrementOccurrences(wordData.getOccurrences());
            }
        }
    }

    private static class ProperWordsAndRemainingLines {
        List<String> remainingLines;
        List<WordData> properWordDataList;
    }
}
