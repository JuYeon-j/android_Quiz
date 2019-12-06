package com.example.admin.quiz;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button start;
    private ImageView create;
    private TextView level;
    private RadioButton easy;
    private RadioButton hard;
    private final int LIST_CODE = 123;
    private static final int REQ_STORAGE =1;
    private static final int START_CODE =2;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode != REQ_STORAGE) return;
        if(permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE) && grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }else{
            Toast.makeText(this, "갤러리 권한이 없습니다",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.button2);
        create = findViewById(R.id.imageView6);
        level = findViewById(R.id.textView);
        easy = findViewById(R.id.radioButton);
        hard = findViewById(R.id.radioButton2);

        create.setOnClickListener(v->{
            if(Build.VERSION.SDK_INT >= 23){
                int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                if(permission != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_STORAGE);
                    return;
                }
            }
            Intent i = new Intent(this, QuestionListActivity.class);
            startActivityForResult(i, LIST_CODE);
        });

        start.setOnClickListener(v -> {
            if(easy.isChecked() == false && hard.isChecked() == false){
                Toast.makeText(this, "모드를 선택해 주세요", Toast.LENGTH_SHORT ).show();
            }else {
                Intent i = new Intent(this, QuizActivity.class);
                if(easy.isChecked()){
                    i.putExtra("level", "easy");
                }else if(hard.isChecked()){
                    i.putExtra("level", "hard");
                }
                i.putExtra("start","no");
                startActivityForResult(i,START_CODE );
            }
        });


        easy.setOnClickListener(v-> {
            level.setText("모든 문제가 객관식으로 출제됩니다.");
        });

        hard.setOnClickListener(v->{
            level.setText("객관식과 주관식이 출제됩니다.");
        });



    }
}
