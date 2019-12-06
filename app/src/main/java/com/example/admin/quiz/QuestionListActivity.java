package com.example.admin.quiz;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class QuestionListActivity extends AppCompatActivity implements ItemClickListener{
    private int[] layoutIds = {R.id.layoutpwd, R.id.layoutcreate};
    private ConstraintLayout[] layouts;
    private Button question;
    private ImageView create;
    private EditText pwd;
    private ArrayList<QuestionBean> data;
    private QuestionDBHelper dbHelper;
    private QuestionAdapter adapter;
    private RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        question = findViewById(R.id.button3);
        create = findViewById(R.id.imageView5);
        pwd = findViewById(R.id.editText);

        layouts = new ConstraintLayout[layoutIds.length];
        for(int i = 0; i < layouts.length; i++){
            layouts[i] = findViewById(layoutIds[i]);
        }

        showLayout(R.id.layoutpwd);

        question.setOnClickListener(v->{
            if(pwd.getText().toString().equals("1234")){
                showLayout(R.id.layoutcreate);
            }else{
                finish();
            }

        });

        create.setOnClickListener(v->{
            Intent i = new Intent(this, QuestionActivity.class);
            startActivityForResult(i, 1);

        });

        dbHelper = new QuestionDBHelper(this, "DB", null, 1);
        data = dbHelper.SELECT();
        adapter = new QuestionAdapter(data, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        listView = findViewById(R.id.questionList);
        listView.setAdapter(adapter);
        listView.setLayoutManager(manager);
    }

    private void showLayout(int id){
        for(ConstraintLayout layout: layouts){
            if(layout.getId() == id){
                layout.setVisibility(View.VISIBLE);
            }else{
                layout.setVisibility(View.GONE);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            this.data = dbHelper.SELECT();
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onItemClick(View v, int index) {
        Intent i = new Intent(this, QuestionActivity.class);
        i.putExtra("id", data.get(index).getQid());
        startActivityForResult(i, 1);
    }
}
