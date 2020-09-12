package com.spicytomato.vocabularybook.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {

    @Insert
    void insert(Word...words);

    @Delete
    void delete(Word...words);

    @Update
    void update(Word...words);

    @Query("SELECT *FROM word")
    LiveData<List<Word>> getAllWords();

    @Query("SELECT * FROM word WHERE chineseeaning LIKE :pattern")
    LiveData<List<Word>> getPatternWord(String pattern);

    @Query("DELETE FROM word")
    void deleteAll();
}
