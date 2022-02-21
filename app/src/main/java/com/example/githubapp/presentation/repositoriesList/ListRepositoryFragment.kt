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

        //Simple getArguments
/*        val typeAuthBySimple = requireArguments().getString(ARG_TYPE_AUTH)
        Toast.makeText(context, typeAuthBySimple, Toast.LENGTH_SHORT).show()*/

//        val typeAuthByNavigationSafeArgs1 = ListRepositoryFragmentArgs.fromBundle(requireArguments()).typeAuth

        val typeAuthByNavigationSafeArgs2 = args.typeAuth
        Toast.makeText(context, typeAuthByNavigationSafeArgs2.name, Toast.LENGTH_SHORT).show()

        //return data to parentFragment
/*        binding.exit.setOnClickListener {
            val text = binding.result.text.toString()
            parentFragmentManager.setFragmentResult(RESULT, bundleOf(TEXT to text))
            findNavController().popBackStack()
        }*/

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