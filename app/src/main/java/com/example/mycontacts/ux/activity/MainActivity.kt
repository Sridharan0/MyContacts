package com.example.mycontacts.ux.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mycontacts.R
import com.example.mycontacts.ux.fragments.ContactsListFragment

class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUESTS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!allPermissionsGranted())
            getRuntimePermissions()
        else addFragment(ContactsListFragment())

    }


    private fun allPermissionsGranted(): Boolean {
        for (permission in getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                return false
            }
        }
        return true
    }

    private fun getRequiredPermissions(): Array<String?> {
        return arrayOf(
                Manifest.permission.READ_CONTACTS
        )
    }

    private fun isPermissionGranted(
            context: Context,
            permission: String?
    ): Boolean {
        if (ContextCompat.checkSelfPermission(context, permission!!)
                == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun getRuntimePermissions() {
        val allNeededPermissions: MutableList<String?> =
                ArrayList()
        for (permission in getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                allNeededPermissions.add(permission)
            }
        }
        if (!allNeededPermissions.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.requestPermissions(
                        allNeededPermissions.toTypedArray(),
                        PERMISSION_REQUESTS
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUESTS) {
            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED){
                addFragment(ContactsListFragment())
            }
        }
    }

    private fun addFragment(fragment: Fragment, backstack: Boolean = true) {
        val tx = supportFragmentManager.beginTransaction()
        tx.add(R.id.fragment_place, fragment, fragment.javaClass.name)
        if (backstack) tx.addToBackStack(fragment.javaClass.name)
        tx.commit()
    }


}