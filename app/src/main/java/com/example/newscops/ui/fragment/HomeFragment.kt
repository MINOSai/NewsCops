package com.example.newscops.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newscops.R
import com.example.newscops.di.Injectable
import com.example.newscops.ui.adapter.ArticlesPagedAdapter
import com.example.newscops.ui.viewmodel.MainViewModel
import com.example.newscops.util.openUrl
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mainViewModel: MainViewModel

    private lateinit var adapter: ArticlesPagedAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        home_refreshlayout.isRefreshing = true

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        initRecyclerView()
        populateRecyclerView()

        home_button_search.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        home_refreshlayout.setOnRefreshListener {
//            Toast.makeText(context, "Refresh", Toast.LENGTH_SHORT).show()
            mainViewModel.refreshHeadlines()
        }
    }

    private fun initRecyclerView() {
        adapter = ArticlesPagedAdapter {
            context?.openUrl(it.url)
        }

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL

        home_rv_articles.layoutManager = linearLayoutManager
        home_rv_articles.adapter = adapter
    }

    private fun populateRecyclerView() {
        mainViewModel.getArticles().observe(this, Observer { pagedList ->
            var asdf = pagedList.snapshot()
            adapter.submitList(pagedList)
            home_rv_articles.smoothScrollToPosition(0)
            home_refreshlayout.isRefreshing = false
        })
    }


    companion object {
        fun newInstance() = HomeFragment()
    }
}
