package org.wordpress.android.ui.stats.refresh.lists.sections.insights.management

import android.support.v7.util.DiffUtil
import android.support.v7.util.DiffUtil.Callback
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.ViewGroup
import org.wordpress.android.ui.stats.refresh.lists.sections.insights.management.InsightsManagementViewModel.InsightModel
import org.wordpress.android.ui.stats.refresh.lists.sections.insights.management.InsightsManagementViewModel.InsightModel.Type.ADDED
import org.wordpress.android.ui.stats.refresh.lists.sections.insights.management.InsightsManagementViewModel.InsightModel.Type.REMOVED
import java.util.Collections

class InsightsManagementAdapter(
    private val onItemButtonClicked: (InsightModel) -> Unit,
    private val onDragStarted: (viewHolder: ViewHolder) -> Unit,
    private val onDragFinished: (List<InsightModel>) -> Unit
) : Adapter<InsightsManagementViewHolder>(), ItemTouchHelperAdapter {
    private var items = ArrayList<InsightModel>()

    override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): InsightsManagementViewHolder {
        val type = InsightModel.Type.values()[itemType]
        return when (type) {
            ADDED -> AddedInsightViewHolder(parent, onDragStarted, onItemButtonClicked)
            REMOVED -> RemovedInsightViewHolder(parent, onItemButtonClicked)
        }
    }

    override fun onBindViewHolder(holder: InsightsManagementViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type.ordinal
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(items, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(items, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onDragFinished() {
        onDragFinished.invoke(items)
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: List<InsightModel>) {
        val diffResult = DiffUtil.calculateDiff(InsightModelDiffCallback(items, newItems))
        items = ArrayList(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}

interface ItemTouchHelperAdapter {
    fun onItemMoved(fromPosition: Int, toPosition: Int)
    fun onDragFinished()
}

class InsightModelDiffCallback(
    private val oldList: List<InsightModel>,
    private val newList: List<InsightModel>
) : Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].insightsTypes == newList[newItemPosition].insightsTypes
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}