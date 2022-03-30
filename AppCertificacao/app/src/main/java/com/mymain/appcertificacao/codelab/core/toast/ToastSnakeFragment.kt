package com.mymain.appcertificacao.codelab.core.toast

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.mymain.appcertificacao.R
import com.mymain.appcertificacao.codelab.util.toast
import com.mymain.appcertificacao.databinding.FragmentToastSnakeBinding


class ToastSnakeFragment : Fragment(R.layout.fragment_toast_snake) {

    private lateinit var binding: FragmentToastSnakeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        binding = FragmentToastSnakeBinding.bind(view)

        binding.toast.setOnClickListener {
            val msg = "Minha Mensagem para você!"
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }
        binding.snake.setOnClickListener {
            Snackbar.make(view, "Oi Snake", Snackbar.LENGTH_SHORT).show()
        }
        binding.snakeAction.setOnClickListener {
            Snackbar
                .make(view, "Snake with Action", Snackbar.LENGTH_SHORT)
                .setAction("Okay") { toast("I'm a snake!")}
                .show()
        }
    }
}