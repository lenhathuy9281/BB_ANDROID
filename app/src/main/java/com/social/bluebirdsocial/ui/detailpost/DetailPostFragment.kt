package com.social.bluebirdsocial.ui.detailpost

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.social.bluebirdsocial.databinding.FragmentPostDetailBinding
import com.social.bluebirdsocial.domain.entity.ItemComment
import com.social.bluebirdsocial.ui.post.User
import java.util.Calendar

class DetailPostFragment : Fragment() {

    private var _binding: FragmentPostDetailBinding? = null

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var commentList : ArrayList<ItemComment>
    lateinit var adapter: CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        database = FirebaseDatabase.getInstance()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commentList = ArrayList()
        adapter = CommentAdapter().apply {
            addItems(commentList)
        }
        val id = arguments?.getString("id_post")
        Log.d("check_id", "$id")
        initRcv()
        readData()
        with(binding) {
            btnPostComment.setOnClickListener {
                writeData()
            }
        }

    }

    private fun initRcv(){
        with(binding){
            rcvComment.adapter = adapter
            rcvComment.layoutManager = LinearLayoutManager(requireContext())
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun writeData() {
        databaseRef = database.getReference("posts")
        val newPostRef = databaseRef.child("huyle").child("comments")

        val cmt = ItemComment("trông cũng đc", Calendar.getInstance().timeInMillis, "1")
        newPostRef.child(commentList.size.toString()).setValue(cmt)
    }

    fun readData() {
        databaseRef = database.getReference("posts")
        val cmtRef = databaseRef.child("huyle").child("comments")
        cmtRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (commentList.isNotEmpty()) commentList.clear()
                for (commentSnapshot in dataSnapshot.children) {
                    val cmt = commentSnapshot.getValue(ItemComment::class.java)
                    if (cmt != null) {
                        commentList.add(cmt)
                    }
                    adapter.reset(commentList)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
    }
}