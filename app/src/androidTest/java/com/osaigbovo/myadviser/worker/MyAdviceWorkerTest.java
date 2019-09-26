package com.osaigbovo.myadviser.worker;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.work.ListenableWorker;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.testing.TestListenableWorkerBuilder;

import com.osaigbovo.myadviser.worker.AdviceWorker;
import com.osaigbovo.myadviser.worker.AdviceWorkerTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class MyAdviceWorkerTest {

    private Context context;
    private WorkManager workManager;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        workManager = WorkManager.getInstance(context);
    }

    @Test
    public void testRefreshMainDataWork() throws Exception {
        // Get the ListenableWorker
        ListenableWorker worker = TestListenableWorkerBuilder.from(context, AdviceWorker.class).build();

        ListenableWorker.Result result = worker.startWork().get();

        String getAdvice = worker.getInputData().getString("refreshTime");

        assertThat(getAdvice, not(isEmptyString()));
    }

}
