package com.example.admin.quiz;


import android.app.Activity;
import android.content.Intent;

import android.net.Uri;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import java.util.ArrayList;


public class QuestionActivity extends AppCompatActivity {
    private int[] layoutIds = {R.id.imageLayout, R.id.editeLayout};
    private LinearLayout[] layouts;
    private Button delete;
    private Button save;
    private ToggleButton type;
    private TextView textQuestion;
    private TextView textScore;
    private EditText editEx1, editEx2, editEx3, editEx4, editQuestion, editScore;
    private RadioButton radioEx1, radioEx2, radioEx3, radioEx4;
    private ImageView imageEx1, imageEx2, imageEx3, imageEx4;
    private LinearLayout imageLayout, editLayout;
    private QuestionBean question;
    private int qid;
    private QuestionDBHelper dbHelper;
    private final int SELECT_PHOTO=1;
    private final int SELECT_PHOTO2=2;
    private final int SELECT_PHOTO3=3;
    private final int SELECT_PHOTO4=4;
    private String imageString="", imageString2="", imageString3="", imageString4="";
    private Uri imageUriDB, imageUriDB2, imageUriDB3, imageUriDB4;




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch(requestCode){
        case SELECT_PHOTO:
            if(resultCode == RESULT_OK) {
                final Uri imageUri = data.getData();
                imageEx1.setImageURI(imageUri);
                imageString = imageUri.toString();
            }
            break;
        case SELECT_PHOTO2:
            if(resultCode == RESULT_OK) {
                final Uri imageUri2 = data.getData();
                imageEx2.setImageURI(imageUri2);
                imageString2 = imageUri2.toString();
            }
    break;
    case SELECT_PHOTO3:
        if(resultCode == RESULT_OK) {


            final Uri imageUri3 = data.getData();
            imageEx3.setImageURI(imageUri3);
            imageString3 = imageUri3.toString();


        }
        break;
    case SELECT_PHOTO4:
        if(resultCode == RESULT_OK) {


            final Uri imageUri4 = data.getData();
            imageEx4.setImageURI(imageUri4);
            imageString4 = imageUri4.toString();


        }
        break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        delete = findViewById(R.id.button4);
        save = findViewById(R.id.button5);
        type = findViewById(R.id.toggleButton);
        textQuestion = findViewById(R.id.textView3);
        textScore = findViewById(R.id.textView4);
        editQuestion = findViewById(R.id.editText2);
        editScore = findViewById(R.id.editText3);
        radioEx1 = findViewById(R.id.radioButton3);
        radioEx2 = findViewById(R.id.radioButton4);
        radioEx3 = findViewById(R.id.radioButton5);
        radioEx4 = findViewById(R.id.radioButton6);
        imageLayout = findViewById(R.id.imageLayout);
        editLayout = findViewById(R.id.editeLayout);
        editEx1 = findViewById(R.id.editText4);
        editEx2 = findViewById(R.id.editText5);
        editEx3 = findViewById(R.id.editText6);
        editEx4 = findViewById(R.id.editText7);
        imageEx1 = findViewById(R.id.imageView);
        imageEx2 = findViewById(R.id.imageView2);
        imageEx3 = findViewById(R.id.imageView3);
        imageEx4 = findViewById(R.id.imageView4);



        layouts = new LinearLayout[layoutIds.length];
        for(int i = 0; i < layouts.length; i++){
            layouts[i] = findViewById(layoutIds[i]);
        }


        dbHelper = new QuestionDBHelper(this, "DB", null, 1);
        qid = getIntent().getIntExtra("id", -1);
        if(qid>-1){
            question = dbHelper.select(qid);
            editQuestion.setText(question.getQuestion());
            editScore.setText(""+question.getScore());

            if(question.getAnswer() == 1){
                radioEx1.setChecked(true);
            }else if(question.getAnswer() == 2){
                radioEx2.setChecked(true);
            }else if(question.getAnswer() == 3){
                radioEx3.setChecked(true);
            }else if(question.getAnswer() == 4){
                radioEx4.setChecked(true);
            }

            if (question.getType().equals(QuestionBean.TYPE_TEXT)) {
                type.setChecked(true);
                showLayout(R.id.editeLayout);
                editEx1.setText(question.getEx1());
                editEx2.setText(question.getEx2());
                editEx3.setText(question.getEx3());
                editEx4.setText(question.getEx4());
            }else if(question.getType().equals(QuestionBean.TYPE_IMAGE)) {
                imageUriDB = Uri.parse(question.getEx1());
                imageEx1.setImageURI(imageUriDB);
                imageString = imageUriDB.toString();
                imageUriDB2 = Uri.parse(question.getEx2());
                imageEx2.setImageURI(imageUriDB2);
                imageString2 = imageUriDB2.toString();
                imageUriDB3 = Uri.parse(question.getEx3());
                imageEx3.setImageURI(imageUriDB3);
                imageString3 = imageUriDB3.toString();
                imageUriDB4 = Uri.parse(question.getEx4());
                imageEx4.setImageURI(imageUriDB4);
                imageString4 = imageUriDB4.toString();
            }

        }else{
            question = new QuestionBean();
        }


        delete.setOnClickListener(v->{
            if(qid == -1) finish();
            dbHelper.delete(qid);
            setResult(RESULT_OK);
            finish();
        });

        save.setOnClickListener(v->{

            if(editQuestion.getText().toString().length() == 0){
                Toast.makeText(this, "문제를 입력하세요", Toast.LENGTH_SHORT).show();
                return;
            }else if(editScore.getText().toString().length() == 0){
                Toast.makeText(this, "점수를 입력하세요", Toast.LENGTH_SHORT).show();
                return;
            }else if(radioEx1.isChecked() == false && radioEx2.isChecked() == false && radioEx3.isChecked() == false&&radioEx4.isChecked() == false){
                Toast.makeText(this, "답을 입력하세요", Toast.LENGTH_SHORT).show();
                return;
            }


            question.setQuestion(editQuestion.getText().toString().trim());
            question.setScore(Integer.parseInt(editScore.getText().toString().trim()));

            if(radioEx1.isChecked()){
                question.setAnswer(1);
            }else if(radioEx2.isChecked()){
                question.setAnswer(2);
            }else if(radioEx3.isChecked()){
                question.setAnswer(3);
            }else if(radioEx4.isChecked()){
                question.setAnswer(4);
            }

            if(type.isChecked()){

                if(editEx1.getText().toString().length()==0 || editEx2.getText().toString().length()==0||editEx3.getText().toString().length()==0||editEx4.getText().toString().length()==0){
                    Toast.makeText(this, "답을 적으세요" , Toast.LENGTH_SHORT).show();
                    return;
                }
                question.setType(QuestionBean.TYPE_TEXT);
                question.setEx1(editEx1.getText().toString().trim());
                question.setEx2(editEx2.getText().toString().trim());
                question.setEx3(editEx3.getText().toString().trim());
                question.setEx4(editEx4.getText().toString().trim());
            }else if(type.isChecked() == false){
                if(imageString.length() == 0 || imageString2.length() ==0 ||imageString3.length() == 0 ||imageString4.length()==0) {
                    Toast.makeText(this, "이미지를 넣으세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                question.setType(QuestionBean.TYPE_IMAGE);
                    question.setEx1(imageString);
                    question.setEx2(imageString2);
                    question.setEx3(imageString3);
                    question.setEx4(imageString4);
            }
            if(qid > -1){
                dbHelper.update(question);
            }else{
                dbHelper.insert(question);
            }

            setResult(RESULT_OK);
            finish();
            showUsers();

        });

        type.setOnCheckedChangeListener((compoundButton, b)->{
            if(b == true){
                showLayout(R.id.editeLayout);

            }else if (b == false){
                showLayout(R.id.imageLayout);
            }
        });

        imageEx1.setOnClickListener(v->{
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
        });
        imageEx2.setOnClickListener(v->{
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO2);
        });
        imageEx3.setOnClickListener(v->{
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO3);
        });
        imageEx4.setOnClickListener(v->{
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO4);
        });

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
    public void showUsers(){
        ArrayList<QuestionBean> users = dbHelper.SELECT();
        for(QuestionBean u: users)
            Log.i("MAIN", "[" + u.getQuestion() + "]" + "[" +u.getScore() +"]" + "["+u.getEx1()+"]");
    }
}
