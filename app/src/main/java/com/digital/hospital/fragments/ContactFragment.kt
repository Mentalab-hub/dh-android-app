package com.digital.hospital.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.digital.hospital.callDialog
import com.digital.hospital.databinding.ContactFragmentBinding
import com.digital.hospital.chat.ChatActivity

/**
 * @CreatedBy Ali Ahsan
 *
 *         Author Email: this.aliahsan@gmail.com
 *         Created on: 24/04/20
 */

class ContactFragment : Fragment() {

    private var _binding: ContactFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = ContactFragmentBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgClose.setOnClickListener {

            findNavController().popBackStack()
        }

        binding.btnCall.setOnClickListener {

            callDialog(requireActivity())
        }

        binding.btnChat.setOnClickListener {

            requireActivity().startActivity(Intent(context, ChatActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}