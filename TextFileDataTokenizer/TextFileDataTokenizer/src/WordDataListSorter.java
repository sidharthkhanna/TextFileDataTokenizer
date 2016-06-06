import java.util.*;

/**
 * This class sorts a list of WordData based on the sort list specified
 * If more than one sorting is provided in the list, the sort
 * that appears earlier in the list takes precedence
 */
public class WordDataListSorter {

    private final List<WordData> wordDataList;
    private Sort[] sortList;

    public WordDataListSorter(List<WordData> wordDataList, Sort... sort) {
        this.wordDataList = wordDataList;
        this.sortList = sort;
    }

    public List<WordData> sort() {
        wordDataList.sort(new Comparator<WordData>() {
            @Override
            public int compare(WordData first, WordData second) {
                return recursiveSort(first, second, 0);
            }

            /**
             * This function picks the sort in the list based on the recursionLevel
             * provided. If the two WordData objects have different values for the sort key,
             * the comparison result is returned, otherwise sorting result is passed onto the next sort
             * in the list
             *
             * @param first
             * @param second
             * @param recursionLevel
             * @return
             */
            private int recursiveSort(WordData first, WordData second, int recursionLevel) {
                if (recursionLevel == sortList.length) {
                    // This means all sort criteria have been exhausted but the two items
                    // have the same sorting value
                    return 0;
                }
                Sort sort = sortList[recursionLevel];

                Comparable firstSortValue = null;
                Comparable secondSortValue = null;
                if (sort.getSortKey().equals(Sort.SortKey.WORD)) {
                    firstSortValue = first.getWord();
                    secondSortValue = second.getWord();
                } else if (sort.getSortKey().equals(Sort.SortKey.OCCURRENCES)) {
                    firstSortValue = first.getOccurrences();
                    secondSortValue = second.getOccurrences();
                }

                int comparisonResult = firstSortValue.compareTo(secondSortValue);

                if (comparisonResult == 0) {
                    // Based on current sort value, both items should appear at
                    // the same position, pass it to the next sort criteria
                    return recursiveSort(first, second, recursionLevel + 1);
                } else if (sort.getSortOrder().equals(Sort.SortOrder.ASCENDING)) {
                    return comparisonResult;
                } else if (sort.getSortOrder().equals(Sort.SortOrder.DESCENDING)) {
                    return -1 * comparisonResult;
                }

                throw new IllegalStateException("Invalid sort order : " + sort.getSortOrder().toString());
            }
        });
        return wordDataList;
    }
}
