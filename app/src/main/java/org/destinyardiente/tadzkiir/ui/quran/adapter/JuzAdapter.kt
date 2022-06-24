package org.destinyardiente.tadzkiir.ui.quran.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.destinyardiente.tadzkiir.R
import org.destinyardiente.tadzkiir.databinding.ItemJuzBinding

class JuzAdapter(private val ctx: Context): RecyclerView.Adapter<JuzAdapter.JuzViewHolder>() {
    private var dataJuz: ArrayList<Int> = arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30)
    lateinit var onItemClick: ((Int) -> Unit)

//    @SuppressLint("NotifyDataSetChanged")
//    fun updateData(data: List<DataSurah>) {
//        dataSurah.clear()
//        dataSurah.addAll(data)
//        notifyDataSetChanged()
//    }

    inner class JuzViewHolder(val binding: ItemJuzBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JuzViewHolder {
        return JuzViewHolder(
            ItemJuzBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: JuzViewHolder, position: Int) {
        val i = dataJuz[position]
        holder.itemView.setOnClickListener {
            onItemClick.invoke(i)
        }
        holder.binding.nomorSurah.text = i.toString()
        holder.binding.juzKe.text = ctx.getString(R.string.juzke,i.toString())
    }

    override fun getItemCount(): Int {
        return dataJuz.size
    }
}