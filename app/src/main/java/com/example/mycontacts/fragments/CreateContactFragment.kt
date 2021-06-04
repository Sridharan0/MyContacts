package com.example.mycontacts.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mycontacts.R
import kotlinx.android.synthetic.main.fragment_create_contact.*

class CreateContactFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveBtn.setOnClickListener {
            if (firstNameET.text.isEmpty()) {
                showAlert(this, "Missing Field", "First name")
                return@setOnClickListener
            }
            if (numberET.text.isEmpty()) {
                showAlert(this, "Missing Field", "Phone Number")
                return@setOnClickListener
            }
            val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
                type = ContactsContract.RawContacts.CONTENT_TYPE
            }
            intent.apply {
                putExtra(
                    ContactsContract.Intents.Insert.NAME,
                    firstNameET.text.toString() + " " + lastNameET.text.toString()
                )
                putExtra(ContactsContract.Intents.Insert.PHONE, numberET.text.toString())
            }
            startActivity(intent)
            popBackStack()
        }

    }
}