package com.example.fastandroidnetworking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_event.view.*

class EventAdapter(private val itemEventList: ArrayList<Event>):
RecyclerView.Adapter<EventAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return itemEventList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get()
            .load(itemEventList[position].image).into(holder!!.imgPoster)
        holder.txtEventTitle.text = itemEventList[position].title
        holder.txtEventDate.text = itemEventList[position].date
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imgPoster = itemView.img_poster!!
        val txtEventTitle = itemView.tv_event_title!!
        val txtEventDate = itemView.tv_event_date!!
    }


}