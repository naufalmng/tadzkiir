package org.destinyardiente.tadzkiir.ui.landing

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class LandingPageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle){
    private var fragmentList: ArrayList<Fragment> = arrayListOf()

    private var fragmentTitleList: ArrayList<String> = arrayListOf()

    fun addFragment(fragment: Fragment,title: String){
        if (fragment.isAdded && fragmentList.contains(fragment)){
            return
        }
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}

//SliderFragment("https://i.ibb.co/6gdr8Xv/masjid-pogung-dalangan-GCl-YQv8-I3-So-unsplash.png",
//"Selamat Datang Di Aplikasi Tadzkiir"),
//SliderFragment("https://i.ibb.co/qY2fQBC/mhrezaa-to-JMh-Rt-At-SE-unsplash-1.png",
//"Dapat Menemani Dzikir Anda Ketika Pagi Dan Petang"),
//SliderFragment("https://i.ibb.co/X2Q9T6F/rachid-oucharia-2d1-OSHk-HXM-unsplash-1.png",
//"Ayo Mulai Sekarang !")