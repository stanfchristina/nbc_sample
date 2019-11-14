package edu.uw.cstanf.nbcsample;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import edu.uw.cstanf.nbcsample.savedarticles.data.SavedArticle;
import edu.uw.cstanf.nbcsample.savedarticles.data.SavedArticlesDao;

/**
 * Database for persisting application data.
 *
 * <p> Currently only holds the Saved Articles table.
 */
@Database(entities = {SavedArticle.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SavedArticlesDao savedArticlesDao();

    private static final String DATABASE_NAME = "app_db";
    private static volatile AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }
}
