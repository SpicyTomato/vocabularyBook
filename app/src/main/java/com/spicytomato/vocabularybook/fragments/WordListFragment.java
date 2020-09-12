package com.spicytomato.vocabularybook.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spicytomato.vocabularybook.DetailActivity;
import com.spicytomato.vocabularybook.MainActivity;
import com.spicytomato.vocabularybook.R;
import com.spicytomato.vocabularybook.adapters.WordListAdapter;
import com.spicytomato.vocabularybook.database.Word;
import com.spicytomato.vocabularybook.utils.Constant;
import com.spicytomato.vocabularybook.viewmodel.MyViewModel;

import java.util.List;

public class WordListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private MyViewModel mMyViewModel;
    private LiveData<List<Word>> mWordList;
    private WordListAdapter mWordListAdapter;
    private View mView;
    private OnWordListFragmentAttachedListener onWordListFragmentAttachedListener;
    private boolean mIsLandSpace;
    private FloatingActionButton mFloatingActionButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.wordlist_fragment, container, false);

        return mView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMyViewModel = ViewModelProviders.of(requireActivity()).get(MyViewModel.class);

        initView();
        getData();

        moveToDetail();
    }

    private void moveToDetail() {
        mWordListAdapter.setOnItemClickedListener(new WordListAdapter.OnItemClickedListener() {
            @Override
            public void onclick(int position, Word item) {
                if (requireActivity().getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    Log.d("TAG", "onclick: " + getResources().getConfiguration().orientation);
                    Intent intent = new Intent(requireContext(), DetailActivity.class);
                    intent.putExtra(Constant.CHINESEMEANING, item.getChineseMeaning());
                    intent.putExtra(Constant.ENGLISHMEANING, item.getEnglishMeaning());
                    intent.putExtra(Constant.EXAMPLE, item.getExample());
                    startActivity(intent);
                } else if (requireActivity().getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    Log.d("MainAc", "onclick: ");
                    mMyViewModel.setShowDetailFragment(true);
                    mMyViewModel.setShowWord(item);
                    onWordListFragmentAttachedListener.send();
                }
            }
        });
    }


    private void getData() {

        mMyViewModel.insert(new Word("w", "english", "i love shinuomiyakaguya"));

        mWordList = mMyViewModel.getAllWords();
        mWordList.observe(getViewLifecycleOwner(), new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                if (words.size() != mWordListAdapter.getItemCount()) {

                }
                Log.d("WordListFragment", words.toString());
                mWordListAdapter.submitList(words);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                Word word = mMyViewModel.getAllWords().getValue().get(position);

                mMyViewModel.delete(word);

            }
        }).attachToRecyclerView(mRecyclerView);


    }

    private void initView() {

        mRecyclerView = mView.findViewById(R.id.wordList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mWordListAdapter = new WordListAdapter();
        mRecyclerView.setAdapter(mWordListAdapter);

        FloatingActionButton floatingActionButton = mView.findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                View view = LayoutInflater.from(requireContext()).inflate(R.layout.insert_layout, null, false);
                final EditText editText_ch = view.findViewById(R.id.chineseMeaning_e);
                final EditText editText_en = view.findViewById(R.id.englishMeaning_e);
                final EditText editText_ex = view.findViewById(R.id.example_e);
                final String ch = editText_ch.getText().toString();
                final String en = editText_en.getText().toString();
                final String ex = editText_ex.getText().toString();
                builder
                        .setView(view)
                        .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (ch.length() != 0 &&
                                        en.length() != 0 &&
                                        ex.length() != 0) {
                                    mMyViewModel.insert(new Word(ch, en, ex));
                                } else {
                                    Toast.makeText(requireContext(), "输入不能为空", Toast.LENGTH_LONG).show();

                                }

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
            }
        });


    }

    public interface OnWordListFragmentAttachedListener {
        void send();
    }

    public void setOnWordListFragmentAttachedListener(OnWordListFragmentAttachedListener onWordListFragmentAttachedListener) {
        this.onWordListFragmentAttachedListener = onWordListFragmentAttachedListener;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        requireActivity().getMenuInflater().inflate(R.menu.menu,menu);
    }


    //TODO
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        View view = LayoutInflater.from(requireContext()).inflate(R.layout.);
//        final String pattern = view.findViewById().getText().toString();
//        if (item.getItemId() == ){
//            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
//            builder
//                    .setView()
//                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            mMyViewModel.getPatternWord(pattern);
//                        }
//                    }).create().show();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
