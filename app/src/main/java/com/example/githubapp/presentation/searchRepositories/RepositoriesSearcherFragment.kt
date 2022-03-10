package com.example.githubapp.presentation.searchRepositories

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.R
import com.example.githubapp.componentManager
import com.example.githubapp.databinding.FragmentRepositoriesSearcherBinding
import com.example.githubapp.models.repository.Repository
import com.example.githubapp.models.SearchViewModel
import com.google.android.material.progressindicator.BaseProgressIndicator
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class RepositoriesSearcherFragment : MvpAppCompatFragment(R.layout.fragment_repositories_searcher),
    RepositoriesSearcherView {

    private lateinit var binding: FragmentRepositoriesSearcherBinding
    private lateinit var adapter: RepositoriesSearcherAdapter

    @Inject
    lateinit var presenterProvider: Provider<RepositoriesSearcherPresenter>
    private val repositoriesSearchPresenter by moxyPresenter { presenterProvider.get() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().componentManager.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoriesSearcherBinding.bind(view)

        initSearchObserve()

        adapter = RepositoriesSearcherAdapter()
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewRepositoriesSearcher.layoutManager = linearLayoutManager
        binding.recyclerViewRepositoriesSearcher.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy()")
    }

    private fun initSearchObserve() {
        val searchViewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]
        searchViewModel.getQuery().observe(viewLifecycleOwner) {
            repositoriesSearchPresenter.onSearchRepositories(mapOf("q" to it))
        }
    }

    override fun showLoading(show: Boolean) {
        if (show) {
            binding.progressIndicator.showAnimationBehavior = BaseProgressIndicator.SHOW_OUTWARD
            binding.progressIndicator.visibility = View.VISIBLE
        } else {
            binding.progressIndicator.setVisibilityAfterHide(View.INVISIBLE)
            binding.progressIndicator.hideAnimationBehavior = BaseProgressIndicator.HIDE_OUTWARD
            binding.progressIndicator.hide()
        }
    }

    override fun showError(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        Timber.e(error)
    }

    override fun showRepositories(listRepository: List<Repository>) {
        Timber.i(listRepository.toString())
        adapter.repositories = listRepository
    }

    override fun markRepositoryAsSaved(repository: Repository) {

    }
}