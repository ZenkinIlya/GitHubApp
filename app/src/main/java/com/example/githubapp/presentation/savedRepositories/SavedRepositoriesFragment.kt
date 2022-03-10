package com.example.githubapp.presentation.savedRepositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentSavedRepositoriesBinding
import com.example.githubapp.models.SearchViewModel
import com.example.githubapp.presentation.githubRepositories.RepositoriesFragment
import moxy.MvpAppCompatFragment
import timber.log.Timber

class SavedRepositoriesFragment() : MvpAppCompatFragment(R.layout.fragment_saved_repositories) {

    lateinit var binding: FragmentSavedRepositoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy()")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchViewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]
        searchViewModel.getQuery().observe(viewLifecycleOwner) {
//            Toast.makeText(context, "Saved = $it", Toast.LENGTH_SHORT).show()
        }
    }
}