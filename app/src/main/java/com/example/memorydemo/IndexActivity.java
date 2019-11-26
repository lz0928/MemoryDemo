package com.example.memorydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class IndexActivity extends AppCompatActivity {

    private TextView tv_next, tv_next_2, tv_next_3, tv_next_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        tv_next = findViewById(R.id.tv_next);
        tv_next_2 = findViewById(R.id.tv_next_2);
        tv_next_3 = findViewById(R.id.tv_next_3);
        tv_next_4 = findViewById(R.id.tv_next_4);

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        tv_next_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, InnerClassLeakDemoActivity.class);
                startActivity(intent);
            }
        });
        tv_next_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, ThreadLeakActivity.class);
                startActivity(intent);
            }
        });
        tv_next_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, HandlerLeakActivity.class);
                startActivity(intent);
            }
        });
    }
}
