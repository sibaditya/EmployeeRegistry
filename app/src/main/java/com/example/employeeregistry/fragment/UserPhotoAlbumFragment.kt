package com.example.employeeregistry.fragment

import Users
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeregistry.R
import com.example.employeeregistry.adapter.PhotoAlbumAdapter
import com.example.employeeregistry.adapter.UserAdapter
import com.example.employeeregistry.fragment.UserProfileCardFragment.Companion.USER_PROFILE_DETAILS
import com.example.employeeregistry.model.Photo
import com.example.employeeregistry.util.SharedPreferenceUtil
import com.example.employeeregistry.viewmodel.MainActivityViewModel
import com.example.employeeregistry.viewmodel.PhotoAlbumViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UserPhotoAlbumFragment : Fragment(),
    PhotoAlbumAdapter.PhotoItemClickedListener,
        PhotoAlbumViewModel.NetworkFailureListener {
    companion object {
        val TAG = UserPhotoAlbumFragment::class.java.simpleName
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserProfileCompanyDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(users: Users?) =
            UserProfileCardFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_PROFILE_DETAILS, users)
                }
            }
    }


    private var userModel: Users? = null
    private lateinit var photAlbumRecyclerView: RecyclerView

    private lateinit var photoAlbumViewModel: PhotoAlbumViewModel
    private lateinit var photoAlbumRecycleListViewAdapter: PhotoAlbumAdapter
    private lateinit var errorView: LinearLayout
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private var dataList: ArrayList<Photo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userModel = it.getParcelable(USER_PROFILE_DETAILS)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_photo_album, container, false)
        photAlbumRecyclerView = view.findViewById(R.id.photo_album_recycler)

        /*errorView = findViewById(R.id.error_layout)
        val retryView = findViewById<ImageView>(R.id.retry)
        retryView.setOnClickListener {
            refreshView()
        }
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container)*/
/*
        photoAlbumViewModel = ViewModelProviders.of(this).get(PhotoAlbumViewModel::class.java)
        photoAlbumViewModel.init(this, this, userModel?.id.toString())
        //setRecyclerView(dataList)
        subscribeDataCallBack(false)
        Log.e("Siba", "UserPhotoAlbumFragment")*/
        return view
    }

    private fun setRecyclerView(dataList: ArrayList<Photo>) {
        /*photoAlbumRecycleListViewAdapter =
            PhotoAlbumAdapter(this, this)
        val categoryLinearLayoutManager = LinearLayoutManager(activity)
        categoryLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        photAlbumRecyclerView.layoutManager = categoryLinearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(
            photAlbumRecyclerView.context,
            categoryLinearLayoutManager.getOrientation()
        )
        photAlbumRecyclerView.addItemDecoration(dividerItemDecoration)
        photAlbumRecyclerView.adapter = photoAlbumRecycleListViewAdapter
        photoAlbumRecycleListViewAdapter.setAppList(dataList, false)*/
    }

    private fun subscribeDataCallBack(refreshNeeded: Boolean) {
        photoAlbumViewModel.getPhotoMutableLiveData()?.observe(viewLifecycleOwner, Observer<ArrayList<Photo>> {
            if (it != null && it.size > 0) {
                /*photAlbumRecyclerView.visibility = View.VISIBLE
                errorView.visibility = View.GONE
                shimmerFrameLayout.stopShimmerAnimation()
                shimmerFrameLayout.visibility = View.GONE*/
                photoAlbumRecycleListViewAdapter.setAppList(it, refreshNeeded)
            } else {
                //handleErrorView()
            }
        })
    }

    override fun onPhotoItemClicked(photoModel: Photo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNetworkFailure(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}