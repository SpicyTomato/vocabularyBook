package com.spicytomato.vocabularybook.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "chineseeaning")
    private String chineseMeaning;

    @ColumnInfo(name = "englishMeaning")
    private String englishMeaning;

    @ColumnInfo(name = "example")
    private String example;

    public Word(String chineseMeaning, String englishMeaning, String example) {
        this.chineseMeaning = chineseMeaning;
        this.englishMeaning = englishMeaning;
        this.example = example;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public int getId() {
        return id;
    }

    public String getChineseMeaning() {
        return chineseMeaning;
    }

    public String getEnglishMeaning() {
        return englishMeaning;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setChineseMeaning(String chineseMeaning) {
        this.chineseMeaning = chineseMeaning;
    }

    public void setEnglishMeaning(String englishMeaning) {
        this.englishMeaning = englishMeaning;
    }
}
