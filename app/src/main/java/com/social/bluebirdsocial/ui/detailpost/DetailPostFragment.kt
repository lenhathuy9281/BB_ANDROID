package com.social.bluebirdsocial.ui.detailpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.social.bluebirdsocial.databinding.FragmentPostDetailBinding
import com.social.bluebirdsocial.domain.entity.ItemComment

class DetailPostFragment : Fragment() {

    private var _binding: FragmentPostDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val commentList = listOf(
        ItemComment(
            time = "12:30 PM",
            content = "Great post, thanks for sharing!"
        ),
        ItemComment(
            time = "2:45 PM",
            content = "I have a question about this topic..."
        ),
        ItemComment(
            time = "4:20 PM",
            content = "This is really helpful, I learned a lot."
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcv()

    }

    private fun initRcv(){
        with(binding){
            rcvComment.adapter = CommentAdapter().apply {
                addItems(commentList)
            }
            rcvComment.layoutManager = LinearLayoutManager(requireContext())
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}