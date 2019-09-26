package com.osaigbovo.myadviser.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.osaigbovo.myadviser.MyAdviserApp;
import com.osaigbovo.myadviser.data.remote.RequestInterface;
import com.osaigbovo.myadviser.di.DaggerAppComponent;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Worker used to fetch advice from API.
 */
public class AdviceWorker extends Worker {

    private static final String TAG = AdviceWorker.class.getSimpleName();
    private Context context;
    private Long timeMillis = System.currentTimeMillis();

    @Inject
    RequestInterface requestInterface;

    public AdviceWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        DaggerAppComponent
                .builder()
                .application((MyAdviserApp) context.getApplicationContext())
                .build()
                .inject(this);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {

        final String advice = requestInterface.getAdvice()
                .map(advicer -> advicer.getSlip().getAdvice()).blockingGet();

        try {
            long end = timeMillis + 10000L;
            if (advice.isEmpty() && System.currentTimeMillis() <= end) {
                return Result.failure();
            } else {
                //sending data to the caller
                Data refreshTime = new Data.Builder()
                        .putString("refreshTime", " " + advice)
                        .build();

                // If there were no errors, return SUCCESS
                return Result.success(refreshTime);
            }

        } catch (Throwable throwable) {
            // If there were errors, return FAILURE
            Timber.tag(TAG).e(throwable, "Error applying blur");
            return Result.failure();
        }
    }

}
