package com.example.githubapp.presentation.searchRepositories

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.R
import com.example.githubapp.componentManager
import com.example.githubapp.databinding.FragmentRepositoriesSearcherBinding
import com.example.githubapp.models.repository.Repository
import com.example.githubapp.models.viewModels.SearchViewModel
import com.example.githubapp.presentation.adapters.RepositoriesAdapter
import com.example.githubapp.presentation.repository.RepositoryClickHandler
import com.google.android.material.progressindicator.BaseProgressIndicator
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class RepositoriesSearcherFragment : MvpAppCompatFragment(R.layout.fragment_repositories_searcher),
    RepositoriesSearcherView {

    private val authorized: Boolean = false
    private lateinit var binding: FragmentRepositoriesSearcherBinding
    private lateinit var adapter: RepositoriesAdapter

    @Inject
    lateinit var presenterProvider: Provider<RepositoriesSearcherPresenter>
    private val repositoriesSearchPresenter by moxyPresenter { presenterProvider.get() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.i("onAttach()")
        requireContext().componentManager.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("onCreateView()")
        binding = FragmentRepositoriesSearcherBinding.inflate(inflater, container, false)

        repositoriesSearchPresenter.initDisplayFavoriteRepositories()
        repositoriesSearchPresenter.initRepositoriesDatabaseListener()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated()")

        initSearchObserve()
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart()")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume()")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause()")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.i("onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.i("onDetach()")
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
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    override fun showRepositories(listRepository: List<Repository>) {
        adapter.repositories = listRepository
    }

    override fun displayFavoriteRepositories(authorized: Boolean) {
        adapter = RepositoriesAdapter(object : RepositoryClickHandler {
            override fun onClickFavorite(repository: Repository) {
                repositoriesSearchPresenter.onClickFavorite(repository)
            }

            override fun onClickRepository(repository: Repository) {
                findNavController().navigate(
                    R.id.action_listRepositoryFragment_to_repositoryFragment,
                    bundleOf("repository" to repository)
                )
            }

        }, authorized)

        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewRepositoriesSearcher.layoutManager = linearLayoutManager
        binding.recyclerViewRepositoriesSearcher.adapter = adapter
    }

    override fun updateRepositories(listRepository: List<Repository>) {
        adapter.updateRepositories(listRepository)
    }
}