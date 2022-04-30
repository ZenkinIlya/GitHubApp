package com.example.githubapp.presentation.repository

import android.view.View
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentRepositoryBinding
import com.example.githubapp.models.repository.Repository

interface RepositoryViewHandler {

    fun bindView(): RepositoryViewHandler
    fun bindFavoriteImageView(show: Boolean): RepositoryViewHandler

    abstract class Abstract(
        private val binding: FragmentRepositoryBinding,
        private val repository: Repository
    ) : RepositoryViewHandler {

        override fun bindView(): RepositoryViewHandler {
            with(binding) {
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
            }
            return this
        }

        override fun bindFavoriteImageView(show: Boolean): RepositoryViewHandler {
            with(binding) {
                if (show) {
                    if (repository.favorite) {
                        Glide.with(favoriteImageView.context)
                            .load(R.drawable.ic_favorite)
                            .placeholder(R.drawable.ic_favorite_border)
                            .error(R.drawable.ic_favorite_border)
                            .into(favoriteImageView)
                    } else {
                        Glide.with(favoriteImageView.context).clear(favoriteImageView)
                        favoriteImageView.setImageResource(R.drawable.ic_favorite_border)
                    }
                }
            }
            return this
        }
    }

    class DefaultRepository(binding: FragmentRepositoryBinding, repository: Repository) :
        Abstract(binding, repository)
}