import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/**
 * This class uses a map of words to their number of occurrences
 * and translates it to XML
 */
public class OutputWriter {

    private List<WordData> wordDataList;

    public OutputWriter(List<WordData> wordDataList) {
        this.wordDataList = wordDataList;
    }

    /**
     * This function writes the xml representing the word occurrences to
     * the writer provided
     * @param writer
     * @throws IOException Thrown if there is a problem writing to the writer
     */
    public void write(BufferedWriter writer) throws IOException {

        System.out.println("Named Entities found are listed below");
        writer.write("<words>");
        for (WordData wordData : wordDataList) {
            writer.newLine();
            writer.write("\t<word occurrences='" + wordData.getOccurrences() + "'" +
                    " isNamedEntity='" +  wordData.isNamedEntity() + "'>" + wordData.getWord() +
                    "</word>");
            if (wordData.isNamedEntity()) {
                System.out.println(wordData.getWord());
            }
        }
        writer.newLine();
        writer.write("</words>");
    }
}
