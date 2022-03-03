package com.example.githubapp.presentation.githubRepositories

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.githubapp.R
import com.example.githubapp.componentManager
import com.example.githubapp.data.signIn.SignInGoogleHandler
import com.example.githubapp.databinding.FragmentRepositoriesBinding
import com.example.githubapp.models.SearchViewModel
import com.example.githubapp.presentation.githubRepositories.pageAdapter.RepositoriesPageAdapter
import com.google.android.material.tabs.TabLayoutMediator
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


class RepositoriesFragment : MvpAppCompatFragment(R.layout.fragment_repositories),
    RepositoriesView {

    private lateinit var binding: FragmentRepositoriesBinding

    @Inject
    lateinit var presenterProvider: Provider<RepositoriesPresenter>
    private val repositoriesPresenter by moxyPresenter { presenterProvider.get() }

    @Inject
    lateinit var signInGoogleHandler: SignInGoogleHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().componentManager.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoriesBinding.bind(view)

        setHasOptionsMenu(true)

        initActions()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list_repository, menu)

        //Search
        val findItem = menu.findItem(R.id.menu_search)
        val searchView = findItem.actionView as SearchView
        searchView.queryHint = "Type here to search"

        val searchViewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchViewModel.setQuery(newText)
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_exit -> {
                if (signInGoogleHandler.isClientSigned()) signInGoogleHandler.signOut()
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading(show: Boolean) {
        TODO("Not yet implemented")
    }

    override fun showError(error: String) {
        TODO("Not yet implemented")
    }

    override fun displaySavedRepositories(flag: Boolean) {
        val repositoriesPageAdapter =
            RepositoriesPageAdapter(requireActivity().supportFragmentManager, lifecycle, flag)
        binding.viewPager2.adapter = repositoriesPageAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.search)
                else -> tab.text = getString(R.string.saved)
            }
        }.attach()
    }

    private fun initActions() {
        binding.tabLayout.setOnClickListener {
            Toast.makeText(context, "tabLayout", Toast.LENGTH_SHORT).show()
        }
        binding.viewPager2.setOnClickListener {
            Toast.makeText(context, "viewPager", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val ARG_TYPE_AUTH = "type_auth"
    }
}
