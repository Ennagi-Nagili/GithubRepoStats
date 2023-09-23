package com.niyazismayilov.githubrepostats.ui.fragment.favlist

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.niyazismayilov.githubrepostats.BR
import com.niyazismayilov.githubrepostats.R
import com.niyazismayilov.githubrepostats.data.Resource
import com.niyazismayilov.githubrepostats.data.model.response.RepoItem
import com.niyazismayilov.githubrepostats.databinding.FragmentFavListBinding
import com.niyazismayilov.githubrepostats.di.component.FragmentComponent
import com.niyazismayilov.githubrepostats.ui.base.BaseFragment
import com.niyazismayilov.githubrepostats.ui.fragment.repolist.adapter.RepoListAdapter

class FavoriteListFragment : BaseFragment<FragmentFavListBinding?, FavoriteListViewModel?>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_fav_list

    override fun performDependencyInjection(buildComponent: FragmentComponent) {
        buildComponent.inject(this)
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel!!.favList
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
    }

    private fun setUpObservers() {
        mViewModel!!.repoListResponseMutableLiveData.observe(viewLifecycleOwner) { result: Resource<List<RepoItem>> ->
            when (result.type) {
                Resource.SUCCESS -> viewDataBinding!!.reposListRecycler.adapter =
                    RepoListAdapter(result.data, object : RepoListAdapter.OnItemClickListener {
                        override fun onItemClick(item: RepoItem?) {
                            showRepoItemDetail(item!!)
                        }
                    })

                Resource.Companion.ERROR -> {}
                Resource.Companion.LOADING -> {}
            }
        }
    }

    fun showRepoItemDetail(item: RepoItem) {
        val bottomSheetDialog = BottomSheetDialog(requireActivity())
        bottomSheetDialog.setContentView(R.layout.fav_item_bottom_sheet)
        (bottomSheetDialog.findViewById<View>(R.id.tv_name) as TextView?)!!.text = item.name
        (bottomSheetDialog.findViewById<View>(R.id.tvLogin) as TextView?)!!.text = item.repoOwner.login
        (bottomSheetDialog.findViewById<View>(R.id.tv_subtitle) as TextView?)!!.text = item.description
        (bottomSheetDialog.findViewById<View>(R.id.tv_stars) as TextView?)!!.text = item.stars
        (bottomSheetDialog.findViewById<View>(R.id.tv_language) as TextView?)!!.text = StringBuilder().append(
            getString(R.string.language)
        ).append(item.language).toString()
        (bottomSheetDialog.findViewById<View>(R.id.tv_forks) as TextView?)!!.text = StringBuilder().append(
            getString(R.string.fork)
        ).append(item.forks).toString()
        (bottomSheetDialog.findViewById<View>(R.id.tv_createDate) as TextView?)!!.text =
            StringBuilder().append(getString(R.string.created_at)).append(item.created_at)
                .toString()
        (bottomSheetDialog.findViewById<View>(R.id.tv_repoLink) as TextView?)!!.text = StringBuilder().append(
            getString(R.string.html_url)
        ).append(item.repoOwner.html_url).toString()
        (bottomSheetDialog.findViewById<View>(R.id.bt_makeFav) as Button?)!!.setOnClickListener { view: View? ->
            mViewModel!!.removeFav(item)
            Toast.makeText(activity, getString(R.string.repo_deleted_info), Toast.LENGTH_LONG)
                .show()
            bottomSheetDialog.dismiss()
        }
        Glide.with(this)
            .load(item.repoOwner.avatar_url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.no_image)
            .into((bottomSheetDialog.findViewById<View>(R.id.iv_avatar) as ImageView?)!!)
        bottomSheetDialog.show()
    }
}