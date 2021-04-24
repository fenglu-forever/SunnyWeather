package com.luyuanyuan.sunnyweather.view.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.luyuanyuan.sunnyweather.R
import com.luyuanyuan.sunnyweather.util.showToast
import com.luyuanyuan.sunnyweather.view.adapter.PlaceAdapter
import com.luyuanyuan.sunnyweather.viewmodel.PlaceViewModel
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment : Fragment() {
    private lateinit var mAdapter: PlaceAdapter
    private lateinit var mProgressDialog: ProgressDialog

    val mViewModel by lazy {
        ViewModelProviders.of(this).get(PlaceViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mProgressDialog = ProgressDialog(activity)
        mProgressDialog.setCancelable(false)
        mProgressDialog.setMessage(getString(R.string.loading_msg))
        return LayoutInflater.from(activity).inflate(R.layout.fragment_place, container, false)
    }

    override fun onDestroyView() {
        if (mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
        super.onDestroyView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListeners()
        initAdapters()
        observerData()
    }

    private fun initListeners() {
        searchDone.setOnClickListener {
            val query = searchPlaceEdit.text.toString().trim()
            if (TextUtils.isEmpty(query)) {
                R.string.input_not_empty_tips.showToast()
            } else {
                mProgressDialog.show()
                mViewModel.searchPlaceList(query)
            }
        }
    }

    private fun initAdapters() {
        recycleView.layoutManager = LinearLayoutManager(activity)
        mAdapter = PlaceAdapter(mViewModel.mPlaceList)
        recycleView.adapter = mAdapter
    }

    private fun observerData() {
        mViewModel.mPlaceListData.observe(this, Observer {
            mProgressDialog.dismiss()
            mViewModel.mPlaceList.clear()
            val placeList = it.getOrNull()
            if (placeList != null) {
                mViewModel.mPlaceList.addAll(placeList)
            } else {
                it.exceptionOrNull()?.printStackTrace()
            }
            if (mViewModel.mPlaceList.isEmpty()) {
                bgImageView.visibility = View.VISIBLE
            } else {
                bgImageView.visibility = View.GONE
            }
            mAdapter.notifyDataSetChanged()
        })
    }
}