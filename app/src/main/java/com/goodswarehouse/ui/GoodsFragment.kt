package com.goodswarehouse.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.goodswarehouse.MainActivity
import com.goodswarehouse.adapter.GoodAdapter
import com.goodswarehouse.databinding.FragmentGoodsBinding

class GoodsFragment : Fragment() {
  private val binding by lazy { FragmentGoodsBinding.inflate(layoutInflater) }
  private val goodAdapter by lazy { GoodAdapter() }
  private val appViewModel by lazy { (requireActivity() as MainActivity).appViewModel }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ) = binding.root

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initialize()
  }

  private fun initialize() {
    initRecyclerView()
    initListeners()
  }

  private fun initRecyclerView() {
    binding.itemsRv.apply {
      layoutManager = LinearLayoutManager(requireContext())
      adapter = goodAdapter
    }
    appViewModel.listGoods.observe(viewLifecycleOwner) { value ->
      if (value != null) {
        goodAdapter.updateList(value)
      }
    }
  }

  private fun initListeners() {
    binding.btnBack.setOnClickListener {
      findNavController().navigateUp()
    }
  }
}