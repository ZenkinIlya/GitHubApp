package com.example.githubapp.presentation.repositoriesList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentListRepositoryBinding

class ListRepositoryFragment : Fragment(R.layout.fragment_list_repository) {

    private lateinit var binding: FragmentListRepositoryBinding
    private val args: ListRepositoryFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListRepositoryBinding.bind(view)

//        val typeAuth = ListRepositoryFragmentArgs.fromBundle(requireArguments()).typeAuth
        val typeAuth = args.typeAuth
        Toast.makeText(context, typeAuth.name, Toast.LENGTH_SHORT).show()

/*        binding.exit.setOnClickListener {
            val text = binding.result.text.toString()
            parentFragmentManager.setFragmentResult(RESULT, bundleOf(TEXT to text))
            findNavController().popBackStack()
        }*/
    }

    companion object {

        const val ARG_TYPE_AUTH = "type_auth"
    }
}