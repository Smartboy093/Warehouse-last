package com.goodswarehouse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goodswarehouse.model.GoodModel

class AppViewModel : ViewModel() {
  private val _listGoods: MutableLiveData<List<GoodModel>> = MutableLiveData()
  val listGoods: LiveData<List<GoodModel>> = _listGoods

  fun setListGoods(list: List<GoodModel>) {
    _listGoods.value = list
  }
}