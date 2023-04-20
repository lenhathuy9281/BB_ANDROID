package com.social.bluebirdsocial.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.social.bluebirdsocial.databinding.FragmentNotificationsBinding
import com.social.bluebirdsocial.domain.entity.ItemNotification
import com.social.bluebirdsocial.domain.entity.Post

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference

    lateinit var adapter: NotificationAdapter
    lateinit var notificationList: ArrayList<ItemNotification>
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        database = FirebaseDatabase.getInstance()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notificationList = ArrayList()
        readData()
        adapter = NotificationAdapter().apply {
            addItems(notificationList)

            onClickNotification = {

            }
        }
        binding.rvNotifications.adapter = adapter
        binding.rvNotifications.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun readData() {

        databaseRef = database.getReference("notifications")
        val postRef = databaseRef.child("huyle")
        postRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (userSnapshot in dataSnapshot.children) {
                    val notification = userSnapshot.getValue(ItemNotification::class.java)
                    if (notification != null) {
                        notificationList.add(notification)
                    }
                }
                adapter.reset(notificationList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
    }
}