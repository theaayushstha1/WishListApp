package com.example.wishlist

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var wishlistItems: ArrayList<WishlistItem>
    private lateinit var adapter: WishlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the wishlist items list
        wishlistItems = ArrayList()

        // Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = WishlistAdapter(wishlistItems)
        recyclerView.adapter = adapter

        // Set up input fields
        val itemNameInput = findViewById<EditText>(R.id.itemName)
        val itemPriceInput = findViewById<EditText>(R.id.itemPrice)
        val itemUrlInput = findViewById<EditText>(R.id.itemURL)
        val addButton = findViewById<Button>(R.id.addButton)
        val emptyTextView = findViewById<TextView>(R.id.emptyTextView)

        // Add item to wishlist on button click
        addButton.setOnClickListener {
            val itemName = itemNameInput.text.toString()
            val itemPrice = itemPriceInput.text.toString()
            val itemUrl = itemUrlInput.text.toString()

            if (itemName.isNotEmpty() && itemPrice.isNotEmpty() && itemUrl.isNotEmpty()) {
                val newItem = WishlistItem(itemName, itemPrice, itemUrl)
                wishlistItems.add(newItem)
                adapter.notifyItemInserted(wishlistItems.size - 1)
                itemNameInput.text.clear()
                itemPriceInput.text.clear()
                itemUrlInput.text.clear()

                // Hide empty message
                emptyTextView.visibility = if (wishlistItems.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        // Show the empty message if the list becomes empty
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                emptyTextView.visibility = if (wishlistItems.isEmpty()) View.VISIBLE else View.GONE
            }
        })
    }
}