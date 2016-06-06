/**
 * Class used to represent sorting for WordData
 */
public class Sort {
    public enum SortKey {
        WORD,
        OCCURRENCES
    }

    public enum SortOrder {
        ASCENDING,
        DESCENDING
    }

    private SortKey sortKey;
    private SortOrder sortOrder;

    public Sort(SortKey sortKey, SortOrder sortOrder) {
        this.sortKey = sortKey;
        this.sortOrder = sortOrder;
    }

    public SortKey getSortKey() {
        return sortKey;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }
}
