package com.codecool.todoapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codecool.todoapp.R
import com.codecool.todoapp.data.viewmodel.ToDoViewModel
import com.codecool.todoapp.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private val listAdapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView = view.recycler_view
        recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }

        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            listAdapter.setData(data)
        })

        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseViews(it)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        setHasOptionsMenu(true)
        return view
    }

    private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            view?.no_data_imageview?.visibility = View.VISIBLE
            view?.no_data_textview?.visibility = View.VISIBLE
        } else {
            view?.no_data_imageview?.visibility = View.INVISIBLE
            view?.no_data_textview?.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) {
            confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                getString(R.string.successfully_removed_everything),
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.alert_delete_all_title))
        builder.setMessage(getString(R.string.confirm_delete_all))
        builder.create().show()
    }

}