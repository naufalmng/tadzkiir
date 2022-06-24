package org.destinyardiente.tadzkiir

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.asLiveData
import org.destinyardiente.tadzkiir.core.data.DataStorePreferences
import org.destinyardiente.tadzkiir.core.data.loginDataStore
import org.destinyardiente.tadzkiir.ui.landing.LandingPageActivity
import org.destinyardiente.tadzkiir.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var dataStorePreferences: DataStorePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        dataStorePreferences = DataStorePreferences(loginDataStore)
        setupSplashScreen()
    }


    private fun setupSplashScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            dataStorePreferences.tokenPreferenceFlow.asLiveData()
                .observe(this){token->
                    Log.i("Token:", token.toString())
                    if(token!=null){
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("isLogin",true)
                        startActivity(intent)
                        finishAffinity()
                    }else startActivity(Intent(this, LandingPageActivity::class.java))
                }
        },500)
    }
}