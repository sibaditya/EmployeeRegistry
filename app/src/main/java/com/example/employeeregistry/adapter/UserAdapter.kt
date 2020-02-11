package com.example.employeeregistry.adapter

import Users
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeregistry.R
import com.squareup.picasso.Picasso
import java.util.*

class UserAdapter(private val context: Context, private val usersItemClickedListener: UsersItemClickedListener) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    interface UsersItemClickedListener {
        fun onUsersItemClicked(userModel: Users)
    }

    private val usersList = ArrayList<Users>()

    fun setAppList(usersModelList: ArrayList<Users>, refreshNeeded: Boolean) {
        if(refreshNeeded) {
            usersList.clear()
        }
        usersList.addAll(usersModelList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val model: Users = usersList[position]
        holder.bind(model, context, usersItemClickedListener)
    }

    class UserViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.layout_user_list, parent, false)) {
        private var usersItemView: LinearLayout? = null
        private var userNameView: TextView? = null
        private var userImageView: ImageView? = null

        private var titleView: TextView? = null
        private var streetView: TextView? = null
        private var suiteView: TextView? = null
        private var cityView: TextView? = null
        private var zipcodeView: TextView? = null


        init {
            usersItemView = itemView.findViewById(R.id.user_container)
            userNameView = itemView.findViewById(R.id.user_name)
            userImageView = itemView.findViewById(R.id.user_image)

            titleView = itemView.findViewById(R.id.title)
            streetView = itemView.findViewById(R.id.street)
            suiteView = itemView.findViewById(R.id.suite)
            cityView = itemView.findViewById(R.id.city)
            zipcodeView = itemView.findViewById(R.id.zipcode)
        }

        fun bind(user: Users, context: Context, usersItemClickedListener: UsersItemClickedListener) {
            userNameView?.text = user.username
            if (!user.avatar?.thumbnail.isNullOrEmpty()) {
                userImageView?.visibility = View.VISIBLE
                Picasso.with(context).load(user.avatar?.large).into(userImageView)
            }
            usersItemView?.setOnClickListener {
                usersItemClickedListener.onUsersItemClicked(user)
            }

            titleView?.text = user.name
            streetView?.text = user.address?.street
            suiteView?.text = user.address?.suite
            cityView?.text = user.address?.city
            zipcodeView?.text = user.address?.zipcode
        }
    }
}
