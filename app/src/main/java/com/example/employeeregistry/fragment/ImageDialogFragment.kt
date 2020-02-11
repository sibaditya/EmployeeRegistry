package com.example.employeeregistry.fragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.example.employeeregistry.R
import com.example.employeeregistry.model.Photo
import com.squareup.picasso.Picasso

class ImageDialogFragment : DialogFragment() {
    companion object {
        const val USER_GALARY_IMAGE = "USER_GALARY_IMAGE"
        val TAG = ImageDialogFragment::class.java.simpleName
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment UserProfileCompanyDetailFragment.
         */
        @JvmStatic
        fun newInstance(photo: Photo) =
            ImageDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_GALARY_IMAGE, photo)
                }
            }
    }

    private var photo: Photo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            photo = it.getParcelable(USER_GALARY_IMAGE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_selected_photo, container, false)
        val imageView = view.findViewById<ImageView>(R.id.album_photo)
        Picasso.with(context)
            .load(photo?.url)
            .placeholder(R.drawable.logo)
            .into(imageView)
        return view
    }

    override fun onResume() {
        super.onResume()
        // We only want this dialog to show in portrait mode
        activity?.let {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    override fun onPause() {
        super.onPause()
        // We only want this dialog to show in portrait mode
        activity?.let {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}