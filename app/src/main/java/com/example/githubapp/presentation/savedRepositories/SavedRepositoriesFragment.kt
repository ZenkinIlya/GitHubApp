package com.example.githubapp.presentation.savedRepositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentSavedRepositoriesBinding
import com.example.githubapp.presentation.githubRepositories.RepositoriesFragment
import moxy.MvpAppCompatFragment

class SavedRepositoriesFragment() : MvpAppCompatFragment(R.layout.fragment_saved_repositories) {

    lateinit var binding: FragmentSavedRepositoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }
}