package org.wordpress.android.ui.stats.models;

import org.wordpress.android.ui.stats.StatsConstants;
import org.wordpress.android.ui.stats.StatsUtils;

import java.io.Serializable;

public class PostModel extends SingleItemModel implements Serializable {

    private final String mPostType;

    public PostModel(long blogId, String date, String itemID, String title, int totals, String url, String postType) {
        super(blogId, date, itemID, title, totals, url, null);
        this.mPostType = postType;
    }

    public PostModel(long blogId, long date, String itemID, String title, int totals, String url) {
        super(blogId, date, itemID, title, totals, url, null);
        this.mPostType = StatsConstants.ITEM_TYPE_POST;
    }

    public PostModel(long blogId, String itemID, String title, String url, String postType) {
        super(blogId, StatsUtils.getCurrentDate(), itemID, title, 0, url, null);
        this.mPostType = postType;
    }

    public String getPostType() {
        return mPostType;
    }
}
