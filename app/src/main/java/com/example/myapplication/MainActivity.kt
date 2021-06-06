package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), RecyclerviewAdapter.OnRecyclerItemClick {
    lateinit var binding: ActivityMainBinding
    lateinit var recyclerviewAdapter: RecyclerviewAdapter
    lateinit var viewModel: MainActivityviewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerview()
        loadData()
    }

    private fun initRecyclerview() {
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(applicationContext,DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            recyclerviewAdapter = RecyclerviewAdapter(this@MainActivity)
            adapter = recyclerviewAdapter
        }
    }


    private fun loadData(){
       viewModel = ViewModelProvider(this).get(MainActivityviewModel::class.java)
        viewModel.getLocListObervable().observe(this, Observer <LocationList>{
            recyclerviewAdapter.apply {
                locListData = it.data.toMutableList()
                notifyDataSetChanged()
            }
        })
        viewModel.loadData(this)
    }

    override fun OnItemClickListener(Data: LocationData) {

    }
}