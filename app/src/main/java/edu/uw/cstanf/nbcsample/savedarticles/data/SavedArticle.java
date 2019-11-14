package edu.uw.cstanf.nbcsample.savedarticles.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents an article a user has saved.
 */
@Entity(tableName = "saved_articles")
public final class SavedArticle {
    @PrimaryKey
    @ColumnInfo(name = "article_id")
    public int articleId;

    @ColumnInfo(name = "headline")
    public String headline;

    @ColumnInfo(name = "article_url")
    public String articleUrl;

    @ColumnInfo(name = "thumbnail_url")
    public String thumbnailUrl;

    public SavedArticle(int articleId, String headline, String articleUrl, String thumbnailUrl) {
        this.articleId = articleId;
        this.headline = headline;
        this.articleUrl = articleUrl;
        this.thumbnailUrl = thumbnailUrl;
    }
}
