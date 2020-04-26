package com.digital.hospital.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digital.hospital.R

/**
 * @CreatedBy Ali Ahsan
 *
 *         Author Email: this.aliahsan@gmail.com
 *         Created on: 25/04/20
 */

class ChatAdapter (private val messageArrayList: MutableList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val self = 100
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        // view type is to identify where to render the chat message
        // left or right
        val itemView: View = if (viewType == self) {
            // self message
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.chat_item_self, parent, false)
        } else {
            // WatBot message
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.chat_item_watson, parent, false)
        }
        return ViewHolder(itemView)
    }

    override fun getItemViewType(position: Int): Int {

        val (id) = messageArrayList[position]

        return if (id == 1L)
                  self
             else
                  position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messageArrayList[position]

        (holder as ViewHolder).message.text = message.message
    }

    override fun getItemCount(): Int {
        return messageArrayList.size
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        var message: TextView = itemView.findViewById(R.id.message)

    }

}