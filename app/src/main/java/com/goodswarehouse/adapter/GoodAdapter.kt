package com.goodswarehouse.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goodswarehouse.databinding.ItemGoodBinding
import com.goodswarehouse.model.GoodModel

class GoodAdapter : RecyclerView.Adapter<GoodAdapter.ItemHolder>() {
  private var adapterList = listOf<GoodModel>()

  class ItemHolder(val binding: ItemGoodBinding) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
    return ItemHolder(ItemGoodBinding.inflate(LayoutInflater.from(parent.context), parent, false))
  }

  override fun getItemCount(): Int {
    return adapterList.size
  }

  override fun onBindViewHolder(holder: ItemHolder, position: Int) {
    adapterList[position].let { item ->
      with(holder.binding) {
        itemTitle.text = item.title
        itemDate.text = "от ${item.date}"
        itemQuantity.text = "Количество: ${item.quantity} шт."
      }
    }
  }

  @SuppressLint("NotifyDataSetChanged")
  fun updateList(list: List<GoodModel>) {
    adapterList = list
    notifyDataSetChanged()
  }
}