package com.social.bluebirdsocial.ui.detailpost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailPostViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Post Fragment"
    }
    val text: LiveData<String> = _text
}