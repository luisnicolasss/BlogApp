package com.example.blogapp.domain.auth.home

import com.example.blogapp.core.Result
import com.example.blogapp.data.model.Post
import com.example.blogapp.data.remote.home.HomeScreenDataSource
import kotlinx.coroutines.flow.Flow

class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource): HomeScreeRepo {

    override suspend fun getLatestPosts(): Result<List<Post>> = dataSource.getLatestPosts()
    override suspend fun registerLikeButtonState(postId: String, liked: Boolean) = dataSource.registerLikeButtonState(postId, liked)
}