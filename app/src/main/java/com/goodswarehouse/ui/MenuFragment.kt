package com.goodswarehouse.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.goodswarehouse.R
import com.goodswarehouse.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
  private val binding by lazy { FragmentMenuBinding.inflate(layoutInflater) }

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
    binding.btnGoods.setOnClickListener {
      findNavController().navigate(R.id.goodsFragment)
    }
    binding.btnAdd.setOnClickListener {
      findNavController().navigate(R.id.addFragment)
    }
    binding.btnRemove.setOnClickListener {
      findNavController().navigate(R.id.removeFragment)
    }
    binding.btnInfo.setOnClickListener {
      findNavController().navigate(R.id.infoFragment)
    }
    binding.btnExit.setOnClickListener {
      requireActivity().finish()
    }
  }
}