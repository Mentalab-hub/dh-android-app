package com.digital.hospital.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.digital.hospital.R
import com.digital.hospital.callDialog
import com.digital.hospital.databinding.HomeFragmentBinding


/**
 * @CreatedBy Ali Ahsan
 *
 *         Author Email: this.aliahsan@gmail.com
 *         Created on: 24/04/20
 */

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = HomeFragmentBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup animation through navOptions
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        binding.cvHealth.setOnClickListener {

            findNavController().navigate(R.id.frag_info, null, options)
        }

        binding.cvSymptom.setOnClickListener {

            findNavController().navigate(R.id.frag_info, null, options)
        }


        binding.cvOperator.setOnClickListener {

            findNavController().navigate(R.id.frag_contact, null, options)
        }

        binding.cvCall.setOnClickListener {

            callDialog(requireActivity())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
