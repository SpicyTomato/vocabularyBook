package com.spicytomato.vocabularybook.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.spicytomato.vocabularybook.R;
import com.spicytomato.vocabularybook.database.Word;
import com.spicytomato.vocabularybook.utils.Constant;
import com.spicytomato.vocabularybook.viewmodel.MyViewModel;

public class DetailFragment extends Fragment {

    private View mView;
    private MyViewModel mMyViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.detail_layout,container,false);
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMyViewModel = ViewModelProviders.of(requireActivity()).get(MyViewModel.class);

        initView();
    }

    private void initView() {
        TextView textViewCh = mView.findViewById(R.id.chineseMeaning_d);
        TextView textViewEn = mView.findViewById(R.id.englishMeaning_d);
        TextView textViewEx = mView.findViewById(R.id.example_d);

        Word word = mMyViewModel.getShowWord();

        textViewCh.setText(word.getChineseMeaning());
        textViewEn.setText(word.getEnglishMeaning());
        textViewEx.setText(word.getExample());

        mMyViewModel.setShowWord(null);
    }
}
