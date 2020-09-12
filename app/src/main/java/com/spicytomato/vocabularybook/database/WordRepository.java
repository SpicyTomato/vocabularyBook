package com.spicytomato.vocabularybook.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {

    private final LiveData<List<Word>> mAllWords;
    private final WordDao mWordDao;

    public WordRepository(Context context) {
        WordDateBase wordDateBase = WordDateBase.getInstance(context);
        mWordDao = wordDateBase.getWordDao();
        mAllWords = mWordDao.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public LiveData<List<Word>> getPatternWord(String pattern) {
        return mWordDao.getPatternWord("%" + pattern + "%");
    }

    public void insert(Word... words) {
        new Insert(mWordDao).execute(words);
    }

    public void delete(Word... words) {
        new Delete(mWordDao).execute(words);
    }

    public void deleteAll() {
        new DeleteAll(mWordDao).execute();
    }

    public void upgrade(Word... words) {
        new Upgrade(mWordDao).execute(words);
    }

    static class Insert extends AsyncTask<Word, Void, Void> {

        private WordDao wordDao;

        Insert(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insert(words);
            return null;
        }
    }

    static class Delete extends AsyncTask<Word, Void, Void> {

        private WordDao wordDao;

        Delete(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.delete(words);
            return null;
        }
    }

    static class Upgrade extends AsyncTask<Word, Void, Void> {

        private WordDao wordDao;

        Upgrade(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.update(words);
            return null;
        }
    }

    static class DeleteAll extends AsyncTask<Void, Void, Void> {

        private WordDao wordDao;

        DeleteAll(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAll();
            return null;
        }
    }
}
