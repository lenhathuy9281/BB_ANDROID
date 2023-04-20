package com.social.bluebirdsocial.ui.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

        }

        onClickBinding()
    }

    private fun onClickBinding(){
        with(binding) {
            postHeader.tvPostBarLeft.setOnClickListener {
                goBack()
            }

            postHeader.btnPostBarRight.setOnClickListener {
                addPost(Post("huyle","Hom nay troi dep that day",Calendar.getInstance().timeInMillis))
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