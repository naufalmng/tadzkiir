package org.destinyardiente.tadzkiir.ui.quran.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.destinyardiente.tadzkiir.R
import org.destinyardiente.tadzkiir.core.data.source.model.DataSurah
import org.destinyardiente.tadzkiir.databinding.ItemSurahBinding

class SurahAdapter(val ctx: Context): RecyclerView.Adapter<SurahAdapter.SurahViewHolder>() {

    private var dataSurah: ArrayList<DataSurah> = arrayListOf()
    lateinit var onItemClick: ((DataSurah) -> Unit)

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<DataSurah>) {
        dataSurah.clear()
        dataSurah.addAll(data)
        notifyDataSetChanged()
    }

    inner class SurahViewHolder(val binding: ItemSurahBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahViewHolder {
        return SurahViewHolder(ItemSurahBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holderSurah: SurahViewHolder, position: Int) {
        val i = dataSurah[position]
        holderSurah.itemView.setOnClickListener {
            onItemClick.invoke(i)
        }
        holderSurah.binding.nomorSurah.text = i.number.toString()
        holderSurah.binding.namaSurah.text = i.name?.transliteration?.id.toString()
        holderSurah.binding.jumlahAyat.text = ctx.getString(R.string.arg_ayat,i.numberOfVerses.toString())
        holderSurah.binding.turunSurah.text = i.revelation?.id.toString()
    }

    override fun getItemCount(): Int {
        return dataSurah.size
    }


}