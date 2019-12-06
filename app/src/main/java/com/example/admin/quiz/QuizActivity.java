package com.example.admin.quiz;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class QuizActivity extends AppCompatActivity {
    private RadioButton radioEx1, radioEx2, radioEx3, radioEx4;
    private RadioGroup radioGroup;
    private ImageView imageEx1, imageEx2, imageEx3, imageEx4;
    private TextView score ,question,textEx1, textEx2, textEx3, textEx4;
    private Button submit;
    private QuestionBean bean;
    private QuestionDBHelper dbHelper;
    private ArrayList<QuestionBean> data;
    private Iterator<QuestionBean> iterator;
    private int[] layoutIds = {R.id.imageLayout, R.id.editeLayout, R.id.textLayout};
    private LinearLayout[] layouts;
    private Uri imageUriDB, imageUriDB2, imageUriDB3, imageUriDB4;
    int radio;
    int ExScore;
    private EditText editEx;
    String text="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        radioGroup = findViewById(R.id.radioGroup3);
        radioEx1 = findViewById(R.id.radioButton7);
        radioEx2 = findViewById(R.id.radioButton8);
        radioEx3 = findViewById(R.id.radioButton9);
        radioEx4 = findViewById(R.id.radioButton10);
        imageEx1 = findViewById(R.id.imageView);
        imageEx2 = findViewById(R.id.imageView2);
        imageEx3 = findViewById(R.id.imageView3);
        imageEx4 = findViewById(R.id.imageView4);
        textEx1 = findViewById(R.id.textView8);
        textEx2 = findViewById(R.id.textView9);
        textEx3 = findViewById(R.id.textView10);
        textEx4 = findViewById(R.id.textView11);
        score = findViewById(R.id.textView5);
        question = findViewById(R.id.textView6);
        submit = findViewById(R.id.button);
        editEx = findViewById(R.id.editText4);


        layouts = new LinearLayout[layoutIds.length];
        for(int i = 0; i < layouts.length; i++){
            layouts[i] = findViewById(layoutIds[i]);
        }


        dbHelper = new QuestionDBHelper(this, "DB", null, 1);
        Intent i = getIntent();
        String mode = i.getStringExtra("level");
        String start = i.getStringExtra("start");
        data = dbHelper.SELECT();

        if(start.equals("no")){
            if(data.size() < 1){
                Toast.makeText(this,"문제를 만들어주세요", Toast.LENGTH_SHORT).show();
                finish();
                return;

            }
        }
        iterator = data.iterator();


        if(mode.equals("easy")){
            LevelEasy();

        }
        if(mode.equals("hard")){
            LevelHard();
        }

        submit.setOnClickListener(v->{
            Intent j = getIntent();
            String mode2 = j.getStringExtra("level");
            if(bean.getType().equals(QuestionBean.getTypeText())){
                if(mode2.equals("easy")){
                    if(radioEx1.isChecked() == false && radioEx2.isChecked() ==false&&radioEx3.isChecked() == false&&radioEx4.isChecked()==false){
                        Toast.makeText(this, "답을 선택하세요", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(mode2.equals("hard")){
                    if(editEx.getText().toString().length() == 0){
                        Toast.makeText(this, "답을 입력하세요", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            if(bean.getType().equals(QuestionBean.getTypeImage())){
                if(radioEx1.isChecked() == false && radioEx2.isChecked() ==false&&radioEx3.isChecked() == false&&radioEx4.isChecked()==false){
                    Toast.makeText(this, "답을 선택하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if(radioEx1.isChecked()){
                radio = 1;
            }
            if(radioEx2.isChecked()){
                radio = 2;
            }
            if(radioEx3.isChecked()){
                radio = 3;
            }
            if(radioEx4.isChecked()){
                radio = 4;
            }

            if(bean.getAnswer() == 1){
                text = bean.getEx1();
            }
            if(bean.getAnswer()==2){
                text = bean.getEx2();
            }
            if(bean.getAnswer()==3){
                text = bean.getEx3();
            }
            if(bean.getAnswer() ==4){
                text = bean.getEx4();
            }

            if(mode2.equals("easy")){


                if(bean.getAnswer() == radio) {
                    Toast.makeText(this, "정답! " + bean.getScore() + " 점 획득", Toast.LENGTH_SHORT).show();
                    ExScore += bean.getScore();


                }else {
                        Toast.makeText(this, "오답ㅜㅠ", Toast.LENGTH_SHORT).show();
                }
                LevelEasy();

            }
            if(mode2.equals("hard")){

                if(bean.getAnswer() == radio) {
                    Toast.makeText(this, "정답! " + bean.getScore() + " 점 획득", Toast.LENGTH_SHORT).show();
                    ExScore += bean.getScore();


                }else if(editEx.getText().toString().trim().equals(text.trim())){
                    Toast.makeText(this, "정답! " + bean.getScore() + " 점 획득", Toast.LENGTH_SHORT).show();
                    ExScore += bean.getScore();


                }else{
                    Toast.makeText(this, "오답ㅜㅠ", Toast.LENGTH_SHORT).show();
                }
                LevelHard();

            }
        });
    }

    public void LevelEasy(){
        if(iterator.hasNext()){
            bean = iterator.next();
        }else{
            Toast.makeText(this, "총점" + ExScore, Toast.LENGTH_SHORT).show();
            finish();
        }

        score.setText("Score " + bean.getScore());
        question.setText(bean.getQuestion());
        if(bean.getType().equals(bean.getTypeText())) {
            for(LinearLayout layout: layouts){
                layout.setVisibility(View.GONE);
            }
            showLayout(R.id.textLayout);
            radioGroup.clearCheck();
            textEx1.setText(bean.getEx1());
            textEx2.setText(bean.getEx2());
            textEx3.setText(bean.getEx3());
            textEx4.setText(bean.getEx4());
        }else if(bean.getType().equals(bean.getTypeImage())){
            showLayout(R.id.imageLayout);
            radioGroup.clearCheck();
            imageUriDB = Uri.parse(bean.getEx1());
            imageEx1.setImageURI(imageUriDB);
            imageUriDB2 = Uri.parse(bean.getEx2());
            imageEx2.setImageURI(imageUriDB2);
            imageUriDB3 = Uri.parse(bean.getEx3());
            imageEx3.setImageURI(imageUriDB3);
            imageUriDB4 = Uri.parse(bean.getEx4());
            imageEx4.setImageURI(imageUriDB4);

        }



    }

    public void LevelHard(){
        if(iterator.hasNext()){
            bean = iterator.next();
        }else{
            Toast.makeText(this, "총점" + ExScore, Toast.LENGTH_SHORT).show();
            finish();
        }

        score.setText("Score " + bean.getScore());
        question.setText(bean.getQuestion());


        if(bean.getType().equals(bean.getTypeText())) {
            for(LinearLayout layout: layouts){
                layout.setVisibility(View.GONE);
            }
            radioGroup.setVisibility(View.GONE);
            showLayout(R.id.editeLayout);
            editEx.setText("");

        }else if(bean.getType().equals(bean.getTypeImage())){
            showLayout(R.id.imageLayout);
            radioGroup.setVisibility(View.VISIBLE);
            radioGroup.clearCheck();
            imageUriDB = Uri.parse(bean.getEx1());
            imageEx1.setImageURI(imageUriDB);
            imageUriDB2 = Uri.parse(bean.getEx2());
            imageEx2.setImageURI(imageUriDB2);
            imageUriDB3 = Uri.parse(bean.getEx3());
            imageEx3.setImageURI(imageUriDB3);
            imageUriDB4 = Uri.parse(bean.getEx4());
            imageEx4.setImageURI(imageUriDB4);

        }
    }

    private void showLayout(int id){
        for(LinearLayout layout: layouts){
            if(layout.getId() == id){
                layout.setVisibility(View.VISIBLE);
            }else{
                layout.setVisibility(View.GONE);
            }
        }
    }
}
