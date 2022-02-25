package com.example.githubapp.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.githubapp.R
import com.example.githubapp.componentManager
import com.example.githubapp.data.SignInGoogleContract
import com.example.githubapp.data.SignInGoogleHandler
import com.example.githubapp.databinding.FragmentLoginBinding
import com.example.githubapp.presentation.repositoriesList.ListRepositoryFragment.Companion.ARG_TYPE_AUTH
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.material.progressindicator.BaseProgressIndicator.HIDE_OUTWARD
import com.google.android.material.progressindicator.BaseProgressIndicator.SHOW_OUTWARD
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


class LoginFragment : MvpAppCompatFragment(), LoginView {

    private lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var signInGoogleHandler: SignInGoogleHandler

    @Inject
    lateinit var presenterProvider: Provider<LoginPresenter>

    private val loginPresenter: LoginPresenter by moxyPresenter { presenterProvider.get() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireContext().componentManager.appComponent.inject(this)

        initActions()

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