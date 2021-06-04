package com.example.mycontacts.ux.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycontacts.R
import com.example.mycontacts.ux.model.Contacts
import kotlinx.android.synthetic.main.item_layout_contacts.view.*

class ContactsAdapter(val contacts: ArrayList<Contacts>) :
    RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(
            LayoutInflater.from(parent.context).inflate(getItemViewType(viewType), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_layout_contacts
    }

    inner class ContactsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(position: Int) {
            val contact = contacts[position]
            itemView.nameTV.text = contact.name
            itemView.numberTV.text = contact.phoneNumber
            itemView.newTV.visibility = View.GONE
            if (contact.isNew)
                itemView.newTV.visibility = View.VISIBLE
        }
    }

    fun addContacts(contacts: ArrayList<Contacts>) {
        this.contacts.clear()
        this.contacts.addAll(contacts)
        notifyDataSetChanged()
    }
}