package com.example.waveoffood.adaptar

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.waveoffood.databinding.RecentBuyItemBinding

class RecentBuyAdapter(
    private var context: Context,
    private val foodNameList: ArrayList<String>,
    private val foodImageList: ArrayList<String>,
    private val foodPriceList: ArrayList<String>,
    private val foodQuantityList: ArrayList<Int>
    ): RecyclerView.Adapter<RecentBuyAdapter.RecentViewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewholder {
        val binding = RecentBuyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentViewholder(binding)
    }
    override fun getItemCount(): Int = foodNameList.size
    override fun onBindViewHolder(holder: RecentViewholder, position: Int) {
        holder.bind(position)
    }
    inner class RecentViewholder(private val binding: RecentBuyItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                foodName.text = foodNameList[position]
                foodPrice.text = foodPriceList[position]
                foodQuantity.text = foodQuantityList[position].toString()
                val uriString = foodImageList[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(foodImage)
            }
        }

    }
}