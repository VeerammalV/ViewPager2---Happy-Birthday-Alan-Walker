package com.example.viewpager2

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.viewpager2.databinding.ActivityMainBinding
import kotlin.math.abs

class MainActivity : AppCompatActivity(), SliderAdapter.OnItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager2: ViewPager2
//    private var dotsIndicator: SpringDotsIndicator
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var slides: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewPager2 = binding.image
//        dotsIndicator = binding.dotsIndicator

        imageSlider()
        setUpTransformer()
        viewPagerCallBack()
        autoSlide()
    }

    private fun viewPagerCallBack() {
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == sliderAdapter.itemCount - 1) {
                    viewPager2.post {
                        viewPager2.setCurrentItem(1, false)
                    }
                } else if (position == 0) {
                    viewPager2.post {
                        viewPager2.setCurrentItem(sliderAdapter.itemCount - 2, false)
                    }
                }
            }
        })
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager2.setPageTransformer(transformer)
    }

    private fun imageSlider() {
        slides = ArrayList()
        slides.add(R.drawable.a)
        slides.add(R.drawable.b)
        slides.add(R.drawable.c)
        slides.add(R.drawable.d)
        slides.add(R.drawable.e)
        slides.add(R.drawable.f)
        slides.add(R.drawable.g)
        slides.add(R.drawable.h)
        slides.add(R.drawable.i)
        slides.add(R.drawable.j)
        slides.add(R.drawable.k)
        slides.add(R.drawable.l)
        slides.add(R.drawable.m)
        slides.add(R.drawable.n)
        slides.add(R.drawable.o)
        slides.add(R.drawable.happy_birthday)
        slides.add(R.drawable.p)

        sliderAdapter = SliderAdapter(slides, viewPager2, this)
        viewPager2.adapter = sliderAdapter

        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun autoSlide() {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                val nextItem = viewPager2.currentItem + 1
                viewPager2.setCurrentItem(nextItem, true)
                handler.postDelayed(this, 3000) // 3 seconds delay
            }
        }
        handler.post(runnable)
    }

    override fun onClick(position: Int) {}

}