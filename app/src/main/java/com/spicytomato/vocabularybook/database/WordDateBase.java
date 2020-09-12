package com.spicytomato.vocabularybook.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordDateBase extends RoomDatabase {

    private static WordDateBase INSTANCE;

    public static WordDateBase getInstance(Context context) {
        if (INSTANCE == null)
            synchronized (WordDateBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordDateBase.class,
                            "WordDateBase")
                            .build();
                }
            }
        return INSTANCE;
    }

    public abstract WordDao getWordDao();
}
