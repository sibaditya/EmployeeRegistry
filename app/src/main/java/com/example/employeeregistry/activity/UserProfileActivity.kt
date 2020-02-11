package com.example.employeeregistry.activity

import Users
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeregistry.GridItemDecoration
import com.example.employeeregistry.R
import com.example.employeeregistry.adapter.PhotoAlbumAdapter
import com.example.employeeregistry.fragment.ImageDialogFragment
import com.example.employeeregistry.fragment.UserPhotoAlbumFragment
import com.example.employeeregistry.fragment.UserProfileCardFragment
import com.example.employeeregistry.model.Photo
import com.example.employeeregistry.viewmodel.PhotoAlbumViewModel
import com.facebook.shimmer.ShimmerFrameLayout


class UserProfileActivity : AppCompatActivity(),
    PhotoAlbumAdapter.PhotoItemClickedListener,
    PhotoAlbumViewModel.NetworkFailureListener{

    companion object {
        const val USER_DATA_BUNDLE = "UserDataBundle"
    }

    private lateinit var userBundle: Users

    private lateinit var photAlbumRecyclerView: RecyclerView

    private lateinit var photoAlbumViewModel: PhotoAlbumViewModel
    private lateinit var photoAlbumRecycleListViewAdapter: PhotoAlbumAdapter
    private lateinit var errorView: TextView
    private var dataList: ArrayList<Photo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        photoAlbumViewModel = ViewModelProviders.of(this).get(PhotoAlbumViewModel::class.java)
        photAlbumRecyclerView = findViewById(R.id.photo_album_recycler)
        userBundle = intent.getParcelableExtra(USER_DATA_BUNDLE)
        supportActionBar?.title = userBundle?.name
        errorView = findViewById(R.id.error_layout)
        photoAlbumViewModel.init(this, this, userBundle?.id.toString())
        setRecyclerView(dataList)
        subscribeDataCallBack(false)
        if (savedInstanceState == null) {
            val userProfileCardFragment = UserProfileCardFragment.newInstance(userBundle)
            val manager: FragmentManager = supportFragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction()
            transaction.add(R.id.container, userProfileCardFragment, UserProfileCardFragment.TAG)
            transaction.commit()
        }
    }

    private fun setRecyclerView(dataList: ArrayList<Photo>) {
        photoAlbumRecycleListViewAdapter =
            PhotoAlbumAdapter(this, this)
        photAlbumRecyclerView.layoutManager = GridLayoutManager(this, 3)
        photAlbumRecyclerView.addItemDecoration(GridItemDecoration(10, 3))
        photAlbumRecyclerView.adapter = photoAlbumRecycleListViewAdapter
        photoAlbumRecycleListViewAdapter.setAppList(dataList, false)
    }

    private fun subscribeDataCallBack(refreshNeeded: Boolean) {
        photoAlbumViewModel.getPhotoMutableLiveData().observe(this, Observer<ArrayList<Photo>> {
            if (it != null && it.size > 0) {
                photAlbumRecyclerView.visibility = View.VISIBLE
                errorView.visibility = View.GONE
                photoAlbumRecycleListViewAdapter.setAppList(it, refreshNeeded)
            } else {
                handleErrorView(getString(R.string.something_went_wrong))
            }
        })
    }

    private fun handleErrorView(msg: String?) {
        photAlbumRecyclerView.visibility = View.GONE
        errorView.visibility = View.VISIBLE
        errorView.text = msg
    }


    override fun onPhotoItemClicked(photoModel: Photo) {
        val imageDialogFragment: ImageDialogFragment = ImageDialogFragment.newInstance(photoModel)
        imageDialogFragment.show(supportFragmentManager, ImageDialogFragment.TAG)
    }

    override fun onNetworkFailure(error: String?) {
        handleErrorView(error)
    }
}
