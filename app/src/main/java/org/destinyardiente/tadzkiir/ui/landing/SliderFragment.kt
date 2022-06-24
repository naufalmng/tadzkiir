package org.destinyardiente.tadzkiir.ui.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.destinyardiente.tadzkiir.R
import org.destinyardiente.tadzkiir.databinding.FragmentSliderBinding


class SliderFragment(
    private val imgUrl: Int,
    private val landingText: String
): Fragment() {
    private var _binding: FragmentSliderBinding? = null
    private val binding get() = _binding!!
    private val landingViewModel: LandingViewModel by lazy {
        ViewModelProvider(this)[LandingViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSliderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSlider()
        setupObservers()

     }

    private fun setupSlider() {
        landingViewModel.setSliderData(imgUrl,binding.imageView)
    }

    private fun setupObservers() {
        landingViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it != true) {
                binding.textView.text = landingText
                binding.textView.setBackgroundResource(R.color.transparent)
            }

        }
    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}