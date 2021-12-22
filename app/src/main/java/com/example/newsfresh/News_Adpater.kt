package com.example.newsfresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class News_Adpater(private val listener:NewsItemClicked) : RecyclerView.Adapter<News_adapter>() {

    private val items:ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): News_adapter {
       val view= LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
       val viewholder = News_adapter(view)
        view.setOnClickListener{
                        listener.onItemClicked(items[viewholder.adapterPosition])
        }
    return viewholder
    }

    override fun onBindViewHolder(holder: News_adapter, position: Int) {
        val currentitem =items[position]
        holder.textView.text = currentitem.title
        Glide.with(holder.itemView.context).load(currentitem.urltoimage).into(holder.imageView)
        holder.textView2.text=currentitem.author


    }
    fun updatedNews(updateNews:ArrayList<News>)
    {
        items.clear();
        items.addAll(updateNews)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
class News_adapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView:TextView=itemView.findViewById(R.id.textView)
    val textView2:TextView=itemView.findViewById(R.id.author)
    val imageView:ImageView=itemView.findViewById(R.id.imageView2)

}
