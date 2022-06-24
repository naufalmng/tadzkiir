package org.destinyardiente.tadzkiir.ui.home.dzikir

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import org.destinyardiente.tadzkiir.core.data.source.remote.network.ApiConfig
import org.destinyardiente.tadzkiir.databinding.ActivityDzikirBinding
import org.destinyardiente.tadzkiir.ui.home.HomeViewModel
import org.destinyardiente.tadzkiir.ui.home.HomeViewModelFactory
import org.destinyardiente.tadzkiir.ui.home.dzikir.adapter.DzikirAdapter
import org.destinyardiente.tadzkiir.utils.Helper.isOnline
import org.destinyardiente.tadzkiir.utils.Helper.isOnlineOrNot

class DzikirActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDzikirBinding
    private val dzikirAdapter : DzikirAdapter by lazy {
        DzikirAdapter()
    }
    private lateinit var namaDzikir: String
    private val viewModel: HomeViewModel by lazy {
        val api = ApiConfig.provideDzikrApi
        val factory = HomeViewModelFactory(api)
        ViewModelProvider(this,factory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDzikirBinding.inflate(layoutInflater)
        val view = binding.root
        this.isOnlineOrNot()
        setContentView(view)
        binding.backBtn.setOnClickListener{
            finish()
        }
        isOnline(this).observe(this){
            if(it == true){
                setupObserver()
                getIntentExtra()
                setupRecyclerView()
                setupRecyclerViewListeners()
                setupOnCounterClick()
            }else return@observe
        }
      }

    private fun setupObserver() {
        viewModel.isLoading.observe(this){
            if(it!=true){
                binding.shimmer.stopShimmer()
                binding.shimmer.visibility = View.GONE
            }
        }
    }


    private fun setupRecyclerViewListeners() {
        binding.recyclerView.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                binding.recyclerView.visibility = View.VISIBLE
                binding.recyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun getIntentExtra() {
        namaDzikir = intent.getStringExtra("namaDzikir").toString()
        binding.tvJudul.text = namaDzikir
        when(namaDzikir){
            "Dzikir Petang Kubra" ->{
                viewModel.getDzikirPetangKubro()
            }
            "Dzikir Petang Sughra" ->{
                viewModel.getDzikirPetangSugro()
            }
            "Dzikir Pagi Kubra" ->{
                viewModel.getDzikirPagiKubro()
            }
            "Dzikir Pagi Sughra" ->{
                viewModel.getDzikirPagiSugro()
            }
        }
    }

    private fun setupOnCounterClick() {
        binding.btnCounter.setOnClickListener{
            dzikirAdapter.resetCounter()
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = dzikirAdapter
        }
        setupRecyclerViewData()
    }

    private fun setupRecyclerViewData() {
        viewModel.dataDzikir.observe(this){
            if(it!=null){
                dzikirAdapter.setData(it)
                binding.tvJudul.visibility = View.VISIBLE
            }
        }
    }
}