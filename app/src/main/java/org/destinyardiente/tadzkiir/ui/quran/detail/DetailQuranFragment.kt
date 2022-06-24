package org.destinyardiente.tadzkiir.ui.quran.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import org.destinyardiente.tadzkiir.R
import org.destinyardiente.tadzkiir.core.data.source.remote.network.ApiConfig
import org.destinyardiente.tadzkiir.databinding.FragmentDetailQuranBinding
import org.destinyardiente.tadzkiir.ui.quran.QuranViewModel
import org.destinyardiente.tadzkiir.ui.quran.QuranViewModelFactory
import org.destinyardiente.tadzkiir.ui.quran.adapter.QuranAdapter
import org.destinyardiente.tadzkiir.ui.quran.adapter.QuranAdapter.Companion.invokedDataJuz
import org.destinyardiente.tadzkiir.ui.quran.adapter.QuranAdapter.Companion.invokedDataSurah
import org.destinyardiente.tadzkiir.utils.Helper.isOnline
import kotlin.properties.Delegates

class DetailQuranFragment : Fragment() {
    private var _binding: FragmentDetailQuranBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: QuranViewModel
    private val args: DetailQuranFragmentArgs by navArgs()
    private val quranAdapter: QuranAdapter by lazy {
        QuranAdapter(this,args.juz)
    }
    private var surahId by Delegates.notNull<Int>()
    private var juzId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val api = ApiConfig.provideQuranApi
        val factory = QuranViewModelFactory(api)
        viewModel = ViewModelProvider(this, factory)[QuranViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailQuranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("InfoArgs", "Juz: "+args.juz.toString()+"\n Surah: "+args.surah?.number.toString())
        setupListeners()
        observerIsLoading()
        checkConnectivity()

    }
    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = quranAdapter
            setHasFixedSize(true)
        }
    }
    private fun showData() {
        if(args.juz!=-1){
            invokedDataJuz = {
                binding.namaSurahAtauJuzKe.text = getString(R.string.juzke,it?.juz.toString())
            }
        }else
            invokedDataSurah = {
                binding.namaSurahAtauJuzKe.text = it?.name?.transliteration?.id.toString()
            }
    }

    private fun setupListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            checkConnectivity()
            observerIsLoading()
        }
        binding.rightArrow.setOnClickListener {
            if(args.juz==-1) viewModel.getQuranSurah(surahId.plus(1))
            else viewModel.getJuz(juzId.plus(1))
        }
        binding.leftArrow.setOnClickListener {
            if(args.juz==-1) viewModel.getQuranSurah(surahId.minus(1))
            else viewModel.getJuz(juzId.minus(1))
        }
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun observerIsLoading() {
        viewModel.isLoading.observe(this@DetailQuranFragment) {
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

    @SuppressLint("FragmentLiveDataObserve")
    private fun checkConnectivity() {
        isOnline(requireContext().applicationContext).observe(this@DetailQuranFragment) {
            if (it == true) {
                getDataQuran()
                setupObservers()
                showData()
            } else {
                binding.recyclerView.visibility = View.INVISIBLE
                binding.textView.visibility = View.VISIBLE
                return@observe
            }
        }
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun setupObservers() {

        viewModel.quran.observe(this@DetailQuranFragment) {
            if (it != null && args.juz==-1) {
                surahId = it.data?.number!!
                Log.i("SurahKe:","$surahId")
                if(it.data?.number == 1) binding.leftArrow.visibility = View.INVISIBLE
                else binding.leftArrow.visibility = View.VISIBLE
                if(it.data?.number == 114) binding.rightArrow.visibility = View.INVISIBLE
                else binding.rightArrow.visibility = View.VISIBLE
                quranAdapter.updateData(it, null)
                Log.i("Surah", it.data!!.verses?.size.toString())
            }
        }

        viewModel.juz.observe(this@DetailQuranFragment){
            if(it!=null && args.juz!=-1){
                Log.i("InfoDataJuz",it.toString())
                juzId = it.juz!!
                if(it.juz == 1) binding.leftArrow.visibility = View.INVISIBLE
                else binding.leftArrow.visibility = View.VISIBLE
                if(it.juz == 30) binding.rightArrow.visibility = View.INVISIBLE
                else binding.rightArrow.visibility = View.VISIBLE
                quranAdapter.updateData(null, it)
            }
        }

    }



    private fun getDataQuran() {
        if(args.juz == -1){
            args.surah?.number?.let { viewModel.getQuranSurah(it) }
            setupRecyclerView()
        }
        else {
            viewModel.getJuz(args.juz)
            setupRecyclerView()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}