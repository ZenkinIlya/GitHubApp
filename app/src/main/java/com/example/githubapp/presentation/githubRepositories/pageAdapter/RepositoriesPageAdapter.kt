package com.example.githubapp.presentation.githubRepositories.pageAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubapp.presentation.githubRepositories.RepositoriesFragment
import com.example.githubapp.presentation.searchRepositories.RepositoriesSearcherFragment
import com.example.githubapp.presentation.savedRepositories.SavedRepositoriesFragment

class RepositoriesPageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private var authorized: Boolean
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return if (authorized) 2 else 1
    }

    override fun createFragment(position: Int): Fragment {
        return if (authorized){
            return when (position) {
                0 ->  RepositoriesSearcherFragment()
                else ->  SavedRepositoriesFragment()
            }
        }else{
            RepositoriesSearcherFragment()
        }
    }
}