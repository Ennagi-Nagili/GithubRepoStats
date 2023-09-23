package com.niyazismayilov.githubrepostats.ui.fragment.repolist

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.niyazismayilov.githubrepostats.BR
import com.niyazismayilov.githubrepostats.R
import com.niyazismayilov.githubrepostats.data.Resource
import com.niyazismayilov.githubrepostats.data.model.request.RepoListRequest
import com.niyazismayilov.githubrepostats.data.model.response.RepoItem
import com.niyazismayilov.githubrepostats.databinding.FragmentRepoListBinding
import com.niyazismayilov.githubrepostats.di.component.FragmentComponent
import com.niyazismayilov.githubrepostats.ui.base.BaseFragment
import com.niyazismayilov.githubrepostats.ui.fragment.repolist.adapter.RepoListAdapter
import com.niyazismayilov.githubrepostats.utils.Constants

class RepoListFragment : BaseFragment<FragmentRepoListBinding?, RepoListViewModel?>(),
    AdapterView.OnItemSelectedListener {
    private val sorting = arrayOf<String?>(Constants.WEEKLY, Constants.MONTHLY, Constants.DAY)
    var repoListRequest: RepoListRequest? = null
    var isLoading = false
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_repo_list

    override fun performDependencyInjection(buildComponent: FragmentComponent) {
        buildComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        repoListRequest =
            RepoListRequest(mViewModel!!.getLastDateTime(Constants.WEEKLY), "stars", "desc", 1)
        mViewModel!!.getRepoList(repoListRequest)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        setUpSpinner()
        setUpListeners()
    }

    private fun setUpListeners() {
        viewDataBinding!!.reposListRecycler.adapter = RepoListAdapter(
            mViewModel!!.cachedList,
            object : RepoListAdapter.OnItemClickListener {
                override fun onItemClick(item: RepoItem?) {
                    showRepoItemDetail(
                        item!!
                    )
                }
            })
        viewDataBinding!!.btFavList.setOnClickListener { listener: View? ->
            findNavController(
                requireView()
            ).navigate(R.id.action_repoListFragment_to_favoriteListFragment)
        }
        viewDataBinding!!.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            @RequiresApi(api = Build.VERSION_CODES.N)
            override fun afterTextChanged(s: Editable) {
                mViewModel!!.filterByQuery(s.toString())
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpObservers() {
        mViewModel!!.repoListResponseMutableLiveData.observe(viewLifecycleOwner) { result: Resource<List<RepoItem>?> ->
            when (result.type) {
                Resource.Companion.SUCCESS -> {
                    viewDataBinding!!.prRepo.visibility = View.GONE
                    viewDataBinding!!.tvError.visibility = View.GONE
                    viewDataBinding!!.reposListRecycler.adapter!!.notifyDataSetChanged()
                    initScrollListener()
                    isLoading = false
                }

                Resource.Companion.ERROR -> {
                    viewDataBinding!!.reposListRecycler.visibility = View.GONE
                    viewDataBinding!!.prRepo.visibility = View.GONE
                    viewDataBinding!!.tvError.visibility = View.VISIBLE
                }

                Resource.Companion.LOADING -> viewDataBinding!!.prRepo.visibility = View.VISIBLE
            }
        }
    }

    private fun setUpSpinner() {
        viewDataBinding!!.spinner.onItemSelectedListener = this
        val spinnerAdapter: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireActivity(), android.R.layout.simple_spinner_item, sorting)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewDataBinding!!.spinner.adapter = spinnerAdapter
    }

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
        mViewModel!!.clearList()
        when (sorting[i]) {
            Constants.WEEKLY -> {
                repoListRequest!!.createdAt = (mViewModel!!.getLastDateTime(Constants.WEEKLY))
                mViewModel!!.getRepoList(repoListRequest)
            }

            Constants.MONTHLY -> {
                repoListRequest!!.createdAt = (mViewModel!!.getLastDateTime(Constants.MONTHLY))
                mViewModel!!.getRepoList(repoListRequest)
            }

            Constants.DAY -> {
                repoListRequest!!.createdAt = mViewModel!!.getLastDateTime(Constants.DAY)
                mViewModel!!.getRepoList(repoListRequest)
            }
        }
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) {}
    private fun initScrollListener() {
        viewDataBinding!!.reposListRecycler.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == viewDataBinding!!.reposListRecycler.adapter!!.getItemCount() - 1) {
                        repoListRequest!!.goNextPage()
                        mViewModel!!.getRepoList(repoListRequest)
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun showRepoItemDetail(item: RepoItem) {
        val bottomSheetDialog = BottomSheetDialog(requireActivity())
        bottomSheetDialog.setContentView(R.layout.repo_item_bottom_sheet)
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
            mViewModel!!.addToFavorites(item)
            Toast.makeText(activity, getString(R.string.repo_added_info), Toast.LENGTH_LONG).show()
        }
        Glide.with(this)
            .load(item.repoOwner.avatar_url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.no_image)
            .into(bottomSheetDialog.findViewById<View>(R.id.iv_avatar) as ImageView)
        bottomSheetDialog.show()
    }
}