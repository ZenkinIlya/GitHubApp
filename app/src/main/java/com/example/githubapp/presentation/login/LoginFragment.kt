package com.example.githubapp.presentation.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.githubapp.R
import com.example.githubapp.componentManager
import com.example.githubapp.data.signIn.SignInGoogleContract
import com.example.githubapp.data.signIn.SignInGoogleHandler
import com.example.githubapp.databinding.FragmentLoginBinding
import com.google.android.gms.common.SignInButton
import com.google.android.material.progressindicator.BaseProgressIndicator.HIDE_OUTWARD
import com.google.android.material.progressindicator.BaseProgressIndicator.SHOW_OUTWARD
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider


class LoginFragment : MvpAppCompatFragment(R.layout.fragment_login), LoginView {

    private lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var presenterProvider: Provider<LoginPresenter>
    private val loginPresenter by moxyPresenter { presenterProvider.get() }

    @Inject
    lateinit var signInGoogleHandler: SignInGoogleHandler

    override fun onAttach(context: Context) {
        Timber.i("onAttach()")
        requireContext().componentManager.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("onCreateView()")
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated()")

        initActions()

        signInGoogleHandler.initActivityResultLauncher(
            getContent = registerForActivityResult(SignInGoogleContract()) { signInGoogleWrapper ->
                loginPresenter.onGetSignInGoogleWrapperFromContract(signInGoogleWrapper)
            })
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart()")
        //Check if user authorized by Google
        if (signInGoogleHandler.isClientSigned()) {
            navigateToRepositories()
        }
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause()")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop()")
    }

    override fun onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu()
        Timber.i("onDestroyOptionsMenu()")
    }

    override fun onDestroyView() {
        signInGoogleHandler.unregisterActivityResultLauncher()
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

    private fun initActions() {
        binding.signInGoogle.setSize(SignInButton.SIZE_STANDARD)
        binding.signInGoogle.setOnClickListener {
            loginPresenter.onClickSignInGoogle()
        }

        binding.signInWithoutAuth.setOnClickListener {
            loginPresenter.onClickSignInWithoutAuth()
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

    override fun showErrorSignInGoogle(statusMessage: Int?) {
        Toast.makeText(
            context,
            signInGoogleHandler.getStatusMessage(statusMessage),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun navigateToRepositories() {
        findNavController()
            .navigate(
                R.id.action_loginFragment_to_listRepositoryFragment
            )
    }

    override fun signInGoogle() {
        signInGoogleHandler.tryToSignInGoogle()
    }
}