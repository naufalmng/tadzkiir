package org.destinyardiente.tadzkiir.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import org.destinyardiente.tadzkiir.R
import org.destinyardiente.tadzkiir.core.data.source.model.DataDaftarMenu
import org.destinyardiente.tadzkiir.databinding.ItemMenuHomeBinding
import org.destinyardiente.tadzkiir.utils.Helper.enableOnClickAnimation

@SuppressLint("NotifyDataSetChanged")
class MenuAdapter(val ctx: Context): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    var onItemClick: ((DataDaftarMenu) -> Unit)? = null
    private var daftarMenu = ArrayList<DataDaftarMenu>()


    fun setListDaftarMenu(daftarMenu: List<DataDaftarMenu>){
        this.daftarMenu = daftarMenu as ArrayList<DataDaftarMenu>
        notifyDataSetChanged()
    }


    inner class MenuViewHolder(val binding: ItemMenuHomeBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(ItemMenuHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        when(daftarMenu[position].name){
            "Dzikir Pagi Kubra" -> {
                holder.binding.tvJudul.setTextColor(AppCompatResources.getColorStateList(ctx, R.color.black))
                holder.binding.menuBg.setBackgroundResource(R.drawable.dzikir_pagi_kubro)
            }
            "Dzikir Petang Kubra" -> {holder.binding.menuBg.setBackgroundResource(R.drawable.dzikir_petang_kubro)}
            "Dzikir Pagi Sughra" -> {holder.binding.menuBg.setBackgroundResource(R.drawable.dzikir_pagi_sugro)}
        }
        holder.binding.tvJudul.text = daftarMenu[position].name
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(daftarMenu[position])
        }
    }

    override fun getItemCount(): Int {
        return daftarMenu.size
    }
}