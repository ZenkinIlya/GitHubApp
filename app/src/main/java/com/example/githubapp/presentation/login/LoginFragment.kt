package com.example.githubapp.presentation.login

import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.githubapp.R
import com.example.githubapp.componentManager
import com.example.githubapp.data.SignInGoogleContract
import com.example.githubapp.data.SignInGoogleHandler
import com.example.githubapp.databinding.FragmentLoginBinding
import com.example.githubapp.presentation.repositoriesList.ListRepositoryFragment.Companion.ARG_TYPE_AUTH
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.material.progressindicator.BaseProgressIndicator.HIDE_OUTWARD
import com.google.android.material.progressindicator.BaseProgressIndicator.SHOW_OUTWARD
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.fragment_login), LoginView {

    private lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var signInGoogleHandler: SignInGoogleHandler

    private lateinit var loginPresenter: LoginPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        requireContext().componentManager.appComponent.inject(this)

        initActions()

        loginPresenter = LoginPresenter(this)
        signInGoogleHandler.initActivityResultLauncher(
            getContent = registerForActivityResult(SignInGoogleContract()) { signInGoogleWrapper ->
                if (signInGoogleWrapper == null) {
                    Toast.makeText(context, "Unknown error", Toast.LENGTH_SHORT).show()
                } else {
                    when (signInGoogleWrapper.statusCode) {
                        CommonStatusCodes.SUCCESS -> letTheUserIn()
                        else -> Toast.makeText(
                            context,
                            signInGoogleHandler.getStatusMessage(signInGoogleWrapper.statusCode),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })

        //Request data by FragmentResult API
/*        val REQUEST_CODE = "REQUEST_TYPE_AUTH"
        parentFragmentManager.setFragmentResult(REQUEST_CODE, bundleOf(ARG_TYPE_AUTH to TypeAuth.GOOGLE))
        findNavController().navigate(...)  //Navigate to fragment which has FragmentListener*/

        //Response listener from another fragments by FragmentResult API
/*        parentFragmentManager.setFragmentResultListener(REQUEST_CODE, viewLifecycleOwner) { REQUEST_CODE, result ->
            val text = result.getString(ARG_TYPE_AUTH)
            binding.signInGithub.text = text
        }*/

        //Navigate by direction. Navigation safeargs
        /** SafeArgs can request data only one way*/
/*        val typeAuth = TypeAuth.GOOGLE
        val direction = LoginFragmentDirections.actionLoginFragmentToListRepositoryFragment(typeAuth)
        findNavController().navigate(direction)*/
    }

    override fun onStart() {
        super.onStart()
        //Check if user authorized by Google
        if (signInGoogleHandler.isClientSigned()) {
            findNavController()
                .navigate(
                    R.id.action_loginFragment_to_listRepositoryFragment,
                    bundleOf(ARG_TYPE_AUTH to TypeAuth.GOOGLE)
                )
        }

        //TODO Check if user authorized by Github
    }

    private fun initActions() {
        binding.signInGithub.setOnClickListener {
            loginPresenter.onClickSignInGithub()
        }

        binding.signInGoogle.setSize(SignInButton.SIZE_STANDARD)
        binding.signInGoogle.setOnClickListener {
            loginPresenter.onClickSignInGoogle()
        }

        binding.signInWithoutAuth.setOnClickListener {
        }
    }

    override fun showLoading(show: Boolean) {
        if (show) {
            binding.progressIndicator.showAnimationBehavior = SHOW_OUTWARD
            binding.progressIndicator.visibility = VISIBLE
        } else {
            binding.progressIndicator.setVisibilityAfterHide(INVISIBLE)
            binding.progressIndicator.hideAnimationBehavior = HIDE_OUTWARD
            binding.progressIndicator.hide()
        }

    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    override fun letTheUserIn() {
        findNavController()
            .navigate(
                R.id.action_loginFragment_to_listRepositoryFragment,
                bundleOf(ARG_TYPE_AUTH to TypeAuth.GOOGLE)
            )
    }

    override fun letTheUnknownUserIn() {
        findNavController()
            .navigate(
                R.id.action_loginFragment_to_listRepositoryFragment,
                bundleOf(ARG_TYPE_AUTH to TypeAuth.WITHOUT_AUTH)
            )
    }

    override fun signInGoogle() {
        signInGoogleHandler.tryToSignInGoogle()
    }
}