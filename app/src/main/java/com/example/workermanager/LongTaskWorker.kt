package com.example.workermanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class LongTaskWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return createForegroundInfo(0)
    }

    private fun createForegroundInfo(progress: Int): ForegroundInfo {
        return ForegroundInfo(NOTIFICATION_ID, createNotification(progress))
    }

    private fun createNotification(progress: Int): Notification {
        val channelId = "Worker 체널 ID"
        val title = "Worker 제목"
        val cancel = "종료"
        val name = "Worker 이름"
        val intent = WorkManager.getInstance(applicationContext).createCancelPendingIntent(id)

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setTicker(title)
            .setProgress(100, progress, false)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setOngoing(true)
            .addAction(NotificationCompat.Action(R.drawable.ic_cancel, cancel, intent))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_LOW)
            notificationManager.createNotificationChannel(channel)
            builder.setChannelId(channel.id)
        }
        return builder.build()
    }

    override suspend fun doWork(): Result {
        var count = 1
        val total = 3600
        while (count <= total && !isStopped) {
            delay(100)
            count += 1
            Log.d("Worker", "$count foreground : $isRunInForeground")
            val progress = count * 100 / total
            setForeground(createForegroundInfo(progress))
        }
        return Result.success()
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}
