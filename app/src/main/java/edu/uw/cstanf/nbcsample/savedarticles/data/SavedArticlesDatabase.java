package edu.uw.cstanf.nbcsample.savedarticles.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Database for storing articles a user has saved.
 */
@Database(entities = {SavedArticle.class}, version = 1)
public abstract class SavedArticlesDatabase extends RoomDatabase {
    public abstract SavedArticlesDao savedArticlesDao();

    private static final String DATABASE_NAME = "saved_articles_db";
    private static volatile SavedArticlesDatabase instance;

    public static SavedArticlesDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (SavedArticlesDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), SavedArticlesDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
