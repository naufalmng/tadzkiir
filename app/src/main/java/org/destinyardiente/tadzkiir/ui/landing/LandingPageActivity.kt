package org.destinyardiente.tadzkiir.ui.landing

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.viewpager2.widget.ViewPager2
import org.destinyardiente.tadzkiir.R
import org.destinyardiente.tadzkiir.core.data.DataStorePreferences
import org.destinyardiente.tadzkiir.databinding.ActivityLandingPageBinding
import org.destinyardiente.tadzkiir.ui.auth.AuthActivity

import java.lang.Boolean

class LandingPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingPageBinding
    private lateinit var dataStorePreferences: DataStorePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViewPager2()
        setupListeners()
        val sharedpreferences =
            getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        if (!sharedpreferences.getBoolean("isLandingPageActivityOpened",false)) {
            val editor = sharedpreferences.edit()
            editor.putBoolean("isLandingPageActivityOpened", Boolean.TRUE)
            editor.apply()
        } else {
            startActivity(Intent(this,AuthActivity::class.java))
            finishAffinity()
        }
    }



    private fun setupListeners() {
        binding.nextButton.setOnClickListener {
            when(binding.viewPager2.currentItem){
                0 -> {binding.viewPager2.currentItem = 1}
                1 -> {binding.viewPager2.currentItem = 2}
                2 -> {startActivity(Intent(this, AuthActivity::class.java))
                }
            }
        }
    }

    fun action(v: View){
        when(v.id){
             R.id.line1 -> {
                 binding.viewPager2.currentItem = 0
             }
            R.id.line2 -> {
                binding.viewPager2.currentItem = 1
             }
            R.id.line3 -> {
                binding.viewPager2.currentItem = 2
             }
        }
    }

    private fun getDrawable(name: String): Int{
        return this.resources.getIdentifier(name,"drawable",this.packageName)
    }

    private fun initViewPager2() {
        val landingPageAdapter = LandingPageAdapter(supportFragmentManager,lifecycle)
        landingPageAdapter.also{
            it.addFragment(SliderFragment(getDrawable("landing1"),
                "Selamat Datang Di Aplikasi Tadzkiir"),"Fragment 1")
            it.addFragment(SliderFragment(getDrawable("landing2"),
                "Dapat Menemani Dzikir Anda Ketika Pagi Dan Petang"),"Fragment 2")
            it.addFragment(SliderFragment(getDrawable("landing3"),
                "Ayo Mulai Sekarang !"),"Fragment 3")

        }


        binding.viewPager2.apply {
            adapter = landingPageAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> {
                            binding.line1.setImageDrawable(AppCompatResources.getDrawable(this@LandingPageActivity,R.drawable.ic_line_selected))
                            binding.line2.setImageDrawable(AppCompatResources.getDrawable(this@LandingPageActivity,R.drawable.ic_line))
                            binding.line3.setImageDrawable(AppCompatResources.getDrawable(this@LandingPageActivity,R.drawable.ic_line))
                        }
                        1 -> {
                            binding.line1.setImageDrawable(AppCompatResources.getDrawable(this@LandingPageActivity,R.drawable.ic_line))
                            binding.line2.setImageDrawable(AppCompatResources.getDrawable(this@LandingPageActivity,R.drawable.ic_line_selected))
                            binding.line3.setImageDrawable(AppCompatResources.getDrawable(this@LandingPageActivity,R.drawable.ic_line))
                        }
                        2 -> {
                            binding.line1.setImageDrawable(AppCompatResources.getDrawable(this@LandingPageActivity,R.drawable.ic_line))
                            binding.line2.setImageDrawable(AppCompatResources.getDrawable(this@LandingPageActivity,R.drawable.ic_line))
                            binding.line3.setImageDrawable(AppCompatResources.getDrawable(this@LandingPageActivity,R.drawable.ic_line_selected))
                        }
                    }
                }
            })
        }
    }
}