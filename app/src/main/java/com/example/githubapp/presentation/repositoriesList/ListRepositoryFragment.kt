package com.example.githubapp.presentation.repositoriesList

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.githubapp.GithubApp
import com.example.githubapp.R
import com.example.githubapp.componentManager
import com.example.githubapp.data.SignInGoogleContract
import com.example.githubapp.data.SignInGoogleHandler
import com.example.githubapp.databinding.FragmentListRepositoryBinding
import javax.inject.Inject

class ListRepositoryFragment : Fragment(R.layout.fragment_list_repository) {

    private lateinit var binding: FragmentListRepositoryBinding

    //Delegate navArgs()
    private val args: ListRepositoryFragmentArgs by navArgs()

    @Inject
    lateinit var signInGoogleHandler: SignInGoogleHandler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListRepositoryBinding.bind(view)
        requireContext().componentManager.appComponent.inject(this)

        setHasOptionsMenu(true)

        val typeAuthByNavigationSafeArgs2 = args.typeAuth
        Toast.makeText(context, typeAuthByNavigationSafeArgs2.name, Toast.LENGTH_SHORT).show()

        initActions()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list_repository, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_exit -> {
                signInGoogleHandler.signOut()
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initActions() {
        binding.tabLayout.setOnClickListener {
            Toast.makeText(context, "tabLayout", Toast.LENGTH_SHORT).show()
        }
        binding.viewPager.setOnClickListener {
            Toast.makeText(context, "viewPager", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        const val ARG_TYPE_AUTH = "type_auth"
    }
}