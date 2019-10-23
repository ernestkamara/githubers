package io.kamara.githubers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.kamara.githubers.R
import io.kamara.githubers.databinding.UserListFragmentBinding
import io.kamara.githubers.di.Injectable
import io.kamara.githubers.model.Result
import io.kamara.githubers.viewmodels.UserListViewModel
import javax.inject.Inject

class UserListFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val userListViewModel: UserListViewModel by viewModels { viewModelFactory }


    private var adapter = UserListAdapter { user ->
        findNavController().navigate(UserListFragmentDirections.showUser(user.login))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dateBinding = DataBindingUtil.inflate<UserListFragmentBinding>(
            inflater,
            R.layout.user_list_fragment,
            container,
            false
        )

        dateBinding.userList.adapter = adapter
        dateBinding.userList.layoutManager = LinearLayoutManager(context)
        dateBinding.userList.adapter

        subscribeUi(dateBinding) //TODO: Move binging to [UserListFragmentBinding]

        return dateBinding.root
    }

    private fun subscribeUi(dateBinding: UserListFragmentBinding) {
        userListViewModel.users.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    dateBinding.progressBar.visibility = View.GONE
                    adapter.submitList(result.data ?: emptyList())
                }
                Result.Status.LOADING -> dateBinding.progressBar.visibility = View.VISIBLE
                Result.Status.ERROR -> {
                    dateBinding.progressBar.visibility = View.GONE
                    Snackbar.make(dateBinding.container, result.message!!, Snackbar.LENGTH_LONG)
                        .show()

                }
            }
        })
    }
}