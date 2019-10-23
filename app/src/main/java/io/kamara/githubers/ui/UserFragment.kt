package io.kamara.githubers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import io.kamara.githubers.R
import io.kamara.githubers.binding.bindImageFromUrl
import io.kamara.githubers.databinding.UserFragmentBinding
import io.kamara.githubers.di.Injectable
import io.kamara.githubers.model.User
import io.kamara.githubers.viewmodels.UserViewModel
import io.kamara.githubers.model.Result
import javax.inject.Inject

class UserFragment : BaseFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val userViewModel: UserViewModel by viewModels { viewModelFactory }

    private val args: UserFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dateBinding = DataBindingUtil.inflate<UserFragmentBinding>(
            inflater,
            R.layout.user_fragment,
            container,
            false
        )

        userViewModel.setLogin(args.login)

        subscribeUi(dateBinding)

        return dateBinding.root
    }


    private fun subscribeUi(binding: UserFragmentBinding) {
        userViewModel.user.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    result.data?.let { bindView(binding, it) }
                }
                //TODO: Move error handling to [UserFragmentBinding]
                Result.Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                Result.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE

                    val message = result.message ?: getString(R.string.unknown_error)
                    Snackbar.make(binding.container, message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun bindView(binding: UserFragmentBinding, newUser: User) {
        newUser.apply {
            bindImageFromUrl(binding.avatar, avatarUrl)
            binding.name.text = name
            binding.login.text = login
            binding.reposUrl.text = reposUrl
        }

    }
}
