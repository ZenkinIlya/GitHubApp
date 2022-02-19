package com.example.githubapp.presentation.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.githubapp.R
import com.example.githubapp.data.SignInByGoogleContract
import com.example.githubapp.databinding.FragmentLoginBinding
import com.example.githubapp.presentation.repositoriesList.ListRepositoryFragment.Companion.ARG_TYPE_AUTH
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton

class LoginFragment : Fragment(R.layout.fragment_login), LoginView {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var loginPresenter: LoginPresenter

    private val getContent = registerForActivityResult(SignInByGoogleContract()) {
        if (it != null){
            letTheUserIn()
        }else{
            Toast.makeText(context, "GoogleSignInAccount = null", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        initActions()
        initGoogleSignInClient()

        loginPresenter = LoginPresenter(this)
        //Response listener from another fragments
/*        parentFragmentManager.setFragmentResultListener(RESULT, viewLifecycleOwner) { _, result ->
            val text = result.getString(TEXT)
            binding.signInByGithub.text = text
        }*/
    }

    override fun onStart() {
        super.onStart()
        //Check if user authorized by Google
        if (GoogleSignIn.getLastSignedInAccount(requireActivity()) != null){
            findNavController()
                .navigate(R.id.action_loginFragment_to_listRepositoryFragment,
                    bundleOf(ARG_TYPE_AUTH to TypeAuth.GOOGLE))
        }

        //TODO Check if user authorized by Github
    }

    private fun initActions() {
        binding.signInByGithub.setOnClickListener {
            loginPresenter.onClickSignInGithub()
        }

        binding.signInByGoogle.setSize(SignInButton.SIZE_STANDARD)
        binding.signInByGoogle.setOnClickListener {
            loginPresenter.onClickSignInGoogle()
        }
    }

    private fun openListRepository() {
//        val direction = LoginFragmentDirections.actionLoginFragmentToListRepositoryFragment(typeAuth)
//        findNavController().navigate(direction)
    }

    override fun showLoading(show: Boolean) {
        if (show) binding.progressIndicator.showAnimationBehavior
        else binding.progressIndicator.hideAnimationBehavior

    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    override fun letTheUserIn() {
        findNavController()
            .navigate(R.id.action_loginFragment_to_listRepositoryFragment,
                bundleOf(ARG_TYPE_AUTH to TypeAuth.GOOGLE))
    }

    override fun letTheUnknownUserIn() {
        findNavController()
            .navigate(R.id.action_loginFragment_to_listRepositoryFragment,
                bundleOf(ARG_TYPE_AUTH to TypeAuth.WITHOUT_AUTH))
    }

    override fun signInGoogle() {
        tryToSignInByGoogle()
    }

    private fun initGoogleSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
    }

    private fun tryToSignInByGoogle() {
        getContent.launch(googleSignInClient)
    }
}