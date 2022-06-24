package org.destinyardiente.tadzkiir.ui.quran.juz

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.destinyardiente.tadzkiir.core.data.source.remote.network.ApiConfig
import org.destinyardiente.tadzkiir.databinding.FragmentJuzBinding
import org.destinyardiente.tadzkiir.ui.quran.QuranFragmentDirections
import org.destinyardiente.tadzkiir.ui.quran.QuranViewModel
import org.destinyardiente.tadzkiir.ui.quran.QuranViewModelFactory
import org.destinyardiente.tadzkiir.ui.quran.adapter.JuzAdapter
import org.destinyardiente.tadzkiir.utils.Helper

class JuzFragment : Fragment() {
    private var _binding: FragmentJuzBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: QuranViewModel
    private lateinit var juzAdapter: JuzAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val api = ApiConfig.provideQuranApi
        val factory = QuranViewModelFactory(api)
        viewModel = ViewModelProvider(this, factory)[QuranViewModel::class.java]
        juzAdapter = JuzAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJuzBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupRecyclerView()
        observerIsLoading()
        checkConnectivity()
     }

    @SuppressLint("FragmentLiveDataObserve")
    private fun checkConnectivity() {
        Helper.isOnline(requireContext().applicationContext).observe(this@JuzFragment) {
            if (it == true) {
//                getDataSurah()

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
            adapter = juzAdapter
            setHasFixedSize(true)
        }
        setupOnItemClick()
    }

    private fun setupOnItemClick() {
        juzAdapter.onItemClick = {
            findNavController().navigate(QuranFragmentDirections.actionQuranFragmentToDetailQuranFragment(null,it))
        }
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun observerIsLoading() {
        viewModel.isLoading.observe(this@JuzFragment) {
            if (it == true) {
                binding.swipeRefresh.isRefreshing = true
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.swipeRefresh.isRefreshing = false
                binding.textView.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }
        viewModel._isLoading.value = false
    }

//    private fun getDataSurah() {
//        viewModel.getSurah()
//    }

//    @SuppressLint("FragmentLiveDataObserve")
//    private fun setupObservers() {
//        viewModel.surah.observe(this@JuzFragment) {
//            if (it != null) {
//               juzAdapter.updateData(it)
//            }
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}