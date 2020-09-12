package com.spicytomato.vocabularybook;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.spicytomato.vocabularybook.database.Word;
import com.spicytomato.vocabularybook.utils.Constant;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        TextView textViewCh = findViewById(R.id.chineseMeaning_d);
        TextView textViewEn = findViewById(R.id.englishMeaning_d);
        TextView textViewEx = findViewById(R.id.example_d);

        Intent intent = getIntent();

        textViewCh.setText(intent.getStringExtra(Constant.CHINESEMEANING));
        textViewEn.setText(intent.getStringExtra(Constant.ENGLISHMEANING));
        textViewEx.setText(intent.getStringExtra(Constant.EXAMPLE));


    }
}
