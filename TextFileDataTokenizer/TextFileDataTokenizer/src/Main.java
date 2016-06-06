import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Class that executes the main function
 */
public class Main {

    public static void main(String[] args) throws IOException {

        File inputFile = new File(Main.class.getResource("nlp_data.txt").getFile());
        // Set the caseSensitive flag to false if the tokenizer should treat
        // same words with different cases like "The" and "the", same
        Tokenizer tokenizer = new Tokenizer(inputFile, true);
        List<WordData> wordDataList = tokenizer.tokenize();
        // Change sort to view the output xml sorted by respective fields
        // By default sorting is first by the number of occurrences of the word itself
        // followed by alphabetical order of the word itself
        WordDataListSorter wordDataListSorter = new WordDataListSorter(wordDataList,
                new Sort(Sort.SortKey.OCCURRENCES, Sort.SortOrder.DESCENDING),
                new Sort(Sort.SortKey.WORD, Sort.SortOrder.ASCENDING));
        OutputWriter outputWriter = new OutputWriter(wordDataListSorter.sort());
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.xml"));
        outputWriter.write(bufferedWriter);
        bufferedWriter.close();
    }
}
