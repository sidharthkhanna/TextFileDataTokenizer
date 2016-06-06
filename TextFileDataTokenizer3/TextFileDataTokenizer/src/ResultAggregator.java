import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Aggregates the results from all the threads
 */
public class ResultAggregator {

    private Map<String, WordData> wordDataMap = new HashMap<>();

    public synchronized void addWordDataList(List<WordData> wordDataList) {
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

    public List<WordData> getMergedWordDataList() {
        return new ArrayList<>(wordDataMap.values());
    }
}
