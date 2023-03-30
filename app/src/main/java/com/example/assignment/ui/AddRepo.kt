package com.example.assignment.ui

import android.content.Context
import android.hardware.input.InputManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.assignment.R
import com.example.assignment.Status
import com.example.assignment.databinding.FragmentAddRepoBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRepo : Fragment() {
    private lateinit var binding: FragmentAddRepoBinding
    private val viewModel by viewModels<NetworkViewModel>()
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddRepoBinding.inflate(layoutInflater)
        navController = findNavController()
        setHasOptionsMenu(true)
        mainUI()
        return binding.root
    }

    private fun mainUI(){
        NavigationUI.setupActionBarWithNavController(requireActivity() as AppCompatActivity, navController)
        requireActivity().title = getString(R.string.add_repo)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)

        binding.repoTF.setOnEditorActionListener { repo, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                getRepo()
                return@setOnEditorActionListener true
            }
            false
        }


        binding.addrepotoroom.setOnClickListener {
            getRepo()
        }

        viewModel.addingStatus.observe(requireActivity()) {
            it?.let {
                when(it.status) {
                    Status.LOADING -> {
                        binding.progressbarlayout.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.repoTF.setText("")
                        binding.ownerTF.setText("")
                        binding.progressbarlayout.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        binding.progressbarlayout.visibility = View.GONE
                        Snackbar.make(requireView(), it.msg, Snackbar.LENGTH_SHORT).show()
                    }
                    Status.IDLE -> {
                        binding.progressbarlayout.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun getRepo() {
        val manager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view?.windowToken, 0)

        val owner = binding.ownerTF.text.toString().trim()
        val repo = binding.repoTF.text.toString().trim()
        if (owner.isEmpty()){
            binding.ownerLayout.error = "Field can not be empty"
        } else if(repo.isEmpty()){
            binding.ownerTF.error = "Field can not be empty"

        } else {
            viewModel.getRepo(owner, repo, requireView())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home ->{
                navController.popBackStack()
                true
            }
            else -> {
                return NavigationUI.onNavDestinationSelected(item, navController)
            }
        }
    }

}