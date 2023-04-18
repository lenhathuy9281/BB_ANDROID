package com.social.bluebirdsocial.ui.post

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.social.bluebirdsocial.R
import com.social.bluebirdsocial.databinding.FragmentPostBinding

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null

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


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            tvPostStatus.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    TODO("Not yet implemented")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    postHeader.btnPostBarRight.isEnabled = !s.isNullOrBlank()
                }

                override fun afterTextChanged(s: Editable?) {
                    TODO("Not yet implemented")
                }

            })
        }

        onClickBinding()
    }

    private fun onClickBinding(){
        with(binding) {
            postHeader.tvPostBarLeft.setOnClickListener {
                goBack()
            }

            postHeader.btnPostBarRight.setOnClickListener {


                goBack()
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
}