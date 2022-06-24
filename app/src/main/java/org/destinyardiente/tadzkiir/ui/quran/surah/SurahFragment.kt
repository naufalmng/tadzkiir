package org.destinyardiente.tadzkiir.ui.quran.surah

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.destinyardiente.tadzkiir.core.data.source.remote.network.ApiConfig
import org.destinyardiente.tadzkiir.databinding.FragmentSurahBinding
import org.destinyardiente.tadzkiir.ui.quran.QuranFragmentDirections
import org.destinyardiente.tadzkiir.ui.quran.QuranViewModel
import org.destinyardiente.tadzkiir.ui.quran.QuranViewModelFactory
import org.destinyardiente.tadzkiir.ui.quran.adapter.SurahAdapter
import org.destinyardiente.tadzkiir.utils.Helper.isOnline

@SuppressLint("FragmentLiveDataObserve")
class SurahFragment : Fragment() {
    private var _binding: FragmentSurahBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: QuranViewModel
    private lateinit var surahAdapter: SurahAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val api = ApiConfig.provideQuranApi
        val factory = QuranViewModelFactory(api)
        viewModel = ViewModelProvider(this, factory)[QuranViewModel::class.java]
        surahAdapter = SurahAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSurahBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupRecyclerView()
        observerIsLoading()
        checkConnectivity()

    }

    private fun checkConnectivity() {
        isOnline(requireContext().applicationContext).observe(this@SurahFragment) {
            if (it == true) {
                getDataSurah()
                setupObservers()

            } else {
                binding.recyclerView.visibility = View.INVISIBLE
                binding.textView.visibility = View.VISIBLE
                return@observe
            }
        }
    }

    private fun setupListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            checkConnectivity()
            observerIsLoading()
        }
    }

    private fun setupRecyclerView() {

        binding.recyclerView.apply {
            adapter = surahAdapter
            setHasFixedSize(true)
        }
        setupOnItemClick()
    }

    private fun setupOnItemClick() {
        surahAdapter.onItemClick = {
            findNavController().navigate(QuranFragmentDirections.actionQuranFragmentToDetailQuranFragment(it,-1))
        }
    }

    private fun observerIsLoading() {
        viewModel.isLoading.observe(this@SurahFragment) {
            if (it == true) {
                binding.swipeRefresh.isRefreshing = true
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.swipeRefresh.isRefreshing = false
                binding.textView.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }
    }


    private fun setupObservers() {
        viewModel.surah.observe(this@SurahFragment) {
            if (it != null) {
                surahAdapter.updateData(it)
            }
        }
    }

    private fun getDataSurah() {
        viewModel.getSurah()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}