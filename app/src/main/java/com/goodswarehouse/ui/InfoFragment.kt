package com.goodswarehouse.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.goodswarehouse.MainActivity
import com.goodswarehouse.R
import com.goodswarehouse.adapter.GoodRemoveAdapter
import com.goodswarehouse.databinding.FragmentInfoBinding
import com.goodswarehouse.databinding.FragmentRemoveBinding

class InfoFragment : Fragment() {
  private val binding by lazy { FragmentInfoBinding.inflate(layoutInflater) }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ) = binding.root

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initialize()
  }

  private fun initialize() {
    initListeners()
  }

  private fun initListeners() {
    binding.btnBack.setOnClickListener {
      findNavController().navigateUp()
    }
  }
}