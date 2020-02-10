package fr.isen.gougeon.androidtoolbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.recycler_view_contact_cell.view.*

import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter(val contacts: ArrayList<ContactModel>): RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>(){

    class ContactViewHolder(val view: View):  RecyclerView.ViewHolder(view) {
        fun bind(contact: String?){
            view.displayNameTextView.text = contact
            view.mail_text_view.text = ""
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsAdapter.ContactViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_contact_cell, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contacts.count()
    }

    override fun onBindViewHolder(holder: ContactsAdapter.ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact.displayName)
    }
}

