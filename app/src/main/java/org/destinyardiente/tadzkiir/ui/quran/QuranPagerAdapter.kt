package org.destinyardiente.tadzkiir.ui.quran

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.destinyardiente.tadzkiir.ui.quran.juz.JuzFragment
import org.destinyardiente.tadzkiir.ui.quran.surah.SurahFragment

class QuranPagerAdapter(supportFragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(supportFragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SurahFragment()
            1 -> JuzFragment()
            else -> Fragment()
        }
    }
}