package com.kelompok.satwalaya.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok.satwalaya.model.PostResponse
import com.kelompok.satwalaya.network.PostRetrofitClient
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    private val _posts = MutableLiveData<List<PostResponse>>()
    val posts: LiveData<List<PostResponse>> = _posts

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init { fetchPosts() }

    fun fetchPosts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _posts.value = PostRetrofitClient.instance.getPosts()
            } catch (e: Exception) {
                _error.value = "Gagal memuat data: ${e.message}"
            }
            _isLoading.value = false
        }
    }
}