package org.destinyardiente.tadzkiir.ui.home.dzikir.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.destinyardiente.tadzkiir.core.data.source.model.DaftarDzikir
import org.destinyardiente.tadzkiir.core.data.source.model.DataDaftarDzikir
import org.destinyardiente.tadzkiir.databinding.ItemDzikirBinding
@SuppressLint("NotifyDataSetChanged")
class DzikirAdapter: RecyclerView.Adapter<DzikirAdapter.ViewHolder>() {
    private var counter = 0
    private var dataList: ArrayList<DataDaftarDzikir> = arrayListOf()


    fun setData(data: ArrayList<DataDaftarDzikir>){
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    fun resetCounter(){
        counter = 0
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemDzikirBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemDzikirBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        val doaArabFilter = data.doaArab.toString().drop(1)
        val artiFilter = data.descriptionDoa.toString().drop(1)

        holder.binding.btnCounter.text = "0"
        holder.binding.btnCounter.setOnClickListener {
            if(counter==100){
                counter = 100
            } else {
                holder.binding.btnCounter.text = (counter + 1).toString()
                counter++
            }
        }

        holder.binding.tvJudul.text = data.title.toString()
        holder.binding.tvTeksArab.text = doaArabFilter.dropLast(1)
        holder.binding.tvTeksArti.text = artiFilter.dropLast(1)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
