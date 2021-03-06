package com.example.githubapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    RecyclerView.Adapter<RepositoriesAdapter.RepositoriesViewHolder>(),
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
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentRepositoryBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.favoriteImageView.setOnClickListener(this)

        return RepositoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoriesViewHolder, position: Int) {
        val repository = repositories[position]
        with(holder.binding) {
            holder.itemView.tag = repository
            favoriteImageView.tag = repository

            RepositoryViewHandler.DefaultRepository(this, repository)
                .bindView()
                .bindFavoriteImageView(authorized)
        }
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
        }else{
            filterFavoriteRepositories = favoriteRepositories.filter { repository ->
                repository.name.contains(
                    value.toString(),
                    ignoreCase = true
                )
            }.toList()
            repositories = filterFavoriteRepositories
        }
    }

    class RepositoriesViewHolder(val binding: FragmentRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root)
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
