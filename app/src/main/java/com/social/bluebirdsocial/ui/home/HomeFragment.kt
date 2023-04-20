package com.social.bluebirdsocial.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.social.bluebirdsocial.R
import com.social.bluebirdsocial.databinding.FragmentHomeBinding
import com.social.bluebirdsocial.domain.entity.Account
import com.social.bluebirdsocial.domain.entity.Post
import com.social.bluebirdsocial.ui.post.User

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference


    lateinit var postList: ArrayList<Post>
    lateinit var adapter: PostAdapter
//    private val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postList = ArrayList()
        adapter = PostAdapter().apply {
            addItems(postList)
            onClickComment = {
                onClickNavigation(it)
            }
        }
        database = FirebaseDatabase.getInstance()
        databaseRef = database.reference
        initView()
//        getUser()
        readData()
//        homeViewModel.text.observe(viewLifecycleOwner) {
//
//        }

    }

    private fun initView(){
        with(binding){

            val dividerItemDecoration = DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
            rcvPostHome.adapter = adapter
            rcvPostHome.addItemDecoration(dividerItemDecoration)
            rcvPostHome.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onClickNavigation(id: String){
        findNavController().navigate(R.id.action_navigation_home_to_navigation_comment, Bundle().apply {
            putString("id_post",id)
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getUser() {

        databaseRef = database.getReference("account")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(Account::class.java)
                    Log.d("check_account","---${user}")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
    }

    fun writeData() {
        val usersRef = databaseRef.child("users")
        val user = User("John", "Doe")
        usersRef.child("1").setValue(user)
    }

    fun readData() {
        val postRef = database.getReference("posts")
        postRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (postList.isNotEmpty()) postList.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val post = postSnapshot.getValue(Post::class.java)
                    Log.d("check_read", "$post")
                    if (post != null) {
                        postList.add(post)
                    }
                }
                adapter.reset(postList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
    }
}