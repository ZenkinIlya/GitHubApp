package com.example.githubapp.presentation.savedRepositories

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.R
import com.example.githubapp.componentManager
import com.example.githubapp.databinding.FragmentSavedRepositoriesBinding
import com.example.githubapp.models.viewModels.SearchViewModel
import com.example.githubapp.models.repository.Repository
import com.example.githubapp.presentation.adapters.RepositoriesAdapter
import com.example.githubapp.presentation.repository.RepositoryClickHandler
import com.google.android.material.progressindicator.BaseProgressIndicator
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class SavedRepositoriesFragment : MvpAppCompatFragment(R.layout.fragment_saved_repositories),
    SavedRepositoriesView {

    private lateinit var binding: FragmentSavedRepositoriesBinding
    private lateinit var adapter: RepositoriesAdapter

    @Inject
    lateinit var presenterProvider: Provider<SavedRepositoriesPresenter>
    private val savedRepositoriesPresenter by moxyPresenter { presenterProvider.get() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.i("onAttach()")
        requireContext().componentManager.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("onCreateView()")
        binding = FragmentSavedRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated()")

        initSearchObserve()

        adapter = RepositoriesAdapter(object : RepositoryClickHandler {
            override fun onClickFavorite(repository: Repository) {
                savedRepositoriesPresenter.onClickFavorite(repository)
            }

            override fun onClickRepository(repository: Repository) {
                findNavController().navigate(R.id.action_listRepositoryFragment_to_repositoryFragment)
            }

        }, true)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSavedRepositories.layoutManager = linearLayoutManager
        binding.recyclerViewSavedRepositories.adapter = adapter

        savedRepositoriesPresenter.onGetFavoriteRepositories(emptyMap())
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
            savedRepositoriesPresenter.onGetFavoriteRepositories(mapOf("q" to it))
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

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        Timber.e(error)
    }

    override fun showRepositories(listRepository: List<Repository>) {
        adapter.repositories = listRepository
    }
}
