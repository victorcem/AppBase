package com.mymain.appcertificacao.codelab.userinterface.activityintents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.mymain.appcertificacao.R
import com.mymain.appcertificacao.codelab.util.navTo
import com.mymain.appcertificacao.databinding.FragmentSendBinding


class SendFragment : Fragment(R.layout.fragment_send) {

    // Para enviar a Key para o outro Fragment
    companion object {
        const val REPLY = "REPLY"
    }

    private lateinit var binding: FragmentSendBinding
    // Pare receber a Key do outro Fragment
    private var reply: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSendBinding.bind(view)

        // Para pegar o texto e enviar em forma de Key para o outro Fragment
        binding.buttonSend.setOnClickListener {
            val args = Bundle()
            args.putString(ReplyFragment.SEND, binding.inputSend.text.toString())
            navTo(R.id.action_sendFragment_to_replyFragment, args)
        }

        // Obtem a mensagem enviada pelo fragment
        arguments?.let{
            reply = it.getString(REPLY, null)
        }

        // Exibe a mensagem obtida
        reply?.let {
            binding.textMessage.text = it
        }
    }

    // Remove logicamente a Toolbar
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    // Assim que sair do Fragment a Toolbar é exibida
    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}