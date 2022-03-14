package com.example.githubapp.presentation.savedRepositories

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.githubapp.R
import com.example.githubapp.componentManager
import com.example.githubapp.databinding.FragmentSavedRepositoriesBinding
import com.example.githubapp.models.viewModels.SearchViewModel
import com.example.githubapp.models.repository.Repository
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class SavedRepositoriesFragment : MvpAppCompatFragment(R.layout.fragment_saved_repositories),
    SavedRepositoriesView {

    private lateinit var binding: FragmentSavedRepositoriesBinding

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
        val searchViewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]
        searchViewModel.getQuery().observe(viewLifecycleOwner) {
            savedRepositoriesPresenter.onSearchSavedRepositories(it)
        }
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

    override fun showLoading(show: Boolean) {
        TODO("Not yet implemented")
    }

    override fun showError(error: String) {
        TODO("Not yet implemented")
    }

    override fun showRepositories(listRepository: List<Repository>) {
        TODO("Not yet implemented")
    }

    override fun markRepositoryAsSaved(repository: Repository) {
        TODO("Not yet implemented")
    }
}
