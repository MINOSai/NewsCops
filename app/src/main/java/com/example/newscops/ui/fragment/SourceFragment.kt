package com.example.newscops.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newscops.R
import com.example.newscops.di.Injectable
import com.example.newscops.ui.adapter.ResultAdapter
import com.example.newscops.ui.viewmodel.SourceViewModel
import com.example.newscops.util.openUrl
import kotlinx.android.synthetic.main.fragment_source.*
import javax.inject.Inject

class SourceFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sourceViewModel: SourceViewModel

    private lateinit var resultAdapter: ResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_source, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sourceViewModel = ViewModelProviders.of(this, viewModelFactory).get(SourceViewModel::class.java)

        source_image_back.setOnClickListener {
            findNavController().popBackStack()
        }

        source_text_title.text = arguments?.get("title").toString()

        arguments?.get("source_id")?.let {
            initRv(it.toString())
        }


    }

    private fun initRv(id: String) {
        resultAdapter = ResultAdapter(arrayListOf()) {
            context?.openUrl(it.url)
        }

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL

        rv_source_news.layoutManager = linearLayoutManager
        rv_source_news.adapter = resultAdapter

        sourceViewModel.getSourceArticles(id)

        sourceViewModel.articlesList.observe(this, Observer {
            resultAdapter.updateList(ArrayList(it))
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = SourceFragment()
    }
}
