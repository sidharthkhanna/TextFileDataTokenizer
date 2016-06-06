import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Class that executes the main function
 */
public class Main {

    // Change this number to change the number of threads in the thread pool
    private static final int NUM_THREADS = 5;

    private static final ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        final File properWordsFile = new File(Main.class.getResource("NER.txt").getFile());
        ProperWordsHelper properWordsHelper = new ProperWordsHelper(properWordsFile);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.xml"));

        final ResultAggregator resultAggregator = new ResultAggregator();

        ZipFile zipFile = new ZipFile(Main.class.getResource("nlp_data.zip").getFile());
        Enumeration entries = zipFile.entries();
        List<Future<Void>> futures = new ArrayList<>();
        while(entries.hasMoreElements()) {
            InputStreamReader inputStreamReader = new InputStreamReader(
                    zipFile.getInputStream((ZipEntry) entries.nextElement()));
            final BufferedReader reader = new BufferedReader(inputStreamReader);

            futures.add(executorService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    Tokenizer tokenizer = new Tokenizer(reader, properWordsHelper);
                    List<WordData> wordDataList = tokenizer.tokenize();
                    resultAggregator.addWordDataList(wordDataList);
                    return null;
                }
            }));
        }

        executorService.shutdown();
        executorService.awaitTermination(2, TimeUnit.SECONDS);

        // Change sort to view the output xml sorted by respective fields
        // By default sorting is first by the number of occurrences of the word itself
        // followed by alphabetical order of the word itself
        WordDataListSorter wordDataListSorter = new WordDataListSorter(resultAggregator.getMergedWordDataList(),
                new Sort(Sort.SortKey.OCCURRENCES, Sort.SortOrder.DESCENDING),
                new Sort(Sort.SortKey.WORD, Sort.SortOrder.ASCENDING));
        OutputWriter outputWriter = new OutputWriter(wordDataListSorter.sort());
        outputWriter.write(bufferedWriter);

        bufferedWriter.close();
    }
}
