package com.goodswarehouse.network

import com.goodswarehouse.AppViewModel
import com.goodswarehouse.model.GoodModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NetworkManager(private val appViewModel: AppViewModel) {
  private val firebaseDatabase by lazy { FirebaseDatabase.getInstance().reference }

  fun firebaseGoods(email: String) {
    firebaseDatabase.child(Constant.GOODS).child(email)
      .addValueEventListener(FirebaseValueEventListener {
        val list = mutableListOf<GoodModel>()
        for (item in it.children) {
          val id = item.key.toString().trim()
          val title = item.child(Constant.TITLE).value.toString().trim()
          val quantity = item.child(Constant.QUANTITY).value.toString().trim()
          val date = item.child(Constant.DATE).value.toString().trim()
          val newGood = GoodModel(id = id, title = title, quantity = quantity, date = date)
          list.add(newGood)
        }
        appViewModel.setListGoods(list)
      })
  }

  fun removeGood(id: String, email: String, quantity: Int, result: (Boolean) -> Unit) {
    firebaseDatabase.child(Constant.GOODS).child(email).child(id).child(Constant.QUANTITY)
      .let { reference ->
        reference.get().addOnSuccessListener {
          if (it.exists()) {
            val oldQuantity = it.value.toString().trim().toInt()
            if (quantity < oldQuantity) {
              reference.setValue(oldQuantity - quantity).addOnSuccessListener {
                result(true)
              }.addOnFailureListener {
                result(false)
              }
            } else {
              firebaseDatabase.child(Constant.GOODS).child(email).child(id).removeValue()
                .addOnSuccessListener {
                  result(true)
                }.addOnFailureListener {
                result(false)
              }
            }
          } else {
            result(false)
          }
        }.addOnFailureListener {
          result(false)
        }
      }
  }

  fun addGood(goodModel: GoodModel, email: String, result: (Boolean) -> Unit) {
    firebaseDatabase.child(Constant.GOODS).child(email).let { reference ->
      val id = reference.push().key.toString().trim()
      reference.child(id).child(Constant.TITLE).setValue(goodModel.title)
      reference.child(id).child(Constant.QUANTITY).setValue(goodModel.quantity)
      reference.child(id).child(Constant.DATE).setValue(goodModel.date).addOnSuccessListener {
        result(true)
      }.addOnFailureListener {
        result(false)
      }
    }
  }

  fun userSignIn(email: String, result: (Boolean) -> Unit) {
    firebaseDatabase.child(Constant.USERS).child(email).get().addOnSuccessListener {
      if (it.exists()) {
        result(true)
      } else {
        result(false)
      }
    }.addOnFailureListener {
      result(false)
    }
  }

  fun userSignUp(email: String, nickname: String, result: (Boolean) -> Unit) {
    firebaseDatabase.child(Constant.USERS).child(email).let { reference ->
      reference.child(Constant.NICKNAME).setValue(nickname).addOnSuccessListener {
        result(true)
      }.addOnFailureListener {
        result(false)
      }
    }
  }

  class FirebaseValueEventListener(val snapshot: (DataSnapshot) -> Unit) : ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
      snapshot(snapshot)
    }

    override fun onCancelled(error: DatabaseError) {
    }
  }
}