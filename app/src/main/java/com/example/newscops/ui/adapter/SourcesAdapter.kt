package com.example.newscops.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newscops.R
import com.example.newscops.model.SourceModel
import kotlinx.android.synthetic.main.item_source.view.*

class SourcesAdapter(
    var sources : ArrayList<SourceModel>,
    var listener: (SourceModel) -> Unit
) : RecyclerView.Adapter<SourcesAdapter.SourcesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourcesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_source, parent, false)
        return SourcesViewHolder(view)
    }

    override fun getItemCount() = sources.size

    override fun onBindViewHolder(holder: SourcesViewHolder, position: Int) =
        holder.bind(sources[position], listener)

    fun updateList(sources: ArrayList<SourceModel>) {
        this.sources = sources
        notifyDataSetChanged()
    }


    class SourcesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(source: SourceModel, listener: (SourceModel) -> Unit) = with(itemView) {

            item_source_title.text = source.name
            item_source_description.text = source.description

            item_source_layout.setOnClickListener {
                listener(source)
            }

        }
    }

}