package com.cacttuseducation.lorikshabani_project_2.ui.blog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cacttuseducation.lorikshabani_project_2.R
import com.cacttuseducation.lorikshabani_project_2.`object`.Singleton
import com.cacttuseducation.lorikshabani_project_2.adapter.ItemAdapter
import com.cacttuseducation.lorikshabani_project_2.databinding.FragmentBlogBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BlogFragment : Fragment() {

    private lateinit var binding: FragmentBlogBinding
    private val items = mutableListOf<Item>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBlogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showList()
        addItems()
        logoutUser()
    }


    private fun addItems() {
        Item(
            "Apple iPhone 14",
            5,
            999.99,
            "https://justbeyourselfblog.com/wp-content/uploads/2022/09/iphone_14_PNG41-500x450.png"
        )
        items.add(
            Item(
                "Apple iPad Pro",
                5,
                799.99,
                "https://static.skyassets.com/contentstack/assets/blt143e20b03d72047e/blt8937265d6aa0575b/622a7dd8eda9a1043584adcb/Carousel_iPad_Blue_Placement02.png"
            )
        )
        items.add(
            Item(
                "Apple MacBook Air",
                5,
                1499.99,
                "https://strgimgr.umico.az/sized/1680/350753-e75169a25f6fd912f3cd964ee77b12ce.jpg"
            )
        )
        items.add(
            Item(
                "Apple AirPods Pro",
                5,
                249.99,
                "https://www.intirof.com/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/a/p/apple_airpods_3_1.png"
            )
        )
        items.add(
            Item(
                "Apple Watch Series 6",
                3,
                399.99,
                "https://i0.wp.com/signalsjo.com/wp-content/uploads/2022/07/s6-1.png?fit=800%2C993&ssl=1"
            )
        )
        items.add(Item("Apple iMac 2001", 4, 1699.99, "https://img.cgaxis.com/2021/12/imac_a.webp"))
        items.add(
            Item(
                "Apple Tv",
                3,
                199.99,
                "https://help.apple.com/assets/634758A2A323C20D855FF144/634758A5A323C20D855FF155/en_US/456e59e4b93623de07daa50e33871049.png"
            )
        )
    }

    private fun showList() {
        binding.rvItems.layoutManager = LinearLayoutManager(context)
        val adapter = ItemAdapter(items)
        binding.rvItems.adapter = adapter
    }

    private fun logoutUser() {
        binding.btnLogout.setOnClickListener {
            val loader = binding.progressBar
            loader.visibility = View.VISIBLE

            val sharedPref = activity?.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            sharedPref?.edit()?.putBoolean("is_logged_in", false)?.apply()
            val token = sharedPref?.getString("token", null)!!
            val logoutCall = Singleton.provideRetrofitInstance().logout(token)
            logoutCall.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful && response.code() == 200) {
                        loader.visibility = View.GONE
                        Snackbar.make(requireView(), "Logged Out", Snackbar.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_nav_blog_to_nav_login)
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("error", "${t.message}")
                    loader.visibility = View.GONE
                }

            })
        }

    }


    data class Item(val name: String, val rating: Int, val price: Double, val image: String)

    fun addItem(name: String, rating: Int, price: Double, image: String) {
        items.add(Item(name, rating, price, image))
    }
}