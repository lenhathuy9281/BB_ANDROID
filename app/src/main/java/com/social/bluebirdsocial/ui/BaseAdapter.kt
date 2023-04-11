package com.social.bluebirdsocial.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView



abstract class BaseRcvVH<T>(itemView: View) : RecyclerView.ViewHolder(itemView), OnBind<T> {

    init {
        onInitView(itemView)
    }

    final override fun onInitView(itemView: View) {

    }

    abstract override fun onBind(data: T)

    override fun onBind(data: T, items: List<Any>) {

    }
}

interface OnBind<T> {
    fun onInitView(itemView: View)
    fun onBind(data: T)
    fun onBind(data: T, items: List<Any>)
}

abstract class BaseAdapter : RecyclerView.Adapter<BaseRcvVH<Any?>>() {
    protected var mDataSet = mutableListOf<Any?>()
    protected var mDataSetAllTemp = mutableListOf<Any?>()

    abstract fun getLayoutResource(viewType: Int): Int
    abstract fun onCreateVH(itemView: View, viewType: Int): BaseRcvVH<*>

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseRcvVH<Any?> {
        val layout = getLayoutResource(viewType)
        val v = LayoutInflater.from(viewGroup.context).inflate(layout, viewGroup, false)
        return onCreateVH(v, viewType) as BaseRcvVH<Any?>
    }

    override fun onBindViewHolder(
        baseRclvHolder: BaseRcvVH<Any?>,
        position: Int
    ) {
        baseRclvHolder.onBind(getItemDataAtPosition(position))
    }

    override fun onBindViewHolder(
        baseRclvHolder: BaseRcvVH<Any?>,
        position: Int,
        items: List<Any>
    ) {
        if (items.isEmpty()) {
            super.onBindViewHolder(baseRclvHolder, position, items)
        } else {
            baseRclvHolder.onBind(getItemDataAtPosition(position), items)
        }
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    fun getItemDataAtPosition(position: Int): Any {
        return mDataSet[position]!!
    }

    fun addItem(item: Any?) {
        mDataSet.add(item)
        mDataSetAllTemp.add(item)
    }

    fun addItems(items: List<Any?>?) {
        if (items.isNullOrEmpty()) {
            return
        }
        mDataSet.addAll(items)
        mDataSetAllTemp.addAll(items)
    }

    fun addItemAndNotify(item: Any?) {
        mDataSet.add(item)
        notifyItemInserted(mDataSet.size - 1)
    }

    fun addItemAtIndexAndNotify(index: Int, item: Any?) {
        mDataSet.add(index, item)
        notifyItemInserted(index)
    }

    fun addItemsAtIndex(items: List<Any?>?, index: Int) {
        if (items.isNullOrEmpty()) {
            return
        }
        mDataSet.addAll(index, items)
    }

    fun addItemAtIndex(item: Any?, index: Int) {
        mDataSet.add(index, item)
    }

    fun addItemsAndNotify(items: List<*>) {
        val start = mDataSet.size
        mDataSet.addAll(items)
        notifyItemRangeInserted(start, items.size)
    }

    fun removeItemAndNotify(index: Int) {
        mDataSet.removeAt(index)
        notifyItemRemoved(index)
    }

    open fun reset(newItems: List<*>?) {
        mDataSet.clear()
        mDataSetAllTemp.clear()
        addItems(newItems)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        mDataSet.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clearData() {
        mDataSet.clear()
        mDataSetAllTemp.clear()
    }

    fun clearDataAndNotify() {
        notifyItemRangeRemoved(0, mDataSet.size)
        mDataSet.clear()
        mDataSetAllTemp.clear()
    }


    open fun getItemAtPosition(position: Int): Any? {
        return mDataSet[position]!!
    }
}