package com.example.newscops.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newscops.R
import com.example.newscops.di.Injectable
import com.example.newscops.ui.adapter.ResultAdapter
import com.example.newscops.ui.adapter.SourcesAdapter
import com.example.newscops.ui.viewmodel.SearchViewModel
import com.example.newscops.util.hide
import com.example.newscops.util.openUrl
import com.example.newscops.util.show
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.bundleOf
import javax.inject.Inject


class SearchFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var searchViewModel: SearchViewModel

    private lateinit var sourcesAdapter: SourcesAdapter
    private lateinit var resultAdapter: ResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        initSourceRv()
        initResultRv()

        search_button_back.setOnClickListener {
            findNavController().popBackStack()
        }

        search_button_clear.setOnClickListener {
            search_input_query.text = null
        }

        search_input_query.doOnTextChanged { text, start, count, after ->
            if (text.isNullOrEmpty()) {
                search_layout_sources.show()
                search_button_clear.hide()
                rv_results.hide()
            } else {

                searchViewModel.populateSearchResults(text.toString())

                search_layout_sources.hide()
                search_button_clear.show()
                rv_results.show()
            }
        }

        search_input_query.text = null
    }

    private fun initResultRv() {
        resultAdapter = ResultAdapter(arrayListOf()) {
            context?.openUrl(it.url)
        }

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL

        rv_results.layoutManager = linearLayoutManager
        rv_results.adapter = resultAdapter

        searchViewModel.searchResults.observe(this, Observer {
            resultAdapter.updateList(ArrayList(it))
        })
    }

    private fun initSourceRv() {
        sourcesAdapter = SourcesAdapter(arrayListOf()) {
//            context?.openUrl(it.url ?: "")
            var bundle = bundleOf(
                "title" to it.name,
                "source_id" to it.id
            )
            findNavController().navigate(R.id.action_searchFragment_to_sourceFragment, bundle)
        }

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL

        rv_sources.layoutManager = linearLayoutManager
        rv_sources.adapter = sourcesAdapter

        searchViewModel.getAllSources().observe(this, Observer {
            sourcesAdapter.updateList(ArrayList(it))
        })
    }


    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
