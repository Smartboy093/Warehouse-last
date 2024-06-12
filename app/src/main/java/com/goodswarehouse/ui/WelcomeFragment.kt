package com.goodswarehouse.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.goodswarehouse.R
import com.goodswarehouse.databinding.FragmentWelcomeBinding
import com.google.firebase.auth.FirebaseAuth

class WelcomeFragment : Fragment() {
  private val binding by lazy { FragmentWelcomeBinding.inflate(layoutInflater) }
  private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ) = binding.root

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initialize()
  }

  private fun initialize() {
    checkAuth()
  }

  private fun checkAuth() {
    Handler(Looper.getMainLooper()).postDelayed({
      if (isAdded) {
        findNavController().popBackStack()
        if (firebaseAuth.currentUser != null) {
          findNavController().navigate(R.id.menuFragment)
        } else {
          findNavController().navigate(R.id.signInFragment)
        }
      }
    }, 1000)
  }
}