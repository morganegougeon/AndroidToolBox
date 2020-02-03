package fr.isen.gougeon.androidtoolbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_contact_cell.view.*


class RandomContactAdapter(val contacts: ArrayList<RandomContactModel>): RecyclerView.Adapter<RandomContactAdapter.RandomContactViewHolder>() {

    class RandomContactViewHolder(val view: View):  RecyclerView.ViewHolder(view) {
        fun bind(contact: String?){
            view.displayNameTextView.text = contact
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RandomContactAdapter.RandomContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_contact_cell, parent, false)
        return RandomContactAdapter.RandomContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contacts.count()
    }

    override fun onBindViewHolder(holder: RandomContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact.name?.first.toString())
    }

}