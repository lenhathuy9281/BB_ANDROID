package com.social.bluebirdsocial.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.social.bluebirdsocial.R
import com.social.bluebirdsocial.databinding.FragmentPostBinding
import com.social.bluebirdsocial.databinding.FragmentProfileBinding
import com.social.bluebirdsocial.domain.entity.User
import com.social.bluebirdsocial.ui.custom.decodeImage
import com.social.bluebirdsocial.ui.custom.encodeImage

class ProfileFragment: Fragment() {
    private var _binding: FragmentProfileBinding? = null

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var storage: SharedPreferences? = null
    private lateinit var userList : List<User>
    private lateinit var mainUser: User
    private var urlImage: Uri? = null
    private var id: String? = ""



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        database = FirebaseDatabase.getInstance()
        storage = context?.getSharedPreferences("STORAGE", Context.MODE_PRIVATE)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get list user from share preference
        val dataJson = storage?.getString("LIST_USER","")
        userList = dataJson?.let { getListUsers(it) } ?: ArrayList()

        id = FirebaseAuth.getInstance().currentUser?.uid?:""

        mainUser = getUserData(id!!)
        initView()

    }

    private fun initView(){
        with(binding) {
            edtNameUser.setText(mainUser.name)
            edtEmailUser.setText(mainUser.email)
            context?.let { Glide.with(it).load(mainUser.avatar?.decodeImage()).error(R.drawable.img_avatar).into(imageAvatarUser) }

            icChooseAvatar.setOnClickListener {
                selectImage()
            }

            icConfirmChange.setOnClickListener {
                mainUser.name = edtNameUser.text.toString()
                id?.let { it1 -> updateUser(it1, mainUser) }
            }
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 2345)
    }

    private fun getListUsers(jsonData: String): List<User>? {
        return Gson().fromJson<List<User>?>(jsonData, object : TypeToken<List<User>?>() {}.type)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == 2345) {
            Log.d("check_image"," -- ${data?.data}")
            context?.let { Glide.with(it).load(data?.data).into(binding.imageAvatarUser) }
            urlImage = data?.data
            mainUser.avatar = context?.let { urlImage.encodeImage(it) }
        }
    }

    private fun updateUser(id: String, user: User) {
        databaseRef = database.getReference("users")

        databaseRef.child(id).setValue(user).addOnSuccessListener {
            findNavController().navigate(R.id.action_navigation_profile_to_navigation_home)
        }
    }

    private fun getUserData(id: String): User {
        if (userList.isNotEmpty()) {
            for (user in userList) {
                if (user.idUser.equals(id)){
                    return user
                }
            }
        }
        return User()
    }
}