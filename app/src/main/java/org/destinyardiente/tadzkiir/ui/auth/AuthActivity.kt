package org.destinyardiente.tadzkiir.ui.auth

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import org.destinyardiente.tadzkiir.R
import org.destinyardiente.tadzkiir.databinding.ActivityAuthBinding
import org.destinyardiente.tadzkiir.utils.Helper.disableTabLayoutTooltip
import org.destinyardiente.tadzkiir.utils.Helper.isOnlineOrNot

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        disableTabLayoutTooltip(binding.tabs,binding.viewPager2)
        setupWithViewPager2()
        this.isOnlineOrNot()


//        setupObservers()
    }



//    private fun setupObservers() {
//        viewModel.registerRequest.observe(this){ response->
//            when(response){
//                is NetworkResult.Success -> {
//                    Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
//                }
//                is NetworkResult.Error -> {
//                    var msg = ""
//                    response.data?.let {
//                        msg = it.toString()
//                    }
//                    if(msg.contains('{')) msg = response.message.toString()
//                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }

    private fun setupWithViewPager2() {
        val sectionAdapter = SectionPagerAdapter(supportFragmentManager,lifecycle)
        with(binding){
            tabs.addTab(tabs.newTab().setText(getString(R.string.login)))
            tabs.addTab(tabs.newTab().setText(getString(R.string.registrasi)))
            viewPager2.apply {
                adapter = sectionAdapter
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                    override fun onPageSelected(position: Int) {
                        binding.tabs.selectTab(binding.tabs.getTabAt(position))
                    }
                })
            }
            tabs.apply {
                addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        viewPager2.currentItem = tab?.position!!
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                    }
                })
            }

        }
    }
}