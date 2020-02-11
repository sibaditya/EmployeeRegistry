package com.example.employeeregistry.activity

import Users
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeregistry.R
import com.example.employeeregistry.adapter.UserAdapter
import com.example.employeeregistry.util.SharedPreferenceUtil
import com.example.employeeregistry.viewmodel.MainActivityViewModel
import com.facebook.shimmer.ShimmerFrameLayout

class MainActivity : AppCompatActivity(), UserAdapter.UsersItemClickedListener,
    MainActivityViewModel.NetworkFailureListener  {
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var userRecycleListViewAdapter: UserAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var errorView: LinearLayout
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private var dataList: ArrayList<Users> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.user_list)
        errorView = findViewById(R.id.error_layout)
        val retryView = findViewById<ImageView>(R.id.retry)
        retryView.setOnClickListener {
            refreshView()
        }
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container)
        SharedPreferenceUtil.init(this)
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.init(this, this)
        setRecyclerView(dataList)
        subscribeDataCallBack(false)
    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
        shimmerFrameLayout.stopShimmerAnimation()
    }


    private fun subscribeDataCallBack(refreshNeeded: Boolean) {
        mainActivityViewModel.getUserMutableLiveData()?.observe(this, Observer<ArrayList<Users>> {
            if (it != null && it.size > 0) {
                recyclerView.visibility = View.VISIBLE
                errorView.visibility = View.GONE
                shimmerFrameLayout.stopShimmerAnimation()
                shimmerFrameLayout.visibility = View.GONE
                userRecycleListViewAdapter.setAppList(it, refreshNeeded)
            } else {
                handleErrorView()
            }
        })
    }


    private fun setRecyclerView(dataList: ArrayList<Users>) {
        userRecycleListViewAdapter =
            UserAdapter(this, this)
        val categoryLinearLayoutManager = LinearLayoutManager(this)
        categoryLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = categoryLinearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            categoryLinearLayoutManager.getOrientation()
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = userRecycleListViewAdapter
        userRecycleListViewAdapter.setAppList(dataList, false)
    }

    override fun onUsersItemClicked(userModel: Users) {
        val intent = Intent(this, UserProfileActivity::class.java)
        intent.putExtra(UserProfileActivity.USER_DATA_BUNDLE, userModel)
        startActivity(intent)
    }

    override fun onNetworkFailure(error: String?) {
        handleErrorView()
    }

    fun handleErrorView() {
        recyclerView.visibility = View.GONE
        shimmerFrameLayout.stopShimmerAnimation()
        errorView.visibility = View.VISIBLE
        findViewById<ImageView>(R.id.retry).setOnClickListener {
            mainActivityViewModel.init(this, this)
            subscribeDataCallBack(false)
        }
    }

    private fun refreshView(): Boolean {
        recyclerView.visibility = View.GONE
        shimmerFrameLayout.visibility = View.GONE
        shimmerFrameLayout.startShimmerAnimation()
        mainActivityViewModel.init(this, this)
        subscribeDataCallBack(true)
        return true
    }

}

