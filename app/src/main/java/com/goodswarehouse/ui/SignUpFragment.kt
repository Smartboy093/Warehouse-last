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
import com.goodswarehouse.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {
  private val binding by lazy { FragmentSignUpBinding.inflate(layoutInflater) }
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
      findNavController().navigateUp()
    }
    binding.btnSignUp.setOnClickListener {
      trySignUp()
    }
  }

  private fun trySignUp() {
    val email = binding.inputEmail.text.toString().trim()
    val nickname = binding.inputNickname.text.toString().trim()
    val password = binding.inputPassword.text.toString().trim()
    val passwordRepeat = binding.inputPasswordRepeat.text.toString().trim()
    if (email.isNotEmpty() && password.isNotEmpty() && nickname.isNotEmpty() && passwordRepeat.isNotEmpty()) {
      if (password.length >= 6) {
        if (password == passwordRepeat) {
          firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            val userEmail =
              firebaseAuth.currentUser?.email?.replace("@", "_")?.replace(".", "-") ?: "error"
            networkManager.userSignUp(userEmail, nickname, result = { result ->
              if (result) {
                val intent = Intent(requireActivity(), MainActivity::class.java)
                requireActivity().finish()
                startActivity(intent)
              } else {
                Toast.makeText(requireContext(), "Профиль не найден", Toast.LENGTH_SHORT).show()
              }
            })
          }.addOnFailureListener {
            Toast.makeText(requireContext(), "Возможно, почта занята", Toast.LENGTH_SHORT).show()
          }
        } else {
          Toast.makeText(requireContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show()
        }
      } else {
        Toast.makeText(requireContext(), "Мин. длина пароля = 6", Toast.LENGTH_SHORT).show()
      }
    } else {
      Toast.makeText(requireContext(), "Заполните поля", Toast.LENGTH_SHORT).show()
    }
  }
}