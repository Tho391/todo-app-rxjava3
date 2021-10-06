package com.thomas.apps.todoapprx3.utils.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.thomas.apps.todoapprx3.BuildConfig
import timber.log.Timber


object ActivityUtils {

    inline fun <reified T : Any> newIntent(context: Context): Intent =
        Intent(context, T::class.java)

    inline fun <reified T : Any> Activity.launchActivity(
        requestCode: Int = -1,
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {},
    ) {
        val intent = newIntent<T>(this)
        intent.init()
        startActivityForResult(intent, requestCode, options)
    }

    inline fun <reified T : Any> Fragment.launchActivity(
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {},
    ) {
        val intent = newIntent<T>(this.requireContext())
        intent.init()
        startActivity(intent, options)
    }

    inline fun <reified T : Any> Fragment.launchActivityForResult(
        options: Bundle? = null,
        launcher: ActivityResultLauncher<Intent>,
        noinline init: Intent.() -> Unit = {},
    ) {
        val intent = newIntent<T>(this.requireContext())
        intent.init()
        launcher.launch(intent)
    }

    inline fun <reified T : Any> Context.launchActivity(
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {},
    ) {
        val intent = newIntent<T>(this)
        intent.init()
        startActivity(intent, options)
    }

    fun Context.toast(message: String, isLengthLong: Boolean = false) =
        Toast.makeText(this, message, if (isLengthLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
            .show()

    fun Activity.toast(message: String, isLengthLong: Boolean = false) =
        Toast.makeText(this, message, if (isLengthLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
            .show()

    fun Fragment.toast(message: String, isLengthLong: Boolean = false) =
        Toast.makeText(
            requireContext(),
            message,
            if (isLengthLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        )
            .show()

    fun View.snack(text: String, length: Int = Snackbar.LENGTH_SHORT): Snackbar {
        return Snackbar.make(this, text, length)
    }

    fun Context.launchAppSetting() {
        val uri = Uri.fromParts("package", packageName, null)
        val intent = Intent().apply {
            action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = uri
        }
        startActivity(intent)
    }

    fun Activity.launchAppSettingForResult(requestCode: Int) {
        val uri = Uri.fromParts("package", packageName, null)
        val intent = Intent().apply {
            action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = uri
        }
        startActivityForResult(intent, requestCode)
    }

    fun Activity.launchBluetoothSetting() {
        val intent = Intent().apply {
            action = android.provider.Settings.ACTION_BLUETOOTH_SETTINGS
        }
        startActivity(intent)
    }

    fun Fragment.launchBluetoothSetting() {
        val intent = Intent().apply {
            action = android.provider.Settings.ACTION_BLUETOOTH_SETTINGS
        }
        startActivity(intent)
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction {
            add(
                frameId,
                fragment,
                fragment.javaClass.simpleName
            )
        }
    }

    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction {
            replace(
                frameId,
                fragment,
                fragment.javaClass.simpleName
            )
        }
    }

    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int, addToStack: Boolean) {
        supportFragmentManager.inTransaction {
            if (addToStack) replace(frameId, fragment, fragment.javaClass.simpleName)
                .addToBackStack(fragment.javaClass.simpleName)
            else
                replace(frameId, fragment, fragment.javaClass.simpleName)
        }
    }

    fun AppCompatActivity.replaceFragment(
        fragment: Fragment,
        frameId: Int,
        addToStack: Boolean,
        clearBackStack: Boolean,
    ) {
        supportFragmentManager.inTransaction {

            if (clearBackStack && supportFragmentManager.backStackEntryCount > 0) {
                val first = supportFragmentManager.getBackStackEntryAt(0)
                supportFragmentManager.popBackStack(
                    first.id,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }

            if (addToStack) replace(frameId, fragment, fragment.javaClass.simpleName)
                .addToBackStack(fragment.javaClass.simpleName)
            else
                replace(frameId, fragment, fragment.javaClass.simpleName)
        }
    }

    fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, addToStack: Boolean) {
        supportFragmentManager.inTransaction {
            if (addToStack) add(frameId, fragment, fragment.javaClass.simpleName)
                .addToBackStack(fragment.javaClass.simpleName)
            else add(frameId, fragment)
        }
    }

    fun AppCompatActivity.getCurrentFragment(): Fragment? {
        val fragmentManager = supportFragmentManager
        var fragmentTag: String? = ""

        if (fragmentManager.backStackEntryCount > 0)
            fragmentTag =
                fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1).name

        return fragmentManager.findFragmentByTag(fragmentTag)
    }

    fun Activity.hideKeyBoard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun Activity.setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController!!.hide(
                WindowInsets.Type.statusBars()
            )
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    fun Activity.disableScreenShot() {
        if (BuildConfig.DEBUG)
            Timber.w("ScreenShot is not disable")
        else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
    }
}