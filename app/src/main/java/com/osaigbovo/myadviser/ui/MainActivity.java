package com.osaigbovo.myadviser.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.WorkInfo;

import com.osaigbovo.myadviser.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class MainActivity extends AppCompatActivity implements HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Object> androidInjector;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textAdvice = findViewById(R.id.text_advice);
        ProgressBar progressBar = findViewById(R.id.progress_bar);

        MainViewModel mainViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MainViewModel.class);

        mainViewModel.refreshOneTimeWorkLiveData.observe(this, info -> {
            if (info.getState() == WorkInfo.State.RUNNING) {
                progressBar.setVisibility(View.VISIBLE);
            } else if (info.getState() == WorkInfo.State.SUCCEEDED) {
                String refreshTime = info.getOutputData().getString("refreshTime");
                progressBar.setVisibility(View.GONE);
                textAdvice.setVisibility(View.VISIBLE);
                textAdvice.setText(refreshTime);
            } else if (info.getState() == WorkInfo.State.FAILED) {
                progressBar.setVisibility(View.GONE);
                textAdvice.setVisibility(View.VISIBLE);
                textAdvice.setText(R.string.display_default);
            }
        });

    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

}
