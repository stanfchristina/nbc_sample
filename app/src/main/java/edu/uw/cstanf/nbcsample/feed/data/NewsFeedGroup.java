package edu.uw.cstanf.nbcsample.feed.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Immutable data class representing a distinct grouping of news items in a news feed.
 */
public final class NewsFeedGroup implements Comparable<NewsFeedGroup> {
    /** The possible types of items contained in a news group, declared in order of prominence. */
    public enum GroupType { PROMO, SECTION, VIDEOS }

    private final List<NewsFeedItem> groupItems;
    private final GroupType groupType;
    private final String header;

    NewsFeedGroup(List<NewsFeedItem> groupItems, GroupType groupType, String header) {
        this.groupItems = new ArrayList<>(groupItems);
        this.groupType = groupType;
        this.header = header;
    }

    /** Returns all {@link NewsFeedItem} belonging to this group. */
    public List<NewsFeedItem> getGroupItems() {
        return new ArrayList<>(groupItems);
    }

    /** Returns the header for this news group, e.g. "Politics" or "Special Reports". */
    public String getHeader() {
        return header;
    }

    public int compareTo(NewsFeedGroup other) {
        return this.groupType.compareTo(other.groupType);
    }
}
