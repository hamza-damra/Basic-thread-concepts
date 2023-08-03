package com.example.basicthreadconcepts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.QuickContactBadge;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    AppCompatButton button;
    TextView textView;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn_counter);
        textView = findViewById(R.id.tv_counter);
        MyHandler handler = new MyHandler();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0 ; i < 10000 ; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Message message = new Message();
                    message.arg1 = i;
                    int finalI = i;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(String.valueOf(finalI));
                        }
                    });
                }

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.sendEmptyMessage(-1);
            }
        });




    }
    @SuppressLint("HandlerLeak")
    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message message)
        {
            super.handleMessage(message);
            if(message.what == -1)
            {
                thread.start();
            }else
            {
                textView.setText(String.valueOf(message.what));
            }
        }
    }

}