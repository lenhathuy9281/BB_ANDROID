package com.social.bluebirdsocial.ui.post

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.social.bluebirdsocial.R
import com.social.bluebirdsocial.databinding.FragmentPostBinding
import com.social.bluebirdsocial.domain.entity.Post
import java.util.Calendar

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val root: View = binding.root
        database = FirebaseDatabase.getInstance()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            imageUpload.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, 1234)
            }
        }

        onClickBinding()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == 1234) {
            Log.d("check_image"," -- ${data?.data}")
            context?.let { Glide.with(it).load(data?.data).into(binding.imageUpload) }
        }
    }

    private fun onClickBinding(){
        with(binding) {
            postHeader.tvPostBarLeft.setOnClickListener {
                goBack()
            }

            postHeader.btnPostBarRight.setOnClickListener {
                if (!tvPostStatus.text.isNullOrBlank())
                    addPost(Post(Calendar.getInstance().timeInMillis.toString(), tvPostStatus.text.toString(),Calendar.getInstance().timeInMillis, idUser = FirebaseAuth.getInstance().currentUser?.uid))
            }
        }
    }

    private fun goBack(){
        findNavController().navigate(R.id.action_navigation_post_to_navigation_home)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun addPost(post: Post){
        databaseRef = database.getReference("posts")
        post.id?.let { databaseRef.child(it).setValue(post).addOnSuccessListener {
            Log.d("check_post","done")
            goBack()
        }.addOnFailureListener { e ->
            e.printStackTrace()
        } }
    }
}