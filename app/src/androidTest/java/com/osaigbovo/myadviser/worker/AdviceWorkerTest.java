package com.osaigbovo.myadviser.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class AdviceWorkerTest extends Worker {
    private Long timeMillis = System.currentTimeMillis();

    public AdviceWorkerTest(Context context, WorkerParameters parameters) {
        super(context, parameters);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data input = getInputData();

        long end = timeMillis + 10000L;

        if (System.currentTimeMillis() <= end){
            return Result.failure();
        }

        if (input.getString("advice") == null || input.getString("advice").isEmpty()) {
            return Result.failure();
        } else {
            return Result.success(input);
        }
    }
}