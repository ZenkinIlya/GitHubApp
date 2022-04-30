package com.example.githubapp.presentation.repository

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentRepositoryBinding
import com.example.githubapp.presentation.menuRepositories.RepositoriesFragmentArgs

class RepositoryFragment : Fragment(R.layout.fragment_repository) {

    private lateinit var binding: FragmentRepositoryBinding
    private val args: RepositoriesFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoryBinding.inflate(inflater, container, false)


        val repository = args.repository
        RepositoryViewHandler.DefaultRepository(binding, repository)
            .bindView()
            .bindFavoriteImageView(false)

        return binding.root
    }
}