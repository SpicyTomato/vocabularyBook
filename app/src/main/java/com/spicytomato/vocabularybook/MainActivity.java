package com.spicytomato.vocabularybook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spicytomato.vocabularybook.adapters.WordListAdapter;
import com.spicytomato.vocabularybook.database.Word;
import com.spicytomato.vocabularybook.fragments.DetailFragment;
import com.spicytomato.vocabularybook.fragments.WordListFragment;
import com.spicytomato.vocabularybook.viewmodel.MyViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        setContentView(R.layout.activity_main);


        judgeOrientation();

    }

    @Override
    protected void onResume() {
        super.onResume();
        judgeOrientation();
    }

    private void judgeOrientation() {
        if (getResources().getConfiguration().orientation
                == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            WordListFragment wordListFragment = new WordListFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.fragment_list,wordListFragment).commit();

            wordListFragment.setOnWordListFragmentAttachedListener(new WordListFragment.OnWordListFragmentAttachedListener() {
                @Override
                public void send() {
                    Log.d("MainAc", "send: " );
                    DetailFragment detailFragment = new DetailFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.detail_fragment,detailFragment)
                            .commit();
                }
            });

        } else if (getResources().getConfiguration().orientation
                == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {

            WordListFragment wordListFragment = new WordListFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.fragment_list,wordListFragment).commit();
        }
    }

}
