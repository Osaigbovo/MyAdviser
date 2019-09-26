package com.osaigbovo.myadviser.ui;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.osaigbovo.myadviser.R;
import com.osaigbovo.myadviser.worker.AdviceWorkerTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("unchecked")
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule activityTestRule =
            new ActivityTestRule(MainActivity.class, true, true);
    private Context appContext;

    @Before
    public void start() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        assertEquals("com.osaigbovo.myadviser", appContext.getPackageName());
    }

    @Test
    public void checkProgressBar() throws Exception {

        Data input = new Data.Builder()
                .putString("getAdvice", "Advice")
                .build();

        // Create request
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(AdviceWorkerTest.class)
                .setInputData(input)
                .build();

        WorkManager workManager = WorkManager.getInstance(appContext);
        // Enqueue and wait for result.
        workManager.enqueue(request).getResult().get();
        // Get WorkInfo and outputData
        WorkInfo workInfo = workManager.getWorkInfoById(request.getId()).get();

        // Assert
        assertTrue(workInfo.getState().equals(WorkInfo.State.ENQUEUED)
                || workInfo.getState().equals(WorkInfo.State.RUNNING));

        onView(withId(R.id.progress_bar))
                .check(matches(isDisplayed()));
    }


    @Test
    public void checkdefaultText() throws Exception {
        Data input = new Data.Builder()
                .putString("getAdvice", "")
                .build();

        // Create request
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(AdviceWorkerTest.class)
                .setInputData(input)
                .build();

        WorkManager workManager = WorkManager.getInstance(appContext);
        // Enqueue and wait for result.
        workManager.enqueue(request).getResult().get();
        // Get WorkInfo and outputData
        WorkInfo workInfo = workManager.getWorkInfoById(request.getId()).get();

        // Assert
        assertThat(workInfo.getState(), is(WorkInfo.State.ENQUEUED));

        onView(withId(R.id.text_advice))
                .check(matches(isDisplayed()));
    }


}
