package com.example.mycontacts.ux.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycontacts.R
import com.example.mycontacts.ux.BaseFragment
import com.example.mycontacts.ux.adapter.ContactsAdapter
import com.example.mycontacts.ux.model.Contacts
import com.example.mycontacts.ux.utils.ContactUtils
import kotlinx.android.synthetic.main.fragment_contacts_list.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ContactsListFragment : BaseFragment() {
    var contacts = arrayListOf<Contacts>()
    var contactsAdapter: ContactsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacts_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener {
            addFragment(CreateContactFragment())
        }
        contactsRvList?.layoutManager = LinearLayoutManager(requireContext())
        contactsAdapter = ContactsAdapter(contacts)
        contactsRvList?.adapter = contactsAdapter

        searchET?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchedContacts = arrayListOf<Contacts>()
                if (s.isNullOrEmpty()) {
                    searchedContacts.addAll(contacts)
                    clearIV.visibility = View.GONE
                } else {
                    clearIV.visibility = View.VISIBLE
                    contacts.forEach {
                        if (it.name.contains(s.toString()))
                            searchedContacts.add(it)
                    }
                }
                if (searchedContacts.isEmpty()){
                    noContactsLayout?.visibility = View.VISIBLE
                    contactsRvList?.visibility = View.GONE
                }else{
                    noContactsLayout?.visibility = View.GONE
                    contactsRvList?.visibility = View.VISIBLE
                    contactsAdapter?.addContacts(searchedContacts)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        clearIV.setOnClickListener {
            searchET.setText("")
        }

        getContacts()
    }

    private fun getContacts() {
        loader?.visibility = View.VISIBLE
        val executor: Executor =
            Executors.newSingleThreadExecutor()
        executor.execute {
            val contact = ContactUtils.getContactList(requireContext())
            if (contacts.isEmpty())
                contacts = contact
            else {
                contact.forEach {
                    it.isNew = !contacts.contains(it)
                }
            }
            activity?.runOnUiThread {
                if (contacts.isNullOrEmpty()) {
                    noContactsLayout?.visibility = View.VISIBLE
                    contactsRvList?.visibility = View.GONE
                    searchLayout?.visibility = View.GONE
                } else {
                    noContactsLayout?.visibility = View.GONE
                    contactsRvList?.visibility = View.VISIBLE
                    searchLayout?.visibility = View.VISIBLE
                    if (contactsAdapter != null) {
                        contactsAdapter?.addContacts(contact)
                        contacts = contact
                    } else {
                        contactsAdapter = ContactsAdapter(contacts)
                        contactsRvList?.adapter = contactsAdapter
                    }
                }
                loader?.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getContacts()
    }

}