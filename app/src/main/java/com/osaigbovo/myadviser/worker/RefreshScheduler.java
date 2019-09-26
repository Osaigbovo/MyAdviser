package com.osaigbovo.myadviser.worker;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.concurrent.TimeUnit;

public class RefreshScheduler {

    public static void refreshPeriodicWork(Context context) {

        //define constraints
        Constraints myConstraints = new Constraints.Builder()
                //.setRequiresDeviceIdle(false)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        Data source = new Data.Builder()
                .putString("workType", "PeriodicTime")
                .build();

        PeriodicWorkRequest refreshWork =
                new PeriodicWorkRequest.Builder(AdviceWorker.class, 2, TimeUnit.SECONDS)
                        .setConstraints(myConstraints)
                        .setInputData(source)
                        .build();

        WorkManager.getInstance(context).enqueue(refreshWork);
    }


}