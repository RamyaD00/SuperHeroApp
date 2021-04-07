package com.example.superheroapp.view

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.NetworkUtils
import com.example.superheroapp.R
import com.example.superheroapp.model.HeroResponse
import com.example.superheroapp.model.HerosResponse
import com.example.superheroapp.viewModel.HerosViewModel
import com.example.superheroapp.viewModel.ViewModelUtil

class HerosListingFragment : Fragment(R.layout.listing_layout), SearchView.OnQueryTextListener {


    lateinit var viewModel: HerosViewModel

    private var dataList : List<HerosResponse> = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelUtil.getAppViewModel(requireActivity() as AppCompatActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchHerosList()

        val text = view.findViewById<TextView>(R.id.text)

        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)

        val search = toolbar.menu.findItem(R.id.search).actionView as SearchView

        search.setOnQueryTextListener(this)

        viewModel.viewState.observe(viewLifecycleOwner,Observer{ state ->

            when(state.heroData){

                HeroResponse.DefaultError -> {

                    if(NetworkUtils.isConnected()){
                        text.text = getString(R.string.wrong)
                    }else{
                        text.text = getString(R.string.connect)
                    }
                }
                HeroResponse.RequestInFlight -> {
                    text.text = getString(R.string.fetch_data)
                }
                is HeroResponse.Success -> {

                    toolbar.menu.findItem(R.id.search).isVisible = true

                    val recyclerView = view.findViewById<RecyclerView>(R.id.heros_list)
                    val adapter = HerosListingAdapter(state.heroData.HerosData,empty = {
                        if(it) {
                            recyclerView.visibility = View.GONE
                            text.text = getString(R.string.no_heroes)
                        }else{
                            recyclerView.visibility = View.VISIBLE
                        }
                    })

                    recyclerView.visibility = View.VISIBLE
                    dataList = state.heroData.HerosData

                    if(recyclerView.adapter == null){
                        recyclerView.adapter = adapter
                        val span = if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 2 else 1
                        val layoutManager = GridLayoutManager(requireContext(),span)
                        layoutManager.orientation = RecyclerView.VERTICAL
                        recyclerView.layoutManager = layoutManager
                    }

                }
                HeroResponse.YetToStart -> {

                }
            }

        })


    }

    override fun onStop() {
        super.onStop()
        viewModel.viewState.removeObservers(viewLifecycleOwner)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(findNavController().currentDestination!!.id == R.id.HerosListingFragment && dataList.isNotEmpty()) {
            val recyclerView = requireView().findViewById<RecyclerView>(R.id.heros_list)
            if (recyclerView.adapter != null) {
                (recyclerView.adapter as HerosListingAdapter).filterSearch(newText, dataList)
            }
        }
        return false
    }
}