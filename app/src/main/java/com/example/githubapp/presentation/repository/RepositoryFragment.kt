package com.example.githubapp.presentation.repository

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentRepositoryBinding
import com.example.githubapp.models.repository.Repository
import com.example.githubapp.presentation.menuRepositories.RepositoriesFragmentArgs
import timber.log.Timber

class RepositoryFragment : Fragment(R.layout.fragment_repository) {

    private lateinit var binding: FragmentRepositoryBinding
    private val args: RepositoriesFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoryBinding.inflate(inflater, container, false)

        val repository = args.repository
        with(binding){
            if (repository.owner.avatarUrl?.isNotBlank() == true) {
                Glide.with(avatarImageView.context)
                    .load(repository.owner.avatarUrl)
                    .circleCrop()
                    .placeholder(R.drawable.ic_account)
                    .error(R.drawable.ic_account)
                    .into(avatarImageView)
            } else {
                Glide.with(avatarImageView.context).clear(avatarImageView)
                avatarImageView.setImageResource(R.drawable.ic_account)
            }
            login.text = repository.owner.login
            nameRepository.text = repository.name
            description.text = repository.description
            stars.text = repository.stars_count.toString()
            forks.text = repository.forks_count.toString()
            dateOfCreation.text = repository.dateOfCreation

            favoriteImageView.visibility = View.GONE
        }

        return binding.root
    }
}