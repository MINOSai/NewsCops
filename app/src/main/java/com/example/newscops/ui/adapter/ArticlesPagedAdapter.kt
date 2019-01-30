package com.example.newscops.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newscops.R
import com.example.newscops.model.ArticleModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_article.view.*

class ArticlesPagedAdapter(
    private val listener: (ArticleModel) -> Unit
) : PagedListAdapter<ArticleModel, ArticlesPagedAdapter.ArticleViewHolder>(

    object : DiffUtil.ItemCallback<ArticleModel>() {
        override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel) = oldItem.url == newItem.url
        override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel) = oldItem == newItem
    }

) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, listener)
        }
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(articleModel: ArticleModel, listener: (ArticleModel) -> Unit) = with(itemView) {

            Picasso.get()
                .load(articleModel.urlToImage ?: "https://image.freepik.com/free-vector/newspaper-vector-illustration-logo-icon-clipart_7688-575.jpg")
                .resize(1000, 700)
                .centerCrop()
                .into(item_article_image)

            item_source_title.text = articleModel.title
            item_article_source.text = articleModel.source?.name
            item_article_date.text = articleModel.publishedAt

            item_article_layout.setOnClickListener {
                listener(articleModel)
            }

        }

    }

}