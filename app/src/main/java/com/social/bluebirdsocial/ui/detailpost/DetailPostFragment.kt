package com.social.bluebirdsocial.ui.detailpost

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.social.bluebirdsocial.databinding.FragmentPostDetailBinding
import com.social.bluebirdsocial.domain.entity.ItemComment
import com.social.bluebirdsocial.domain.entity.Post
import com.social.bluebirdsocial.domain.entity.User
import com.social.bluebirdsocial.ui.custom.toDateString
import java.util.Calendar

class DetailPostFragment : Fragment() {

    private var _binding: FragmentPostDetailBinding? = null

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var commentList : ArrayList<ItemComment>
    lateinit var userList : List<User>
    lateinit var adapter: CommentAdapter

    private var storage: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        database = FirebaseDatabase.getInstance()
        storage = context?.getSharedPreferences("STORAGE", Context.MODE_PRIVATE)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get list user from share preference
        commentList = ArrayList()
        val dataJson = storage?.getString("LIST_USER","")
        userList = dataJson?.let { getListUsers(it) } ?: ArrayList()

        adapter = CommentAdapter().apply {
            addItems(commentList)
            listUser = ArrayList<User>().apply {
                addAll(userList)
            }
        }

        val id = arguments?.getString("id_post")
        Log.d("check_id", "$id")

        initRcv()

        if (id != null) {
            readData(id)
        }

        id?.let { getPost(it) }



        with(binding) {
            btnPostComment.setOnClickListener {
                if (id != null) {
                    writeData(id)
                }
            }
        }
        Log.d("check_user", "--${storage?.getString("LIST_USER","")}")

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

    private fun getListUsers(jsonData: String): List<User>? {
        return Gson().fromJson<List<User>?>(jsonData, object : TypeToken<List<User>?>() {}.type)
    }

    fun writeData(id: String) {
        databaseRef = database.getReference("posts")
        val newPostRef = databaseRef.child(id).child("comments")

        val cmt = ItemComment(binding.edtCommentNew.text.toString(), Calendar.getInstance().timeInMillis, FirebaseAuth.getInstance().currentUser?.uid ?: "")

        newPostRef.child(if (commentList.isNotEmpty()) commentList.size.toString() else "0").setValue(cmt).addOnSuccessListener {
            readData(id)
            binding.edtCommentNew.setText("")
        }
    }

    fun readData(id: String) {
        databaseRef = database.getReference("posts")
        val cmtRef = databaseRef.child(id).child("comments")
        cmtRef.addValueEventListener(object : ChildEventListener, ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (commentList.isNotEmpty()) commentList.clear()
                for (commentSnapshot in dataSnapshot.children) {
                    val cmt = commentSnapshot.getValue(ItemComment::class.java)
                    if (cmt != null) {
                        commentList.add(cmt)
                    }
                }
                adapter.reset(commentList)
                getPost(id)

            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val cmt = snapshot.getValue(ItemComment::class.java)
                if (cmt != null) {
                    commentList.add(cmt)
                    adapter.addItem(cmt)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
    }

    fun getPost(id: String){
        databaseRef = database.getReference("posts")
        val detailPostRef = databaseRef.child(id)
        detailPostRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val post = snapshot.getValue(Post::class.java)
                with(binding) {
                    tvDetailNotificationContent.text = post?.body ?: ""
                    tvPostDetailHour.text = post?.timestamp?.toDateString() ?: "0"
                    tvDetailPostLike.text = post?.likes.toString()
                    tvDetailPostShare.text = post?.shares.toString()

                    val user = post?.idUser?.let { adapter.getUserData(it) }
                    if (user != null && !user.name.isNullOrBlank()) {
                        tvPostDetailName.text = user.name
                        tvPostDetailUser.text = user.email
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}