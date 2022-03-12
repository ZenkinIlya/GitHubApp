package com.example.githubapp.presentation.searchRepositories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentRepositoryBinding
import com.example.githubapp.models.repository.Repository

class RepositoriesSearcherAdapter :
    RecyclerView.Adapter<RepositoriesSearcherAdapter.RepositoriesViewHolder>() {

    var repositories: List<Repository> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentRepositoryBinding.inflate(inflater, parent, false)

        return RepositoriesViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RepositoriesViewHolder, position: Int) {
        val repository = repositories[position]
        with(holder.binding){
            nameRepository.text = repository.name
            author.text = repository.owner.author

            if (repository.owner.avatar?.isNotBlank() == true){
                Glide.with(avatarImageView.context)
                    .load(repository.owner.avatar)
                    .circleCrop()
                    .placeholder(R.drawable.ic_account)
                    .error(R.drawable.ic_account)
                    .into(avatarImageView)
            }else{
                Glide.with(avatarImageView.context).clear(avatarImageView)
                avatarImageView.setImageResource(R.drawable.ic_account)
            }

            description.text = repository.description
            forks.text = repository.forks_count.toString()
            stars.text = repository.stars_count.toString()
            dateOfCreation.text = repository.dateOfCreation

            if (repository.favorite){
                Glide.with(favoriteImageView.context)
                    .load(R.drawable.ic_favorite)
                    .placeholder(R.drawable.ic_favorite_border)
                    .error(R.drawable.ic_favorite_border)
                    .into(favoriteImageView)
            }else{
                Glide.with(favoriteImageView.context).clear(favoriteImageView)
                favoriteImageView.setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }

    override fun getItemCount(): Int = repositories.size

    class RepositoriesViewHolder(val binding: FragmentRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}
