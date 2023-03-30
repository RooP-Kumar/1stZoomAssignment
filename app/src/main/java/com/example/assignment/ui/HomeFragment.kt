package com.example.assignment.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.assignment.R
import com.example.assignment.data.entity.Repo
import com.example.assignment.databinding.FragmentHomeBinding
import com.example.assignment.ui.adapter.RecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<NetworkViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        mainUI()
        setHasOptionsMenu(true)
        requireActivity().title = getString(R.string.home)
        return binding.root
    }

    private fun mainUI() {
        binding.gotoaddreposcreen.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addRepo)
        }

        val rv = binding.recyclerView
        val manager = LinearLayoutManager(requireContext())
        manager.orientation = LinearLayoutManager.VERTICAL
        rv.layoutManager = manager
        val adapter = RecyclerViewAdapter(itemClickListener = object: RecyclerViewAdapter.OnItemClickListener {
            override fun onClick(repo: Repo) {
                if(repo.html_url.isNotEmpty()){
                    try {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(repo.html_url)
                        startActivity(
                            intent
                        )
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(requireContext(), "No installed web browser found $e", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(requireContext(), "No url found", Toast.LENGTH_SHORT).show()
                }
            }
        }, shareClickListener = object : RecyclerViewAdapter.OnShareClickListener{
            override fun onClick(repo: Repo) {
                if(repo.html_url.isNotEmpty()){
                    try {
                        val text = "Repository name : ${repo.name}\nRepository link : ${repo.html_url}"
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain"
                        intent.putExtra(Intent.EXTRA_TEXT, text)
                        startActivity(
                            Intent.createChooser(intent, "share with")
                        )
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(requireContext(), "No installed web browser found $e", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(requireContext(), "No url found", Toast.LENGTH_SHORT).show()
                }
            }
        })
        rv.adapter = adapter

        viewModel.repos.observe(requireActivity()) {
            Log.d("CheckingData", "mainUI: ${it.size}")
            if(it.isNotEmpty()) {
                binding.showhidell.visibility = View.GONE
                adapter.addData(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_my_button){
            findNavController().navigate(R.id.action_homeFragment_to_addRepo)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}