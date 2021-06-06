package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.RecyclerListRowBinding


class RecyclerviewAdapter(val clickListener:OnRecyclerItemClick):RecyclerView.Adapter<RecyclerviewAdapter.myviewHolder>() {

    var locListData = mutableListOf<LocationData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerviewAdapter.myviewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_row, parent, false)
        return myviewHolder(inflater , clickListener)
    }

    override fun onBindViewHolder(holder: RecyclerviewAdapter.myviewHolder, position: Int) {
        holder.bind(locListData[position])
        holder.itemView.setOnClickListener {
            clickListener.OnItemClickListener(locListData[position])
        }
    }

    override fun getItemCount(): Int = locListData.size

    class myviewHolder(view: View ,val clickListener:OnRecyclerItemClick) : RecyclerView.ViewHolder(view) {
        val binding = RecyclerListRowBinding.bind(view)
        fun bind(Data: LocationData) {
            binding.textViewName.text = Data.locationName
            var address = ""

            Data.address?.let {
                address += it
            }

            Data.city?.let {
                address += it
            }

            Data.state?.let {
                address += it
            }

            Data.zip?.let {
                address += it
            }

            Data.country?.let {
                address += it
            }
            binding.textViewAddress.text = address

            Glide
                .with(binding.imageview)
                .load(Data.url)
                .into(binding.imageview)

            if (Data.childLocation != null && Data.childLocation.size > 0) {
                binding.apply {
                    childtext.visibility = VISIBLE
                    childRecyclerView.visibility = VISIBLE
                    childRecyclerView.apply {
                        layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        val decoration = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
                        addItemDecoration(decoration)
                        val recyclerviewAdapter = RecyclerviewAdapter(clickListener)
                        recyclerviewAdapter.locListData = Data.childLocation.toMutableList()
                        adapter = recyclerviewAdapter
                    }
                }
                binding.childtext.text = " Child Location " + Data.childLocation.size
            } else {
                binding.apply {
                    childtext.visibility = GONE
                    childRecyclerView.visibility = GONE
                }
            }

        }
    }
    interface OnRecyclerItemClick{
      fun  OnItemClickListener(Data :LocationData)
    }
}