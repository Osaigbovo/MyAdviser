package com.osaigbovo.myadviser.ui;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.osaigbovo.myadviser.worker.AdviceWorker;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    Context context;
    LiveData<WorkInfo> refreshOneTimeWorkLiveData;

    @Inject
    MainViewModel(Context context) {
        this.context = context;

        //start one time task using work manager
        refreshOneTimeWorkLiveData = refreshOneTimeWork(context);
    }

    private LiveData<WorkInfo> refreshOneTimeWork(Context context) {

        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(true)
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        //Log.i("WorkManager", "starting workmanager");

        //One time work request
        OneTimeWorkRequest oneTimeWorkRequest =
                new OneTimeWorkRequest.Builder(AdviceWorker.class)
                        //.setConstraints(constraints)
                        .setBackoffCriteria(BackoffPolicy.LINEAR,
                                WorkRequest.MIN_BACKOFF_MILLIS,
                                TimeUnit.NANOSECONDS)
                        .build();

        //enqueue the work request
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);

        //listen to status and data from worker
        return WorkManager.getInstance(context).getWorkInfoByIdLiveData(oneTimeWorkRequest.getId());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

}
