package com.example.githubapp.presentation.searchRepositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentRepositoriesBinding
import com.example.githubapp.models.Repository
import com.example.githubapp.models.SearchViewModel
import com.example.githubapp.presentation.githubRepositories.RepositoriesFragment
import com.example.githubapp.presentation.githubRepositories.RepositoriesPresenter
import com.example.githubapp.presentation.githubRepositories.RepositoriesView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class RepositoriesSearcherFragment() : MvpAppCompatFragment(R.layout.fragment_repositories_searcher) {

    lateinit var binding: FragmentRepositoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchViewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]
        searchViewModel.getQuery().observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
}