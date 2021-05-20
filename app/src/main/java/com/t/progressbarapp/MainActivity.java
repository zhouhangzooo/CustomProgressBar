package com.t.progressbarapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.t.progressbarapp.view.RoundProgressBar;
import com.t.progressbarapp.view.TestProgressBar;

public class MainActivity extends AppCompatActivity {
    private Button btnStart;
    private Button btnStop;
    private TestProgressBar progressBar;
    private RoundProgressBar roundProgressBar;

    private int mCurrentProgress;
    private boolean isStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setListener();
    }

    private void setListener() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStart) {
                    return;
                }
                isStart = true;
                mCurrentProgress = 0;
                new Thread(progressRunnable).start();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStart = false;
            }
        });
    }

    private Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {
            while (isStart) {
                if (mCurrentProgress <= roundProgressBar.getMax()) {
                    mCurrentProgress += 1;
                    roundProgressBar.setProgress(mCurrentProgress);
                    progressBar.setProgress(mCurrentProgress);
                }else {
                    isStart = false;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void init() {
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
        progressBar = findViewById(R.id.progress_bar);
        roundProgressBar = findViewById(R.id.round_progress_bar);
        progressBar.setMax(500);
        roundProgressBar.setMax(500);
    }
}
