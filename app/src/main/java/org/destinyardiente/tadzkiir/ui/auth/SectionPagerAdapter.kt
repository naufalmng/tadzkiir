package org.destinyardiente.tadzkiir.ui.auth

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.destinyardiente.tadzkiir.ui.auth.login.LoginFragment
import org.destinyardiente.tadzkiir.ui.auth.register.RegisterFragment

class SectionPagerAdapter(supportFragmentManager: FragmentManager,lifecycle: Lifecycle): FragmentStateAdapter(supportFragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> LoginFragment()
            1 -> RegisterFragment()
            else -> Fragment()
        }
    }
}