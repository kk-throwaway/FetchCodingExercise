package com.example.fetchcodingexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    lateinit var viewModel : MainActivityViewModel
    lateinit var itemAdapter: ItemAdapter
    lateinit var mProgressBar: ProgressBar
    lateinit var mErrorLayout: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        initView()
        observers()
        pollDataFromApi()
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)

            itemAdapter = ItemAdapter()
            adapter = itemAdapter
        }
    }

    private fun initView(){
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mProgressBar = progressBar
        mErrorLayout = relativeLayout
        mErrorLayout.setOnClickListener {
            pollDataFromApi()
        }
    }

    private fun observers(){
        viewModel.isLoading.observe(this, Observer {
            mProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.hasError.observe(this, Observer {
            mErrorLayout.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    private fun pollDataFromApi(){
        viewModel.getItemListObserver().observe(this, Observer<List<ItemModel>>{
            if(it != null){
                itemAdapter.mItems = it
                itemAdapter.notifyDataSetChanged()
            } else {
                Log.e(TAG, "Failed to Load Data")
            }
        })
        viewModel.pollData()
    }
}