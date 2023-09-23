package com.niyazismayilov.githubrepostats.ui.fragment.repolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.niyazismayilov.githubrepostats.R
import com.niyazismayilov.githubrepostats.data.model.response.RepoItem

class RepoListAdapter(var mItemList: List<RepoItem?>?, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.repo_rv_item, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_rv_item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ItemViewHolder) {
            viewHolder.itemView.setOnClickListener { v: View? ->
                listener.onItemClick(
                    mItemList!![position]
                )
            }
            populateItemRows(viewHolder, position)
        } else if (viewHolder is LoadingViewHolder) {
            showLoadingView(viewHolder, position)
        }
    }

    override fun getItemCount(): Int {
        return if (mItemList == null) 0 else mItemList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (mItemList!![position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    private inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var tvLogin: TextView
        var tvDescription: TextView
        var tvStar: TextView
        var ivAvatar: ImageView

        init {
            tvTitle = itemView.findViewById(R.id.tv_name)
            tvLogin = itemView.findViewById(R.id.tvLogin)
            tvDescription = itemView.findViewById(R.id.tv_subtitle)
            tvStar = itemView.findViewById(R.id.tv_stars)
            ivAvatar = itemView.findViewById(R.id.iv_avatar)
        }
    }

    private inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById(R.id.progressBar)
        }
    }

    private fun showLoadingView(viewHolder: LoadingViewHolder, position: Int) {}
    private fun populateItemRows(viewHolder: ItemViewHolder, position: Int) {
        val item = mItemList!![position]
        viewHolder.tvTitle.text = item!!.name
        viewHolder.tvLogin.text = item.repoOwner.login
        viewHolder.tvDescription.text = item.description
        viewHolder.tvStar.text = item.stars
        Glide.with(viewHolder.itemView)
            .load(item.repoOwner.avatar_url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.no_image)
            .into(viewHolder.ivAvatar)
    }

    interface OnItemClickListener {
        fun onItemClick(item: RepoItem?)
    }
}