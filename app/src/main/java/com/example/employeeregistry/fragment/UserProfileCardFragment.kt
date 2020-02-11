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
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.employeeregistry.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class UserProfileCardFragment : Fragment() {

    companion object {
        const val USER_PROFILE_DETAILS = "USER_PROFILE_DETAILS"
        val TAG = UserProfileCardFragment::class.java.simpleName
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserProfileCompanyDetailFragment.
         */
        @JvmStatic
        fun newInstance(users: Users?) =
            UserProfileCardFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_PROFILE_DETAILS, users)
                }
            }
    }


    private var userModel: Users? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userModel = it.getParcelable(USER_PROFILE_DETAILS)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_profile_front, container, false)
        val userImage = view.findViewById<CircleImageView>(R.id.userimage)
        val name = view.findViewById<TextView>(R.id.username)
        val email = view.findViewById<TextView>(R.id.email)
        val phone = view.findViewById<TextView>(R.id.phone)
        val website = view.findViewById<TextView>(R.id.website)
        Picasso.with(context).load(userModel?.avatar?.large).into(userImage)
        name.text = userModel?.name
        email.text = userModel?.email
        phone.text = userModel?.phone
        website.text = userModel?.website

        email.setOnClickListener(View.OnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.data = Uri.parse("mailto:${userModel?.email}")
            startActivity(Intent.createChooser(emailIntent, "Send Email"))
        })

        phone.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_DIAL
            intent.data = Uri.parse("tel: ${userModel?.phone}")
            startActivity(intent)
        })

        website.setOnClickListener {

            try {
                if (!URLUtil.isValidUrl(userModel?.website)) {
                    Toast.makeText(context, " This is not a valid link", Toast.LENGTH_LONG).show()
                } else {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(userModel?.website)
                    context!!.startActivity(intent)
                }
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    context,
                    " You don't have any browser to open web page",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        return view
    }
}