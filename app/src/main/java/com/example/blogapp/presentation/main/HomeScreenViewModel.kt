package com.example.blogapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.blogapp.core.Result
import com.example.blogapp.domain.auth.home.HomeScreeRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class HomeScreenViewModel(private val repo: HomeScreeRepo) : ViewModel() {

    fun fetchLatestPosts() = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        kotlin.runCatching {
            repo.getLatestPosts()
        }.onSuccess { postList ->
            emit(postList)
        }.onFailure {
            emit(Result.Failure(Exception(it.message)))
        }
    }

    fun registerLikeButtonState(postId: String, liked: Boolean) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Result.Loading())

        kotlin.runCatching {
            repo.registerLikeButtonState(postId, liked)
        }.onSuccess {
            emit(Result.Success(Unit))
        }.onFailure { throwable ->
            emit(Result.Failure(Exception(throwable.message)))
        }
    }
}

class HomeScreenViewModelFactory(private val repo: HomeScreeRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeScreeRepo::class.java).newInstance(repo)
    }
}