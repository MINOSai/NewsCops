package com.example.newscops.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newscops.R
import com.example.newscops.model.ArticleModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_search_result.view.*

class ResultAdapter(
    var articles: ArrayList<ArticleModel>,
    var listener: (ArticleModel) -> Unit
) : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
        return ResultViewHolder(view)
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) = holder.bind(articles[position], listener)

    fun updateList(articles: ArrayList<ArticleModel>) {
        this.articles = articles
        notifyDataSetChanged()
    }


    class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(article: ArticleModel, listener: (ArticleModel) -> Unit) = with(itemView) {

            Picasso.get()
                .load(article.urlToImage ?: "https://image.freepik.com/free-vector/newspaper-vector-illustration-logo-icon-clipart_7688-575.jpg")
                .resize(500,500)
                .centerCrop()
                .into(item_result_image)
            item_result_body.text = article.title

            item_result_layout.setOnClickListener {
                listener(article)
            }

        }

    }

}