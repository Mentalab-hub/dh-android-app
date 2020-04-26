package com.digital.hospital.chat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.digital.hospital.R
import com.digital.hospital.checkAvailableNetwork
import com.digital.hospital.databinding.ContentChatRoomBinding
import com.ibm.cloud.sdk.core.http.Response
import com.ibm.cloud.sdk.core.security.IamAuthenticator
import com.ibm.watson.assistant.v2.Assistant
import com.ibm.watson.assistant.v2.model.CreateSessionOptions
import com.ibm.watson.assistant.v2.model.MessageInput
import com.ibm.watson.assistant.v2.model.MessageOptions
import com.ibm.watson.assistant.v2.model.SessionResponse

/**
 * @CreatedBy Ali Ahsan
 *
 *         Author Email: this.aliahsan@gmail.com
 *         Created on: 25/04/20
 */

class ChatActivity : AppCompatActivity() {
    private lateinit var mAdapter: ChatAdapter
    private var messageArrayList =  mutableListOf<Message>()
    private var initialRequest = false
    private lateinit var binding: ContentChatRoomBinding
    private lateinit var watsonAssistant: Assistant
    private var watsonAssistantSession: Response<SessionResponse>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContentChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAdapter = ChatAdapter(messageArrayList)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        binding.recyclerView.layoutManager = layoutManager

        binding.recyclerView.adapter = mAdapter

        binding.message.setText("")
        initialRequest = true

        binding.imgClose.setOnClickListener { finish() }

        binding.btnSend.setOnClickListener {
            if (checkAvailableNetwork(this))
                sendMessage()
        }

        createServices()

        sendMessage()
    }

    private fun createServices() {
        watsonAssistant = Assistant("2019-02-28",
                IamAuthenticator(getString(R.string.assistant_apikey)))
        watsonAssistant.serviceUrl = getString(R.string.assistant_url)
    }

    private fun inValidateInput() =  binding.message.text.toString().trim().isEmpty()


    // Sending a message to Watson Assistant Service
    private fun sendMessage() {

        if(inValidateInput() && !initialRequest) {
            binding.message.setText("")
            return
        }

        val message = binding.message.text.toString()

        if (!initialRequest) {
            val inputMessage = Message(message = message)
            messageArrayList.add(inputMessage)

        } else {

            initialRequest = false
        }

        binding.message.setText("")

        mAdapter.notifyDataSetChanged()
        val thread = Thread(Runnable {
            try {
                if (watsonAssistantSession == null) {
                    val call = watsonAssistant.createSession(
                            CreateSessionOptions.Builder().assistantId(getString(R.string.assistant_id)).build())
                    watsonAssistantSession = call.execute()
                }
                val input = MessageInput.Builder()
                        .text(message)
                        .build()
                val options = MessageOptions.Builder()
                        .assistantId(getString(R.string.assistant_id))
                        .input(input)
                        .sessionId(watsonAssistantSession!!.result.sessionId)
                        .build()
                val response = watsonAssistant.message(options).execute()
                Log.i("ChatResponse", "run: " + response.result)
                if (response.result.output != null && response.result.output.generic.isNotEmpty()) {
                    val responses = response.result.output.generic
                    for (r in responses) {
                        var outMessage: Message
                        when (r.responseType()) {
                            "text" -> {
                                outMessage = Message(id = 2L, message = r.text())

                                messageArrayList.add(outMessage)
                            }
                            "option" -> {

                                val title = r.title()
                                val output = StringBuilder()
                                var i = 0
                                while (i < r.options().size) {
                                    val option = r.options()[i]
                                    output.append(option.label).append("\n")
                                    i++
                                }
                                val messageText = """
                                    $title
                                    $output
                                    """.trimIndent()

                                outMessage = Message(id = 2L, message = messageText)
                                messageArrayList.add(outMessage)
                            }

                            else -> Log.e("Error", "Unhandled message type")
                        }
                    }
                    runOnUiThread {
                        mAdapter.notifyDataSetChanged()
                        if (mAdapter.itemCount > 1)
                           binding.recyclerView.layoutManager?.smoothScrollToPosition(
                                   binding.recyclerView, null, mAdapter.itemCount - 1)

                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        thread.start()
    }
}