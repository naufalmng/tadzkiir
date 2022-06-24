package org.destinyardiente.tadzkiir.ui.quran

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import org.destinyardiente.tadzkiir.R
import org.destinyardiente.tadzkiir.databinding.FragmentQuranBinding
import org.destinyardiente.tadzkiir.utils.Helper
import org.destinyardiente.tadzkiir.utils.Helper.isOnlineOrNot

class QuranFragment : Fragment() {
    private var _binding: FragmentQuranBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View {
         _binding = FragmentQuranBinding.inflate(inflater, container, false)
         return binding.root
     }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
         Helper.disableTabLayoutTooltip(binding.tabs, binding.viewPager2)
         setupWithViewPager2()
      }

    private fun setupWithViewPager2() {
        val sectionAdapter = QuranPagerAdapter(childFragmentManager,lifecycle)
        with(binding){
            tabs.addTab(tabs.newTab().setText(getString(R.string.surah)))
            tabs.addTab(tabs.newTab().setText(getString(R.string.juz)))
            viewPager2.isUserInputEnabled = false
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

    override fun onDestroyView() {
         super.onDestroyView()
         _binding = null
     }

}