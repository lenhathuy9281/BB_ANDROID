package com.social.bluebirdsocial.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.social.bluebirdsocial.R
import com.social.bluebirdsocial.databinding.FragmentHomeBinding
import com.social.bluebirdsocial.domain.entity.Post

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    val posts = listOf(
        Post("This is my first post!"),
        Post("I love coding in Kotlin!"),
        Post("Here's a picture of my cat.")
    )
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

        initView()

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
            rcvPostHome.adapter = PostAdapter().apply {
                addItems(posts)
                onClickComment = {
                    onClickNavigation()
                }
            }
            rcvPostHome.addItemDecoration(dividerItemDecoration)
            rcvPostHome.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onClickNavigation(){
        findNavController().navigate(R.id.action_navigation_home_to_navigation_comment)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}