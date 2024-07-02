package com.example.annect

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import com.example.annect.data.AnimaData
import com.example.annect.data.AnimaDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyAppWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ){
        CoroutineScope(Dispatchers.Main).launch {
            val data = withContext(Dispatchers.IO) {
                val db = AnimaDatabase.buildAnimaDatabase(context)
                val dao = db.animaDataDao()
                dao.getFirstAnimaData()
            }
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val views = RemoteViews(context.packageName, R.layout.widget_layout)
            views.setImageViewResource(R.id.imageView1, data.body)
            views.setImageViewResource(R.id.imageView2, data.eye)
            views.setImageViewResource(R.id.imageView3, data.mouth)
            views.setImageViewResource(R.id.imageView4, data.accessory)
            appWidgetManager.updateAppWidget(appWidgetIds, views)
        }
    }
}