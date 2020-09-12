package com.spicytomato.vocabularybook.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.spicytomato.vocabularybook.R;
import com.spicytomato.vocabularybook.database.Word;

public class WordListAdapter extends ListAdapter<Word, WordListAdapter.InnerHolder> {
    private OnItemClickedListener onItemClickedListener;

    public WordListAdapter() {
        super(new DiffUtil.ItemCallback<Word>() {
            @Override
            public boolean areItemsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
                if (oldItem == newItem) {
                    return true;
                }
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
                if (oldItem.getChineseMeaning().equals(newItem.getChineseMeaning())
                        && oldItem.getEnglishMeaning().equals(newItem.getEnglishMeaning())
                        && oldItem.getExample().equals(newItem.getExample())) {
                    return true;
                }

                return false;
            }
        });
    }

    @NonNull
    @Override
    public WordListAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View item = layoutInflater.inflate(R.layout.cell_recyclerview_layout, null, false);

        return new InnerHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListAdapter.InnerHolder holder, final int position) {
        String chineseMeaning = getItem(position).getChineseMeaning();
        String englishMeaning = getItem(position).getEnglishMeaning();
        String example = getItem(position).getExample();
        holder.mTextViewChinese.setText(chineseMeaning);
        holder.mTextViewEnglish.setText(englishMeaning);
        holder.mTextViewExample.setText(example);

        holder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onclick(position,getItem(position));
                }
            }
        });
    }


    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView mTextViewChinese;
        private final TextView mTextViewEnglish;
        private final TextView mTextViewExample;
        private final View mItem;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            mItem = itemView.findViewById(R.id.cell);
            mTextViewChinese = itemView.findViewById(R.id.chineseMeaning);
            mTextViewEnglish = itemView.findViewById(R.id.englishMeaning);
            mTextViewExample = itemView.findViewById(R.id.example);
        }
    }

    public interface OnItemClickedListener {
        void onclick(int position, Word item);
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
