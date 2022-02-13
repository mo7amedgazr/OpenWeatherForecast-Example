package com.example.weatherforecast.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.common.data.preferences.SharedPrefs
import com.example.common.data.utils.DataState
import com.example.weatherforecast.domain.current.usecase.CurrentWeatherUseCase
import com.example.weatherforecast.utils.NotificationUtils
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import java.util.*
import java.util.concurrent.TimeUnit

@HiltWorker
class DailyWorker @AssistedInject constructor(
    private val currentUseCase: CurrentWeatherUseCase,
    private val sharedPrefs: SharedPrefs,
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(ctx, params) {


    companion object {
        const val TAG_OUTPUT = "DailyWorker"
    }

    override suspend fun doWork(): Result = coroutineScope {

        if (sharedPrefs.isLastKnownLocationAvailable()) {
            currentUseCase.execute(
                CurrentWeatherUseCase.Params(
                    sharedPrefs.getLastLatitude(), sharedPrefs.getLastLongitude(),
                    true
                )
            ).collect { response ->
                when (response) {
                    is DataState.GenericError -> {
                    }
                    is DataState.Success -> {
                        response.value?.let { entity ->
                            NotificationUtils.showNotification(
                                context = applicationContext,
                                entity
                            )
                        }
                    }
                }
            }
        }
        scheduleNextTask()

        Result.success()
    }

    private fun scheduleNextTask() {
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()

        // Execution around 06:00:00 AM
        dueDate.set(Calendar.HOUR_OF_DAY, 22)
        dueDate.set(Calendar.MINUTE, 0)
        dueDate.set(Calendar.SECOND, 0)

        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }

        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val dailyWorkRequest = OneTimeWorkRequest.Builder(DailyWorker::class.java)
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .addTag(TAG_OUTPUT)
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueue(dailyWorkRequest)
    }
}