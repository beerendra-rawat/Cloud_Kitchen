package com.example.waveoffood.Feagment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.waveoffood.adaptar.BuyAgainAdaptar
import com.example.waveoffood.databinding.FragmentHistoryBinding
import com.example.waveoffood.model.OrderDetails
import com.example.waveoffood.recentOrderItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdaptar: BuyAgainAdaptar

    private lateinit var database :FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId:String
    private var listOfOderItem: MutableList<OrderDetails> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)

        auth =FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        //Retrieve and display the user order hisyory
        retrieveBuyhistory()

        binding.recentBuyItem.setOnClickListener {
            seeItemsRecentBuy()
        }
        return binding.root
    }

    private fun seeItemsRecentBuy() {
        listOfOderItem.firstOrNull()?.let { recentBuy ->
            val intent = Intent(requireContext(),recentOrderItems::class.java)
            //intent.putExtra("RecentBuyOrderItem",recentBuy)
            intent.putExtra("RecentBuyOrderItem", listOfOderItem)
            startActivity(intent)
        }
    }

    private fun retrieveBuyhistory() {
        binding.recentBuyItem.visibility = View.INVISIBLE
        userId = auth.currentUser?.uid?:""
        val buyItemReference: DatabaseReference = database.reference.child("user").child(userId).child("BuyHistory")
        val shortingQuery = buyItemReference.orderByChild("currentTime")

        shortingQuery.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapshot in snapshot.children){
                    val buyHistoryItem = buySnapshot.getValue(OrderDetails::class.java)
                    buyHistoryItem?.let {
                        listOfOderItem.add(it)
                    }
                }
                listOfOderItem.reverse()
                if(listOfOderItem.isNotEmpty()){
                    setDataInRecentBuyItem()
                    setPreviousBuyItemsRecyclerView()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    private fun setDataInRecentBuyItem() {
        binding.recentBuyItem.visibility = View.VISIBLE
        val recentOderItem = listOfOderItem.firstOrNull()
        recentOderItem?.let {
            with(binding){
                buyAgainFoodName.text = it.foodName?.firstOrNull()?:""
                buyAgainFoodPrice.text = it.foodPrice?.firstOrNull()?:""
                val image = it.foodImage?.firstOrNull()?:""
                val uri = Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(buyAgainFoodImage)

                listOfOderItem.reverse()
                if(listOfOderItem.isNotEmpty()){

                }
            }
        }
    }

    private fun setPreviousBuyItemsRecyclerView() {
        val buyAgainFoodName = mutableListOf<String>()
        val buyAgainFoodPrice = mutableListOf<String>()
        val buyAgainFoodImage = mutableListOf<String>()
        for (i in 1 until listOfOderItem.size) {
            listOfOderItem[i].foodName?.firstOrNull()?.let {
                buyAgainFoodName.add(it)
                listOfOderItem[i].foodPrice?.firstOrNull()?.let {
                    buyAgainFoodPrice.add(it)
                    listOfOderItem[i].foodImage?.firstOrNull()?.let {
                        buyAgainFoodImage.add(it)
                    }
                }
                val rv = binding.BuyAgainRecyclerView
                rv.layoutManager = LinearLayoutManager(requireContext())
                buyAgainAdaptar = BuyAgainAdaptar(
                    buyAgainFoodName,
                    buyAgainFoodPrice,
                    buyAgainFoodImage,
                    requireContext()
                )
                rv.adapter = buyAgainAdaptar
            }
        }
    }
}

private fun Parcelable.putExtra(s: String, listOfOderItem: MutableList<OrderDetails>) {
}
