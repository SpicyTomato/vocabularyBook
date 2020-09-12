package com.spicytomato.vocabularybook.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.spicytomato.vocabularybook.database.Word;
import com.spicytomato.vocabularybook.database.WordRepository;

import java.util.List;

public class MyViewModel extends AndroidViewModel {

    WordRepository mWordRepository;

    Boolean isShowDetailFragment;
    private Word showWord;

    public MyViewModel(@NonNull Application application) {
        super(application);
        this.mWordRepository = new WordRepository(application.getApplicationContext());
        this.isShowDetailFragment = false;
    }

    public void setShowWord(Word showWord) {
        this.showWord = showWord;
    }

    public Boolean getShowDetailFragment() {
        return isShowDetailFragment;
    }

    public void setShowDetailFragment(Boolean showDetailFragment) {
        isShowDetailFragment = showDetailFragment;
    }

    public Word getShowWord(){
        if (isShowDetailFragment && showWord!=null){
            return this.showWord;
        }
        return null;
    }


    public LiveData<List<Word>> getAllWords() {
        return mWordRepository.getAllWords();
    }

    public LiveData<List<Word>> getPatternWord(String pattern) {
        return mWordRepository.getPatternWord(pattern);
    }

    public void insert(Word... words) {
        mWordRepository.insert(words);
    }

    public void delete(Word... words) {
        mWordRepository.delete(words);
    }

    public void deleteAll(Word... words) {
        mWordRepository.deleteAll();
    }

    public void upGrade(Word... words) {
        mWordRepository.upgrade(words);
    }


}
