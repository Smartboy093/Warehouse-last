package com.goodswarehouse.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.goodswarehouse.MainActivity
import com.goodswarehouse.R
import com.goodswarehouse.adapter.GoodRemoveAdapter
import com.goodswarehouse.databinding.FragmentRemoveBinding
import com.goodswarehouse.model.GoodModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth

class RemoveFragment : Fragment() {
  private val binding by lazy { FragmentRemoveBinding.inflate(layoutInflater) }
  private val networkManager by lazy { (requireActivity() as MainActivity).networkManager }
  private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
  private val userEmail by lazy {
    firebaseAuth.currentUser?.email?.replace("@", "_")?.replace(".", "-") ?: "error"
  }
  private val removeAdapter by lazy {
    GoodRemoveAdapter(removeItem = { item ->
      showBottomRemove(item)
    })
  }
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
      adapter = removeAdapter
    }
    appViewModel.listGoods.observe(viewLifecycleOwner) { value ->
      if (value != null) {
        removeAdapter.updateList(value)
      }
    }
  }

  private fun initListeners() {
    binding.btnBack.setOnClickListener {
      findNavController().navigateUp()
    }
  }

  private fun showBottomRemove(item: GoodModel) {
    val bottomSheet = BottomSheetDialog(requireContext())
    val bottomView = layoutInflater.inflate(R.layout.bottom_remove, null)
    bottomSheet.apply {
      setContentView(bottomView)
      show()
    }
    bottomView.findViewById<TextView>(R.id.text_quantity).text = "Всего: ${item.quantity} шт."
    bottomView.findViewById<Button>(R.id.btn_apply).setOnClickListener {
      val quantity =  bottomView.findViewById<TextView>(R.id.input_quantity).text.toString().trim().toInt()
      networkManager.removeGood(item.id, userEmail, quantity, result = { result ->
        if (!result) {
          Toast.makeText(requireActivity(), "Произошла ошибка", Toast.LENGTH_SHORT).show()
        } else {
          bottomSheet.dismiss()
        }
      })
    }
  }
}