package com.example.annect

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.RemoteViews
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.lifecycle.viewModelScope
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.annect.MainActivity
import com.example.annect.R
import com.example.annect.data.AnimaData
import com.example.annect.data.AnimaDataDao
import com.example.annect.data.AnimaDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

var loaded =false
class AppWidgetProvider : AppWidgetProvider() {


    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { appWidgetId ->
            val updateWidgetWorkRequest = OneTimeWorkRequestBuilder<UpdateWidgetWorker>()
                .setInputData(workDataOf("appWidgetId" to appWidgetId))
                .build()
            WorkManager.getInstance(context).enqueue(updateWidgetWorkRequest)
        }
    }
}

class UpdateWidgetWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val appWidgetId = inputData.getInt("appWidgetId", -1)
        if (appWidgetId != -1) {
            val data = fetchDataFromDatabase(applicationContext)
            updateAppWidget(applicationContext, appWidgetId, data)
        }
        return Result.success()
    }

    private suspend fun fetchDataFromDatabase(context: Context): AnimaData {
        return withContext(Dispatchers.IO) {
            val db = AnimaDatabase.buildAnimaDatabase(context)
            val dao = db.animaDataDao()
            dao.getFirstAnimaData()
        }

    }

    private fun updateAppWidget(context: Context, appWidgetId: Int, data: AnimaData) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val views = RemoteViews(context.packageName, R.layout.widget_layout)

        // data を使って views を更新 (例: ImageView に画像を設定)
        views.setImageViewResource(R.id.imageView1, data.body)
        views.setImageViewResource(R.id.imageView2, data.eye)
        views.setImageViewResource(R.id.imageView3, data.mouth)
        views.setImageViewResource(R.id.imageView4, data.accessory)

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}