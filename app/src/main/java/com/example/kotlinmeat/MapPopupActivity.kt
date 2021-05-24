package com.example.kotlinmeat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinmeat.databinding.ActivityMapPopupBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class MapPopupActivity : AppCompatActivity() {

    lateinit var binding: ActivityMapPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateProfile()





            //finish()
    }

    interface  FirebaseCallback{
        fun onCallback(list: MutableList<String>)
    }
    private fun readFriebaseData(firebaseCallBack: ProfileActivity.FirebaseCallback)
    {
        val uid = intent.getStringExtra("id")
        val ref = FirebaseDatabase.getInstance().getReference("users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<String>()
                val user = snapshot.child("Name").getValue(String::class.java)
                list.add(user!!)
                val email = snapshot.child("Email").getValue(String::class.java)
                list.add(email!!)
                val image = snapshot.child("ImageLink").getValue(String::class.java)
                list.add(image!!)
                val surname = snapshot.child("Surename").getValue(String::class.java)
                list.add(surname!!)
                val description = snapshot.child("Info").getValue(String::class.java)
                list.add(description!!)
                val nickname = snapshot.child("Nickname").getValue(String::class.java)
                list.add(nickname!!)
                firebaseCallBack.onCallback(list)
            }
        })
    }

    private fun updateProfile()
    {
        readFriebaseData(object: ProfileActivity.FirebaseCallback {
            override fun onCallback(list: MutableList<String>){
                binding.textViewNameMapPopup.text = "Name: " + list.elementAt(0)  + " " + list.elementAt(3) //Html.fromHtml("Name: "+ "<b>" + list.elementAt(0)  + list.elementAt(3) + "</b>" )
                binding.textViewDescMapPopup.text = "Info: " + list.elementAt(4)  //Html.fromHtml("Info: " + "<b>" + list.elementAt(4) + "</b>")
                binding.textViewNicknameMapPopup.text ="Nickname: " + list.elementAt(5) //Html.fromHtml("Nickname: " + "<b>" + list.elementAt(5)+ "</b>")
                Log.d("MapPopUp11","Name: ${list.elementAt(0)} ${list.elementAt(3)}, Info: ${list.elementAt(4)}, Nick: ${list.elementAt(5)}, Image: ${list.elementAt(2)} ")
                Picasso.with(this@MapPopupActivity).load(list.elementAt(2)).fit().centerInside().into(binding.profilePhotoMapPopup)
            }
        })

    }


}