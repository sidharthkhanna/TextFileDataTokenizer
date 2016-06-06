/**
 * This object represents the data associated with a word
 */
public class WordData {

    private String word;
    private Integer occurrences;

    public WordData(String word, int occurrences) {
        this.word = word;
        this.occurrences = occurrences;
    }

    public String getWord() {
        return word;
    }

    public Integer getOccurrences() {
        return occurrences;
    }
}
