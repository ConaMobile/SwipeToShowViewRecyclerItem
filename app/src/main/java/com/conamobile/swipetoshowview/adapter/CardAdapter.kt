package com.conamobile.swipetoshowview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.conamobile.swipetoshowview.databinding.CardItemBinding
import com.conamobile.swipetoshowview.model.Card
import java.lang.ref.WeakReference

class CardAdapter : RecyclerView.Adapter<CardAdapter.VH>() {

    private var dif = AsyncListDiffer(this, ITEM_DIFF)
    lateinit var onDeleteClick: (() -> Unit)

    inner class VH(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val view = WeakReference(binding.root)
        fun bind() {
            val card = dif.currentList[adapterPosition]
            view.get()?.let {
                it.setOnClickListener {
                    if (view.get()?.scrollX != 0) {
                        view.get()?.scrollTo(0, 0)
                    }
                }
            }

            binding.apply {
                title.text = ""

                deleteTv.setOnClickListener {
                    onDeleteClick.invoke()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()

    override fun getItemCount(): Int = dif.currentList.size

    fun submitList(list: List<Card>) {
        dif.submitList(list)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean =
                oldItem.title == newItem.title
        }
    }
}