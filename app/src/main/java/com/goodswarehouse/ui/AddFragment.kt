package com.goodswarehouse.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.goodswarehouse.MainActivity
import com.goodswarehouse.databinding.FragmentAddBinding
import com.goodswarehouse.model.GoodModel
import com.google.firebase.auth.FirebaseAuth

class AddFragment : Fragment() {
  private val binding by lazy { FragmentAddBinding.inflate(layoutInflater) }
  private val networkManager by lazy { (requireActivity() as MainActivity).networkManager }
  private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
  private val userEmail by lazy {
    firebaseAuth.currentUser?.email?.replace("@", "_")?.replace(".", "-") ?: "error"
  }

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
    binding.btnAdd.setOnClickListener {
      tryAddGood()
    }
  }

  private fun tryAddGood() {
    val title = binding.inputTitle.text.toString().trim()
    val quantity = binding.inputQuantity.text.toString().trim()
    val date = binding.inputDate.text.toString().trim()
    if (title.isNotEmpty() && quantity.isNotEmpty() && date.isNotEmpty()) {
      val newGood = GoodModel(id = "", title = title, quantity = quantity, date = date)
      networkManager.addGood(newGood, userEmail, result = { result ->
        if (!result) {
          Toast.makeText(requireContext(), "Произошла ошибка", Toast.LENGTH_SHORT).show()
        } else {
          findNavController().navigateUp()
        }
      })
    } else {
      Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
    }
  }
}