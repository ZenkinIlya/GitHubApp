package com.example.githubapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentRepositoryBinding
import com.example.githubapp.models.repository.Repository
import com.example.githubapp.presentation.repository.RepositoryClickHandler
import com.example.githubapp.presentation.repository.RepositoryViewHandler

class RepositoriesAdapter(
    private val repositoryClickHandler: RepositoryClickHandler,
    private val authorized: Boolean
) :
    PagingDataAdapter<Repository, RepositoriesAdapter.RepositoriesViewHolder>(
        COMPARATOR
    ),
    View.OnClickListener {

    var repositories: List<Repository> = emptyList()
        set(newValue) {
            val calculateDiff =
                DiffUtil.calculateDiff(RepositoriesDiffUtils(field, newValue))
            field = newValue
            calculateDiff.dispatchUpdatesTo(this)
        }

    override fun onClick(view: View) {
        val repository = view.tag as Repository
        when (view.id) {
            R.id.favorite_image_view -> {
                repositoryClickHandler.onClickFavorite(repository)
            }
            else -> {
                repositoryClickHandler.onClickRepository(repository)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesViewHolder {
        val binding =
            FragmentRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.root.setOnClickListener(this)
        binding.favoriteImageView.setOnClickListener(this)

        return RepositoriesViewHolder(binding, authorized)
    }

    override fun onBindViewHolder(holder: RepositoriesViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = repositories.size

    fun updateRepositories(favoriteRepositories: List<Repository>) {
        //TODO Optimize update repositories
        val currentRepositories = repositories.map { it.copy() }
        currentRepositories.forEach { repository ->
            repository.favorite = favoriteRepositories.contains(repository)
        }
        this.repositories = currentRepositories
    }

    fun updateSavedRepositories(favoriteRepositories: List<Repository>, value: String?) {
        val filterFavoriteRepositories: List<Repository>
        if (value.isNullOrBlank()) {
            repositories = favoriteRepositories
        } else {
            filterFavoriteRepositories = favoriteRepositories.filter { repository ->
                repository.name.contains(
                    value.toString(),
                    ignoreCase = true
                )
            }.toList()
            repositories = filterFavoriteRepositories
        }
    }

    class RepositoriesViewHolder(
        private val binding: FragmentRepositoryBinding,
        private val authorized: Boolean
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repository: Repository) {
            this.itemView.tag = repository
            with(binding) {
                favoriteImageView.tag = repository

                RepositoryViewHandler.DefaultRepository(
                    this,
                    repository
                )
                    .bindView()
                    .bindFavoriteImageView(authorized)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class RepositoriesDiffUtils(
    private val oldList: List<Repository>,
    private val newList: List<Repository>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRepository = oldList[oldItemPosition]
        val newRepository = newList[newItemPosition]
        return oldRepository.id == newRepository.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRepository = oldList[oldItemPosition]
        val newRepository = newList[newItemPosition]
        return oldRepository == newRepository && oldRepository.favorite == newRepository.favorite
    }

}

