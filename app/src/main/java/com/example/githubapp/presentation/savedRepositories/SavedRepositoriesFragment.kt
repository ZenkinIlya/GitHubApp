package com.example.githubapp.presentation.savedRepositories

import android.content.Context
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.i("onAttach()")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("onCreateView()")
        binding = FragmentSavedRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated()")
        val searchViewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]
        searchViewModel.getQuery().observe(viewLifecycleOwner) {
//            Toast.makeText(context, "Saved = $it", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart()")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume()")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause()")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.i("onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.i("onDetach()")
    }
}