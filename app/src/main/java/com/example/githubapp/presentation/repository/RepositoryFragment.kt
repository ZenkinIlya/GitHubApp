package com.example.githubapp.presentation.repository

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentRepositoryBinding

class RepositoryFragment : Fragment(R.layout.fragment_repository) {

    lateinit var binding: FragmentRepositoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

    }
}