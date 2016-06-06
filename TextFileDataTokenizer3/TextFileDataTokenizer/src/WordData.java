/**
 * This object represents the data associated with a word
 */
public class WordData {

    private String word;
    private Integer occurrences;
    private Boolean isNamedEntity;

    public WordData(String word, int occurrences, boolean isNamedEntity) {
        this.word = word;
        this.occurrences = occurrences;
        this.isNamedEntity = isNamedEntity;
    }

    public String getWord() {
        return word;
    }

    public Integer getOccurrences() {
        return occurrences;
    }

    public Boolean isNamedEntity() {
        return isNamedEntity;
    }

    public void incrementOccurrences(int count) {
        occurrences += count;
    }
}
