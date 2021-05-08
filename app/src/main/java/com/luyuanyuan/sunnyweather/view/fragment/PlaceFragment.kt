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
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.luyuanyuan.sunnyweather.R
import com.luyuanyuan.sunnyweather.model.entity.Place
import com.luyuanyuan.sunnyweather.util.showToast
import com.luyuanyuan.sunnyweather.util.startActivity
import com.luyuanyuan.sunnyweather.view.activity.MainActivity
import com.luyuanyuan.sunnyweather.view.activity.WeatherActivity
import com.luyuanyuan.sunnyweather.view.adapter.PlaceAdapter
import com.luyuanyuan.sunnyweather.viewmodel.PlaceViewModel
import kotlinx.android.synthetic.main.activity_weather.*
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
        val savePlace = mViewModel.getSavePlace()
        if (savePlace != null) {
            savePlace(savePlace)
        }
    }

    private fun initListeners() {
        searchDone.setOnClickListener {
            val query = searchPlaceEdit.text.toString().trim()
            if (TextUtils.isEmpty(query)) {
                R.string.input_not_empty_tips.showToast()
            } else {
                mProgressDialog.show()
                searchDone.isEnabled = false
                mViewModel.searchPlaceList(query)
            }
        }
        ivBack.setOnClickListener {
            activity?.finish()
        }
    }

    private fun initAdapters() {
        recycleView.layoutManager = LinearLayoutManager(activity)
        mAdapter = PlaceAdapter(this, mViewModel.mPlaceList)
        recycleView.adapter = mAdapter
    }

    private fun observerData() {
        mViewModel.mPlaceListData.observe(this, Observer {
            mProgressDialog.dismiss()
            searchDone.isEnabled = true
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

    fun savePlace(place: Place) {
        mViewModel.savePlace(place)
        if (activity is MainActivity) {
            activity?.startActivity<WeatherActivity> {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            activity?.finish()
        } else if (activity is WeatherActivity) {
            val weatherActivity = activity as WeatherActivity
            weatherActivity.drawerLayout.closeDrawer(GravityCompat.START)
            weatherActivity.viewModel.locationLng = place.location.lng
            weatherActivity.viewModel.locationLat = place.location.lat
            weatherActivity.viewModel.placeName = place.name
            weatherActivity.refreshWeather()
        }
    }
}