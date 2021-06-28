package ru.lachesis.weather_app.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.contacts_fragment.view.*
import ru.lachesis.weather_app.R


class ContactsAdapter: RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
     var contactsArray: Array<String> = arrayOf()

    fun setContacts(contacts: Array<String>){
        contactsArray = contacts
    }

    inner class ContactViewHolder(itemView: View): RecyclerView.ViewHolder( itemView) {
        fun bind(contact: String){
             itemView.findViewById<TextView>(R.id.contacts_item_textview).text= contact
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.contacts_item,parent,false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactsArray[position])
    }

    override fun getItemCount(): Int {
        return contactsArray.size
    }
}