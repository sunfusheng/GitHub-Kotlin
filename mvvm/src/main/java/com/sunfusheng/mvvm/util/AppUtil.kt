package com.sunfusheng.mvvm.util

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.core.content.FileProvider
import androidx.lifecycle.ProcessLifecycleOwner
import com.sunfusheng.mvvm.lifecycle.AppLifecycleObserver
import com.sunfusheng.mvvm.lifecycle.DefaultActivityLifecycleCallbacks
import java.io.File
import java.io.FileNotFoundException
import java.util.*

/**
 * @author sunfusheng
 * @since  2020/10/22
 */
object AppUtil {

    private val activityStack = Stack<Activity>()
    private val appLifecycleObserver = AppLifecycleObserver()

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object : DefaultActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                super.onActivityCreated(activity, savedInstanceState)
                activityStack.push(activity)
            }

            override fun onActivityDestroyed(activity: Activity) {
                super.onActivityDestroyed(activity)
                activityStack.pop()
            }
        })
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
    }

    fun getPackageName() = ContextHolder.context.packageName

    fun getVersionCode(): Int {
        return try {
            val pi = packageManager.getPackageInfo(getPackageName(), 0)
            pi?.versionCode ?: 0
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            0
        }
    }

    fun getVersionName(): String {
        return try {
            val pi = packageManager.getPackageInfo(getPackageName(), 0)
            pi?.versionName ?: ""
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ""
        }
    }

    fun getTopActivity(): Activity = activityStack.peek()

    fun isForeground() = appLifecycleObserver.isForeground

    fun installApk(apkPath: String) {
        try {
            val apkFile = File(apkPath)
            if (!apkFile.exists()) {
                throw FileNotFoundException("Apk file does not exist!")
            }
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val apkUri: Uri
            if (Build.VERSION.SDK_INT >= 24) {
                apkUri = FileProvider.getUriForFile(
                    ContextHolder.context,
                    "${getPackageName()}.file_provider",
                    apkFile
                )
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                apkUri = Uri.fromFile(apkFile)
            }
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
            ContextHolder.context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}