package com.example.waveoffood.Feagment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waveoffood.R
import com.example.waveoffood.adaptar.MenuAdaptar
import com.example.waveoffood.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adaptar: MenuAdaptar
    private val orignalMenuFoodName =  listOf("Burger", "Sandwich", "Momo's", "item", "Sandwich", "Momo's", "Sandwich", "Momo's", "item", "Sandwich", "Momo's")
    private val orignalMenuPrice = listOf("$5", "$7", "$8","$18", "$4", "$9", "$7", "$8","$18", "$4", "$9")
    val orignalMenuImage = listOf(
        R.drawable.menu1,
        R.drawable.menu2,
        R.drawable.menu3,
        R.drawable.menu4,
        R.drawable.menu5,
        R.drawable.menu2,
        R.drawable.menu2,
        R.drawable.menu3,
        R.drawable.menu4,
        R.drawable.menu5,
        R.drawable.menu2,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private val filterMenuFoodName = mutableListOf<String>()
    private val filterMenuItemPrice = mutableListOf<String>()
    private val filterMenuImage = mutableListOf<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
//        adaptar = MenuAdaptar(filterMenuFoodName,filterMenuItemPrice, filterMenuImage, requireContext())
        binding.MenuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.MenuRecyclerView.adapter = adaptar

        //setup for search view
        setupSearchView()
        //Show all menu items
        showAllMenu()
        return binding.root
    }

    private fun showAllMenu() {
        filterMenuFoodName.clear()
        filterMenuItemPrice.clear()
        filterMenuImage.clear()

        filterMenuFoodName.addAll(orignalMenuFoodName)
        filterMenuItemPrice.addAll(orignalMenuPrice)
        filterMenuImage.addAll(orignalMenuImage)

        adaptar.notifyDataSetChanged()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query:String): Boolean {
                filterMenuItem(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItem(newText)
                return true
            }
        })
    }

    private fun filterMenuItem(query: String) {
        filterMenuFoodName.clear()
        filterMenuItemPrice.clear()
        filterMenuImage.clear()

        orignalMenuFoodName.forEachIndexed { index, foodName ->
            if(foodName.contains(query, ignoreCase = true)){
                filterMenuFoodName.add(foodName)
                filterMenuItemPrice.add(orignalMenuPrice[index])
                filterMenuImage.add(orignalMenuImage[index])
            }
        }
        adaptar.notifyDataSetChanged()
    }


    companion object {

    }
}
