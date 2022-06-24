package org.destinyardiente.tadzkiir.ui.quran.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.destinyardiente.tadzkiir.R
import org.destinyardiente.tadzkiir.core.data.source.model.*
import org.destinyardiente.tadzkiir.databinding.ItemDetailJuzBinding
import org.destinyardiente.tadzkiir.databinding.ItemDetailSurahBinding

open class QuranAdapter(val ctx: Fragment,val juz: Int): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object{
        lateinit var invokedDataSurah: ((DataQuran?) -> Unit)
        lateinit var invokedDataJuz: ((DataJuz?) -> Unit)
    }

    private var dataSurah: DataQuran? = null
    private var dataJuz: DataJuz? = null

    private var surahData: ArrayList<Verse?> = arrayListOf()
    private var juzData: ArrayList<Verse?> = arrayListOf()



    @SuppressLint("NotifyDataSetChanged")
    fun updateData(dataSurah: Quran?, dataJuz: DataJuz?) {
        this.surahData.clear()
        this.juzData.clear()

        if(dataSurah!=null) {
            Log.i("SizeDataQuran:",dataSurah.data?.verses?.size.toString())
            this.dataSurah = dataSurah.data
            dataSurah.data?.verses?.let { this.surahData.addAll(it) }
            Log.i("PropertiDataQuran:",this.dataSurah.toString())
        }
        if(dataJuz!=null){
            Log.i("DataJuz:",dataSurah.toString())
            this.dataJuz = dataJuz
            dataJuz.verses?.let { this.juzData.addAll(it) }
        }

//        Log.d("QuranAdapter: ", this.dataSurah.size.toString())
//        Log.d("QuranAdapter: ", this.dataJuz.size.toString())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val detailSurahView = SurahViewHolder.from(parent)
        val detailJuzView = JuzViewHolder.from(parent)
        return if(juz!=-1) detailJuzView else detailSurahView
    }

    class SurahViewHolder private constructor(val binding: ItemDetailSurahBinding): RecyclerView.ViewHolder(binding.root){
        companion object {
            fun from(parent: ViewGroup): SurahViewHolder{
                return SurahViewHolder(ItemDetailSurahBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
        }
        fun bind(ctx: Fragment, position: Int, surahData: ArrayList<Verse?>, dataSurah: DataQuran?){
            val i = surahData[position]
            invokedDataSurah.invoke(dataSurah)
            with(binding){
                nomorAyat.text = i?.number?.inSurah.toString()
                arab.text = i?.text?.arab.toString()
                latin.text = i?.text?.transliteration?.en.toString()
                arti.text = ctx.getString(R.string.arg_arti, i?.number?.inSurah.toString()," "+ i?.translation?.id)
            }
        }
    }

    class JuzViewHolder private constructor(val binding: ItemDetailJuzBinding): RecyclerView.ViewHolder(binding.root){
        companion object{
            fun from(parent: ViewGroup): JuzViewHolder{
                return JuzViewHolder(ItemDetailJuzBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
        }

        fun bind(ctx: Fragment, position: Int, juzData: ArrayList<Verse?>,dataJuz: DataJuz?){
            val i = dataJuz
            val itemAyah = juzData[position]
            invokedDataJuz.invoke(dataJuz)
            with(binding){
                arab.text = itemAyah?.text?.arab.toString()
                latin.text = itemAyah?.text?.transliteration?.en.toString()
                nomorAyat.text = itemAyah?.number?.inSurah.toString()
                arti.text = ctx.getString(R.string.arg_arti, itemAyah?.number?.inSurah.toString()," "+ i?.verses?.get(position)?.translation?.id)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is SurahViewHolder -> {holder.bind(ctx,position, surahData,dataSurah)}
            is JuzViewHolder ->{holder.bind(ctx,position, juzData,dataJuz)}
        }
    }

    override fun getItemCount(): Int {
        return if(juz!= -1) juzData.size else surahData.size
    }
}