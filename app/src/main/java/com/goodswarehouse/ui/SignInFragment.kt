package com.goodswarehouse.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.goodswarehouse.MainActivity
import com.goodswarehouse.R
import com.goodswarehouse.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {
  private val binding by lazy { FragmentSignInBinding.inflate(layoutInflater) }
  private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
  private val networkManager by lazy { (requireActivity() as MainActivity).networkManager }

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
    binding.btnSignIn.setOnClickListener {
      trySignIn()
    }
    binding.btnSignUp.setOnClickListener {
      findNavController().navigate(R.id.signUpFragment)
    }
  }

  private fun trySignIn() {
    val email = binding.inputEmail.text.toString().trim()
    val password = binding.inputPassword.text.toString().trim()
    if (email.isNotEmpty() && password.isNotEmpty()) {
      firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
        val userEmail =
          firebaseAuth.currentUser?.email?.replace("@", "_")?.replace(".", "-") ?: "error"
        networkManager.userSignIn(userEmail, result = { result ->
          if (result) {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            requireActivity().finish()
            startActivity(intent)
          } else {
            Toast.makeText(requireContext(), "Профиль не найден", Toast.LENGTH_SHORT).show()
          }
        })
      }.addOnFailureListener {
        Toast.makeText(requireContext(), "Возможно, пароль неверный", Toast.LENGTH_SHORT).show()
      }
    } else {
      Toast.makeText(requireContext(), "Заполните поля", Toast.LENGTH_SHORT).show()
    }
  }

}