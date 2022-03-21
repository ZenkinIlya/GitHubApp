package com.example.githubapp.presentation.repository

import com.example.githubapp.models.repository.Repository

interface RepositoryClickHandler {

    fun onClickFavorite(repository: Repository)
    fun onClickRepository(repository: Repository)
}