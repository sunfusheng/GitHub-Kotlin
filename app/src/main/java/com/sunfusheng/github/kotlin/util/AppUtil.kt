package com.sunfusheng.github.kotlin.util

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import androidx.annotation.NonNull
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileNotFoundException

/**
 * @author sunfusheng
 * @since 2019-12-30
 */
object AppUtil {
    private var application: Application? = null
    private var context: Context? = null

    val sActivityService by lazy {
        getApp().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    }

    fun getApp(): Application {
        if (application != null) {
            return application as Application
        }
        throw NullPointerException("AppUtil should be initialized first!")
    }

    fun getContext(): Context {
        if (context != null) {
            return context as Context
        }
        throw NullPointerException("AppUtil should be initialized first!")
    }

    fun init(@NonNull app: Application) {
        application = app
        context = app.applicationContext
    }

    fun getPackageName(): String {
        return getApp().packageName
    }

    fun getName(): String? {
        return getName(getApp().packageName)
    }

    fun getName(packageName: String?): String? {
        return try {
            val pm = getApp().packageManager
            val pi = pm.getPackageInfo(packageName, 0)
            pi?.applicationInfo?.loadLabel(pm)?.toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    fun getIcon(): Drawable? {
        return getIcon(getApp().packageName)
    }

    fun getIcon(packageName: String?): Drawable? {
        return try {
            val pm = getApp().packageManager
            val pi = pm.getPackageInfo(packageName, 0)
            pi?.applicationInfo?.loadIcon(pm)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    fun getVersionCode(): Int {
        return getVersionCode(getPackageName())
    }

    fun getVersionCode(packageName: String?): Int {
        return try {
            val pm = getApp().packageManager
            val pi = pm.getPackageInfo(packageName, 0)
            pi?.versionCode ?: -1
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            -1
        }
    }

    fun getVersionName(): String? {
        return getVersionName(getPackageName())
    }

    fun getVersionName(packageName: String?): String? {
        return try {
            val pm = getApp().packageManager
            val pi = pm.getPackageInfo(packageName, 0)
            pi?.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    fun getPath(): String? {
        return getPath(getApp().packageName)
    }

    fun getPath(packageName: String?): String? {
        return try {
            val pm = getApp().packageManager
            val pi = pm.getPackageInfo(packageName, 0)
            pi?.applicationInfo?.sourceDir
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    fun isAppForeground(): Boolean {
        val appProcesses = sActivityService.runningAppProcesses
        if (CollectionUtil.isEmpty(appProcesses)) {
            return false
        }

        for (processInfo in appProcesses) {
            if (processInfo?.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return processInfo.processName == getApp().packageName
            }
        }
        return false
    }

    fun installApk(apkPath: String?) {
        if (TextUtils.isEmpty(apkPath)) {
            return
        }
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
                    getContext(),
                    getPackageName() + ".file_provider",
                    apkFile
                )
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                apkUri = Uri.fromFile(apkFile)
            }
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
            getContext().startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}