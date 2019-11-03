package edu.uw.cstanf.nbcsample.feed.data;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedGroup {
    public enum GroupType { SECTION, VIDEOS, HERO }

    private List<NewsFeedItem> groupItems = new ArrayList<>();
    private GroupType groupType;
    private String title;

    public NewsFeedGroup(GroupType groupType, String title) {
        this.groupType = groupType;
        this.title = title;
    }

    public void addItem(NewsFeedItem item) {
        groupItems.add(item);
    }

    public List<NewsFeedItem> getGroupItems() {
        return groupItems;
    }
}
