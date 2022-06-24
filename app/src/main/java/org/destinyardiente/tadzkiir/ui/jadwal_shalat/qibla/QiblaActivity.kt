@file:Suppress("DEPRECATION")

package org.destinyardiente.tadzkiir.ui.jadwal_shalat.qibla

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.destinyardiente.tadzkiir.databinding.ActivityQiblaBinding
import kotlin.math.roundToInt


class QiblaActivity : AppCompatActivity(),SensorEventListener {

    private lateinit var binding: ActivityQiblaBinding
    private val mSensorManager: SensorManager? by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    private val mSensor: Sensor? by lazy {
        mSensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)
    }
    private var currentDegree: Float? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQiblaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if(mSensor!=null){
            mSensorManager!!.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST)
        }else{
            Toast.makeText(applicationContext, "HP Anda Tidak Support", Toast.LENGTH_SHORT).show()
        }
    }

    fun restartActivity(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate()
        } else {
            activity.finish()
            activity.startActivity(activity.intent)
        }
    }


//    override fun onResume() {
//        super.onResume()
//        mSensorManager!!.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        mSensorManager!!.unregisterListener(this)
//    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent) {
        val degree = event.values[0].roundToInt()
        val animation = currentDegree?.let { RotateAnimation(it,
            (-degree).toFloat(),Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f) }
        animation?.duration = 500
        animation?.fillAfter = true
        binding.qiblaCompass.animation = animation
        currentDegree = -degree.toFloat()
    }

}