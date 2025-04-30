package com.argel.weathertraker.ui.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.argel.weathertraker.databinding.ItemLocationBinding
import com.argel.weathertraker.presentation.models.SuggestModel

class SuggestionsAdapter(var onSuggestionClicked: (SuggestModel) -> Unit = {}):
    ListAdapter<SuggestModel, SuggestionsAdapter.SuggestionViewHolder>(DIFF) {

        companion object {
            val DIFF = object : DiffUtil.ItemCallback<SuggestModel>() {
                override fun areItemsTheSame(oldItem: SuggestModel, newItem: SuggestModel) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: SuggestModel, newItem: SuggestModel) =
                    oldItem == newItem
            }
        }

        inner class SuggestionViewHolder(val binding: ItemLocationBinding) : RecyclerView.ViewHolder(binding.root) {

            fun bind(item: SuggestModel) {
                binding.tvTitle.text = item.name
                binding.tvDescription.text = item.description
                itemView.setOnClickListener {
                    onSuggestionClicked(item)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SuggestionViewHolder (
            ItemLocationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
            holder.bind(getItem(position))
        }
}