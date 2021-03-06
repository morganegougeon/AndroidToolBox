package fr.isen.gougeon.androidtoolbox

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_view_contact_cell.view.*
import kotlinx.android.synthetic.main.activity_web_services.*



class RandomContactAdapter(val contacts: ArrayList<RandomContactModel>, val listener : OnItemClickListener): RecyclerView.Adapter<RandomContactAdapter.RandomContactViewHolder>() {


    class RandomContactViewHolder(val view: View):  RecyclerView.ViewHolder(view) {
        fun bind(contact: String?, mail : String?, img_url: String?){
            view.displayNameTextView.text = contact
            view.mail_text_view.text = mail
            Picasso.get().load(img_url).into(view.contact_image_view)

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RandomContactAdapter.RandomContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_contact_cell, parent, false)
        view.setOnClickListener{
            listener.onItemClicked(it.tag as? RandomContactModel)
        }
        return RandomContactAdapter.RandomContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contacts.count()
    }

    override fun onBindViewHolder(holder: RandomContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact.name?.first.toString() + " " + contact.name?.last.toString(), contact.email.toString(), contact.picture?.large)

    }



    interface OnItemClickListener {
        fun onItemClicked(contact : RandomContactModel?)
    }





}