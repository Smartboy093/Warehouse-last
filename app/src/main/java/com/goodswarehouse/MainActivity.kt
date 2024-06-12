package com.goodswarehouse

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.goodswarehouse.network.NetworkManager
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
  private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
  private val userEmail by lazy {
    firebaseAuth.currentUser?.email?.replace("@", "_")?.replace(".", "-") ?: "error"
  }
  val appViewModel by viewModels<AppViewModel>()
  val networkManager by lazy { NetworkManager(appViewModel) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    initialize()
  }

  private fun initialize() {
    checkData()
  }

  private fun checkData() {
    if (userEmail != "error") {
      networkManager.firebaseGoods(userEmail)
    }
  }
}