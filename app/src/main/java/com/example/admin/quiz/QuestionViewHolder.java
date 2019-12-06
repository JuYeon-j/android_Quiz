package com.example.admin.quiz;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class QuestionViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewTitle;
    public TextView textViewTime;
    public TextView textViewType;

    public QuestionViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.textViewTitle);
        textViewTime = itemView.findViewById(R.id.textViewTime);
        textViewType = itemView.findViewById(R.id.textViewType);

    }
}
