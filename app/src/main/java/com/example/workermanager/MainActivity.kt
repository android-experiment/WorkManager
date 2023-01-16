package com.example.workermanager

import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.example.workermanager.databinding.ActivityMainBinding
import com.example.workermanager.permission.PostNotificationPermissionManager
import com.example.workermanager.permission.core.permissions

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val permission: PostNotificationPermissionManager by permissions()

    private val postNotificationLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                enqueueWork()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding.runWorkmanager.setOnClickListener {
            if (permission.isGrant) {
                enqueueWork()
            } else {
                postNotificationLauncher.launch(permission.basicPermission)
            }
        }

        Log.d("Activity", "Create")
    }

    override fun onStart() {
        super.onStart()

        Log.d("Activity", "Start")
    }

    override fun onResume() {
        super.onResume()

        Log.d("Activity", "Resume")
    }

    override fun onPause() {
        super.onPause()

        Log.d("Activity", "Pause")
    }

    override fun onStop() {
        super.onStop()

        Log.d("Activity", "Stop")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("Activity", "Destroy")
    }

    private fun enqueueWork() {
        // 신속 실행 모드로 실행
        // Android API 12 미만에서는 getForegroundInfo()를 이용하여 Foreground Service 실행
        // 12 이상의 경우, 따로 Foreground Service가 자동실행되지 않음
        // Notification을 실행하거나 업데이트를 위해서는
        // setForeground(ForegroundInfo) 를 이용하여 전환 / 업데이트 할 수 있다.
        // 신속 실행 모드의 할당량을 넘거나 자원이 부족한경우 일반 Worker 로 등록되어 지연실행된다
        val request = OneTimeWorkRequestBuilder<LongTaskWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()

        WorkManager.getInstance(this).enqueue(request)
    }
}
