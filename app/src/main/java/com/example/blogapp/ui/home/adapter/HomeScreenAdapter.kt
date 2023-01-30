package com.example.blogapp.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blogapp.R
import com.example.blogapp.core.BaseViewHolder
import com.example.blogapp.core.TimeUtils
import com.example.blogapp.data.model.Post
import com.example.blogapp.databinding.PostItemViewBinding


class HomeScreenAdapter(private val postList: List<Post>, private val onPostClickListener: OnPostClickListener) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {


    private var postClickListener: OnPostClickListener? = null

    init {
        postClickListener = onPostClickListener
    }



    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            PostItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeScreenViewHolder(itemBinding, parent.context)
    }

    //Le ponemos a cada uno de los elementos del Post, la info del HomeScreenVH
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is HomeScreenViewHolder -> holder.bind(postList[position])
        }
    }

    //Retorna la cantidad de items en la lista
    override fun getItemCount(): Int = postList.size

    //Bindiamos los datos
    private inner class HomeScreenViewHolder(
        val binding: PostItemViewBinding,
        val context: Context
    ) :
        BaseViewHolder<Post>(binding.root) {
        override fun bind(item: Post) {

            setupProfileInfo(item)
            addPostTimeStamp(item)
            setupPostImage(item)
            setupPostDescription(item)
            tintHeartIcon(item)
            setupLikeCount(item)
            setupClickAction(item)

        }

        private fun setupProfileInfo(post: Post) {
            Glide.with(context).load(post.poster?.profile_picture).centerCrop().into(binding.profilePicture)
            binding.profileName.text = post.poster?.username
        }

        private fun addPostTimeStamp(post: Post) {
            val createAt = (post.created_at?.time?.div(1000L))?.let {
                TimeUtils.getTimaAGo(it.toInt())
            }
            binding.postTimestamp.text = createAt
        }

        private fun setupPostImage(post: Post) {
            Glide.with(context).load(post.post_image).centerCrop().into(binding.postImage)
        }

        private fun setupPostDescription(post: Post) {
            if (post.post_description.isEmpty()) {
                binding.postDescription.visibility = View.GONE
            } else {
                binding.postDescription.text = post.post_description
            }

        }

        private fun tintHeartIcon(post: Post){
            if(!post.liked){
                binding.likeBtn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_border_24))
                binding.likeBtn.setColorFilter(ContextCompat.getColor(context, R.color.black))
            } else {
                binding.likeBtn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_24))
                binding.likeBtn.setColorFilter(ContextCompat.getColor(context, R.color.red_like))
            }
        }

        private fun setupLikeCount(post: Post){
            if(post.likes > 0){
                binding.likeCount.visibility = View.VISIBLE
                binding.likeCount.text = "${post.likes} likes"
            } else {
                binding.likeCount.visibility = View.GONE
            }
        }

        private fun setupClickAction(post: Post){
            binding.likeBtn.setOnClickListener {
                if(post.liked) post.apply { liked = false } else post.apply { liked = true }
                tintHeartIcon(post)
                postClickListener?.onLikeButtonClick(post, post.liked)
            }
        }
    }

    interface OnPostClickListener {
        fun onLikeButtonClick(post: Post, liked: Boolean)
    }
}