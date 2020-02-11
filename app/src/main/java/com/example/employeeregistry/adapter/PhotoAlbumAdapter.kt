package com.example.employeeregistry.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeregistry.R
import com.example.employeeregistry.model.Photo
import com.squareup.picasso.Picasso
import java.util.ArrayList

class PhotoAlbumAdapter(private val context: Context, private val usersItemClickedListener: PhotoItemClickedListener) : RecyclerView.Adapter<PhotoAlbumAdapter.PhotoAlbumViewHolder>() {

    interface PhotoItemClickedListener {
        fun onPhotoItemClicked(photoModel: Photo)
    }

    private val photosList = ArrayList<Photo>()

    fun setAppList(usersModelList: ArrayList<Photo>, refreshNeeded: Boolean) {
        if(refreshNeeded) {
            photosList.clear()
        }
        photosList.addAll(usersModelList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoAlbumViewHolder {
        return PhotoAlbumViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_photo_album, parent, false))
    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    override fun onBindViewHolder(holder: PhotoAlbumViewHolder, position: Int) {
        val model: Photo = photosList[position]
        holder.bind(model, context, usersItemClickedListener)
    }

    class PhotoAlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var photoImageView: ImageView = view.findViewById(R.id.album_photo)

        fun bind(photo: Photo, context: Context?, usersItemClickedListener: PhotoItemClickedListener) {

            if (!photo.thumbnailUrl.isNullOrEmpty()) {
                Picasso.with(context)
                    .load(photo.thumbnailUrl)
                    .placeholder(context?.getDrawable(R.drawable.logo))
                    .into(photoImageView)

            }
            photoImageView?.setOnClickListener {
                usersItemClickedListener.onPhotoItemClicked(photo)
            }
        }
    }
}
