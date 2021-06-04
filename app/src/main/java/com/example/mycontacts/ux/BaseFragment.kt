package com.example.mycontacts.ux

import android.app.Activity
import android.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.mycontacts.R
import kotlinx.android.synthetic.main.layout_default_alert.view.*

open class BaseFragment : Fragment() {

    fun addFragment(fragment: Fragment, backStack: Boolean = true) {
        activity?.let {
            val tx = it.supportFragmentManager.beginTransaction()
            tx.add(R.id.fragment_place, fragment, fragment.javaClass.name)
            if (backStack) tx.addToBackStack(fragment.javaClass.name)
            tx.commit()
        }
    }

    fun replaceFragment(fragment: Fragment, backStack: Boolean = true) {
        activity?.let {
            val tx = it.supportFragmentManager.beginTransaction()
            tx.replace(R.id.fragment_place, fragment, fragment.javaClass.name)
            if (backStack) tx.addToBackStack(fragment.javaClass.name)
            tx.commit()
        }
    }

    fun popBackStack(times: Int = 1) {
        for (i in 1..times)
            activity?.supportFragmentManager?.popBackStack()
    }

    fun showAlert(
        fragment: Fragment,
        title: String,
        message: String,
        onOkClick: onClickListener? = null,
    ) {
        val alertDialog = AlertDialog.Builder(fragment.requireContext())
        val view = fragment.layoutInflater.inflate(R.layout.layout_default_alert, null)
        alertDialog.setView(view)
        val builder = alertDialog.create()

        view.alertTV.text = title
        view.messageTV.text = message

        view.okBtn.setOnClickListener {
            onOkClick?.onClick()
            builder.dismiss()
        }
        builder.show()
    }

    interface onClickListener {
        fun onClick()
    }
}