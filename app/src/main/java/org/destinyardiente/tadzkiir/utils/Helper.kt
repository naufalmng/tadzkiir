package org.destinyardiente.tadzkiir.utils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.*
import android.os.Build
import android.text.TextUtils
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.LiveData

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("FragmentLiveDataObserve")
object Helper {


    @SuppressLint("ClickableViewAccessibility")
    fun View.enableOnClickAnimation() {
        val reducedvalue = 0.95F
        val defaultValue = 1.0f
        setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_UP -> animate().scaleX(defaultValue).scaleY(defaultValue).duration = 0
                MotionEvent.ACTION_DOWN -> animate().scaleX(reducedvalue).scaleY(reducedvalue).duration = 0
                MotionEvent.ACTION_CANCEL -> animate().scaleX(defaultValue).scaleY(defaultValue).duration = 0
            }
            false
        }
    }

    fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val c = Calendar.getInstance().time
        return formatter.format(c)
    }

    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })}

    fun Context.showConfirmDialog(title: String, message: String, actionIfAgree: () -> Unit) {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { dialog, _ ->
            actionIfAgree()
            dialog.dismiss()
        }
        alertDialog.show()
    }



    fun Activity.checkAndRequestPermission(
        title: String, message: String,
        manifestPermission: String, requestCode: Int,
        action: () -> Unit
    ) {
        val permissionStatus = ContextCompat.checkSelfPermission(applicationContext, manifestPermission)

        if (permissionStatus == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, manifestPermission)) {
                applicationContext.showConfirmDialog(title, message) {
                    requestPermission(manifestPermission, requestCode)
                }
            } else {

                requestPermission(manifestPermission, requestCode)
            }
        } else {
            action()
        }
    }

    private fun Activity.requestPermission(manifestPermission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(this, arrayOf(manifestPermission), requestCode)
    }
    fun disableTabLayoutTooltip(tl: TabLayout, vp2: ViewPager2) {
        for (i in 0 until tl.tabCount) {
            Objects.requireNonNull(tl.getTabAt(i)).let {
                if (it != null) {
                    TooltipCompat.setTooltipText(it.view,null)
                }
            }



        }
        tl.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position
                position?.let { vp2.setCurrentItem(it,true) }
                for(i in 0 until tl.tabCount){
                    Objects.requireNonNull(tl.getTabAt(i)).let {
                        if (it != null) {
                            TooltipCompat.setTooltipText(it.view,null)
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    fun AppCompatActivity.isOnlineOrNot(action: (() -> Unit?)? = null){
        isOnline(this).observe(this){
            if(it!=true){
                Toast.makeText(this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show()
            }else{
                if (action != null) {
                    action()
                }
            }
        }
    }
    fun String.isValidEmail() = !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun Fragment.isOnlineOrNot(action: (() -> Unit?)? = null){
        isOnline(requireContext()).observe(this){
            if(it!=true){
                Toast.makeText(requireContext(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show()
            }else{
                if (action != null) {
                    action()
                }
            }
        }
    }



    @SuppressLint("ObsoleteSdkInt")
    @Suppress("DEPRECATION")
    fun isOnline(context: Context) = object : LiveData<Boolean>() {
        var connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        lateinit var networkCallback: ConnectivityManager.NetworkCallback


        override fun onActive() {
            super.onActive()
            updateConnection()
            when{
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallback())
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> lollipopNetworkRequest()
                else -> {
                    context.registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
                }
            }
        }

        override fun onInactive() {
            super.onInactive()
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                connectivityManager.unregisterNetworkCallback(networkCallback)
            }else {
                context.unregisterReceiver(networkReceiver)
            }
        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun lollipopNetworkRequest(){
            val requestBuilder = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            connectivityManager.registerNetworkCallback(
                requestBuilder.build(),
                connectivityManagerCallback()
            )
        }
        fun connectivityManagerCallback(): ConnectivityManager.NetworkCallback{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                networkCallback = object: ConnectivityManager.NetworkCallback(){
                    override fun onLost(network: Network) {
                        super.onLost(network)
                        postValue(false)
                    }

                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        postValue(true)
                    }
                }
                return networkCallback
            }else throw IllegalAccessError("Error")
        }

        val networkReceiver = object: BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                updateConnection()
            }
        }

        fun updateConnection(){
            val activityNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            postValue(activityNetwork?.isConnected == true)
        }
    }

    @Suppress("DEPRECATION")
    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }












    fun replaceFragment(activity: AppCompatActivity,fm: Fragment,tag: String){
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.addToBackStack(tag)
        transaction.add(fm,tag)
        transaction.commit()
    }

    fun popBackStack(activity: AppCompatActivity,fm: Fragment){
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.detach(fm)
        transaction.commit()
    }
}