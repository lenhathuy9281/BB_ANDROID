package com.social.bluebirdsocial.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.social.bluebirdsocial.databinding.FragmentNotificationsBinding
import com.social.bluebirdsocial.domain.entity.ItemNotification

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    val notificationList = listOf(
        ItemNotification(
            avatar = "https://example.com/avatar1.png",
            content = "New message from John"
        ),
        ItemNotification(
            avatar = "https://example.com/avatar2.png",
            content = "You have a new follower"
        ),
        ItemNotification(
            avatar = "https://example.com/avatar3.png",
            content = "Your post has been liked"
        )
    )
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

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvNotifications.adapter = NotificationAdapter().apply {
            addItems(notificationList)

            onClickNotification = {

            }
        }
        binding.rvNotifications.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}