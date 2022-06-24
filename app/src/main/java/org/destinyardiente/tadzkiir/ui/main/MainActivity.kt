@file:Suppress("DEPRECATION")

package org.destinyardiente.tadzkiir.ui.main

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import org.destinyardiente.tadzkiir.R
import org.destinyardiente.tadzkiir.databinding.ActivityMainBinding
import org.destinyardiente.tadzkiir.utils.Helper.isOnlineOrNot

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityMainBinding
//    private var mSensorManager: SensorManager? = null
//    private var mSensor: Sensor? = null
    private val mSensorManager: SensorManager? by lazy {
    getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    private val mSensor: Sensor? by lazy {
        mSensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        checkIntentExtra()
        this.isOnlineOrNot()
        setupBottomNavView()
//        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        mSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)

        if(mSensor!=null){
            mSensorManager!!.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST)
        }else{
            Toast.makeText(applicationContext, "HP Anda Tidak Support Pencarian Kiblat", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkIntentExtra() {
        val isLogin = intent.getBooleanExtra("isLogin",false)
        if(isLogin){
            Toast.makeText(this, "Berhasil Login", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBottomNavView() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(com.google.android.material.R.anim.abc_fade_in)
            .setExitAnim(com.google.android.material.R.anim.abc_fade_out)
            .setPopEnterAnim(com.google.android.material.R.anim.abc_popup_enter)
            .setPopExitAnim(com.google.android.material.R.anim.abc_popup_exit)
            .setPopUpTo(navController.graph.startDestinationRoute,false)
            .build()

        with(binding.bottomNav){
            setOnItemSelectedListener{ item->
                when(item.itemId){
                    R.id.homeFragment ->{
                        navController.navigate(R.id.homeFragment,null,options)
                    }
                    R.id.quranFragment ->{
                        navController.navigate(R.id.quranFragment,null,options)
                    }
                    R.id.jadwalShalatFragment ->{
                        navController.navigate(R.id.jadwalShalatFragment,null,options)
                    }
                }
                true
            }
            setOnItemReselectedListener{ item->
                when(item.itemId){
                    R.id.homeFragment ->{
                        navController.navigate(R.id.homeFragment,null,options)
                    }
                    R.id.quranFragment ->{
                        navController.navigate(R.id.quranFragment,null,options)
                    }
                    R.id.jadwalShalatFragment ->{
                        navController.navigate(R.id.jadwalShalatFragment,null,options)
                    }
                }
            }
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }


}