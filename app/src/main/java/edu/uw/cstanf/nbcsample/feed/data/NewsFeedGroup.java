package edu.uw.cstanf.nbcsample.feed.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Data class representing a distinct group of a news feed.
 */
public final class NewsFeedGroup {
    public enum GroupType {SECTION, VIDEOS, HERO}

    private List<NewsFeedItem> groupItems;
    private GroupType groupType;
    private String header;

    NewsFeedGroup(GroupType groupType, String header) {
        this.groupItems = new ArrayList<>();
        this.groupType = groupType;
        this.header = header;
    }

    void addItem(NewsFeedItem item) {
        groupItems.add(item);
    }

    public List<NewsFeedItem> getGroupItems() {
        return groupItems;
    }

    public String getHeader() {
        return header;
    }
}
