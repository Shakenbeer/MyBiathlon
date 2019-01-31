package com.shakenbeer.biathlon.ui

import android.view.View
import androidx.databinding.ViewDataBinding
import com.shakenbeer.biathlon.BR

class BaseBindingViewHolder(private val binding: ViewDataBinding,
                            private val clickListener: ClickListener)
    : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    interface ClickListener {
        fun onViewClick(position: Int)
    }

    init {
        binding.root.setOnClickListener(this)
    }

    fun bind(obj: Any) {
        binding.setVariable(BR.obj, obj)
        binding.executePendingBindings()
    }

    override fun onClick(v: View) {
        clickListener.onViewClick(adapterPosition)
    }
}
