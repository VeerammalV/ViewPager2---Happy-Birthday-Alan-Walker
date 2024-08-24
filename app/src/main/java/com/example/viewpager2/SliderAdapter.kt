package com.example.viewpager2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide


class SliderAdapter(private val slides: ArrayList<Int>, private val viewPager2: ViewPager2,  private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {
    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(imageResource: Int) {
            Glide.with(itemView)
                .load(imageResource)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.inflate_slider_images_item, parent, false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(slides[position])
        if (position == slides.size-1) {
            viewPager2.post(runnable)
        }
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return slides.size
    }

    @SuppressLint("NotifyDataSetChanged")
    private val runnable = Runnable {
        slides.addAll(slides)
        notifyDataSetChanged()
    }
}