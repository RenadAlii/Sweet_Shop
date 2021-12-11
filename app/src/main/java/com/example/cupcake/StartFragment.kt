/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentStartBinding
import com.example.cupcake.model.OrderViewModel

/**
 * This is the first screen of the Cupcake app. The user can choose how many cupcakes to order.
 */
class StartFragment : Fragment() {

    // Binding object instance corresponding to the fragment_start.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentStartBinding? = null
    private val sharedViewModel: OrderViewModel by activityViewModels()
    var sweetName = ""
    var sweetImage = ""
    var sweetPrice = 0

    companion object {
        const val NAME = "name"
        const val PRICE = "price"
        const val IMAGE = "image"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            sweetImage = it.getString(IMAGE).toString()
            sweetName = it.getString(NAME).toString()
            sweetPrice = it.getInt(PRICE)
        }

        sharedViewModel.setPrice(sweetPrice)
        sharedViewModel.setName(sweetName)
        binding?.apply {
            // Set up the button click listeners
            //and call the order() fun to make you navigate
            viewModel = sharedViewModel
            startFragment = this@StartFragment
            lifecycleOwner = viewLifecycleOwner
            sweetTitle.text = getString(R.string.order_cupcakes, sweetName)
            orderOneSweet.setText(getString(R.string.one_cupcake, sweetName))
            orderSixSweets.setText(getString(R.string.six_cupcakes, sweetName))
            orderTwelveSweets.setText(getString(R.string.twelve_cupcakes, sweetName))
            image.setImageResource(sweetImage.toInt())
        }
    }

    /**
     * Start an order with the desired quantity of cupcakes and navigate to the next screen.
     */
    fun order(quantity: Int) {

        //set the Quantity before navigate
        sharedViewModel.setQuantity(quantity)
        //if the flavor is not set make it vanilla
        if (sharedViewModel.hasNoFlavorSet()) {
            sharedViewModel.setFlavor(getString(R.string.vanilla))
        }
        //here we navigate from startFragment to flavorFragment
        findNavController().navigate(R.id.action_startFragment_to_flavorFragment)

    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun cancelOrder() {
        sharedViewModel.restOrder()
        findNavController().navigate(R.id.action_flavorFragment_to_sweetsFragment)
    }

}