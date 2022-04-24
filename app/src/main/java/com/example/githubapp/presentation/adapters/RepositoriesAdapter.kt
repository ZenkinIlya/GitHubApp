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

            nameRepository.text = repository.name
            login.text = repository.owner.login

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

            description.text = repository.description
            forks.text = repository.forks_count.toString()
            stars.text = repository.stars_count.toString()
            dateOfCreation.text = repository.dateOfCreation

            if (authorized) {
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

    fun removeRepository(repository: Repository) {
        val toMutableList = repositories.toMutableList()
        toMutableList.remove(repository)
        repositories = toMutableList
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
