package com.goodweather.android.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.goodweather.android.R
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment: Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(PlaceViewModel::class.java)
    }

    private lateinit var mAdapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        rvPlace.layoutManager = layoutManager
        mAdapter = PlaceAdapter(this, viewModel.placeList)
        rvPlace.adapter = mAdapter

        placeSearchEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                // 向服务器查询数据
                viewModel.searchPlaces(content)
            } else {
                rvPlace.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                mAdapter.notifyDataSetChanged()
            }
        }

        // temp value: viewLifecycleOwner replace this
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer {result ->
            val places = result.getOrNull()
            if (places != null) {
                rvPlace.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                mAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }

}