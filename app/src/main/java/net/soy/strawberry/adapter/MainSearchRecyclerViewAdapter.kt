package net.soy.strawberry.adapter

import android.content.Intent
import android.net.Uri
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image_view_holder.view.*
import net.soy.strawberry.R

class MainSearchRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    data class ImageItem(var imageUrl: String, var documentUrl: String)

    private val imageItemList = ArrayList<ImageItem>()

    override fun getItemCount() = imageItemList.size

    fun addImageItem(imageUrl: String, documentUrl: String) {
        imageItemList.add(ImageItem(imageUrl, documentUrl))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ImageHolder)?.onBind(imageItemList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ImageHolder(parent)

    class ImageHolder(parent: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_image_view_holder, parent, false)
    ) {
        fun onBind(item: ImageItem){
            itemView.run {
                Glide.with(context).load(item.imageUrl).into(main_image_view)
                main_image_view.setOnClickListener {
                    ContextCompat.startActivity(context, Intent(Intent.ACTION_VIEW, Uri.parse(item.documentUrl)), null)
                }
            }
        }
    }
}