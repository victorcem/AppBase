package com.mymain.appcertificacao.codelab.core.workmanager

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.mymain.appcertificacao.R
import com.mymain.appcertificacao.codelab.util.navTo
import com.mymain.appcertificacao.databinding.FragmentSelectImageBinding
import java.util.jar.Manifest

class SelectImageFragment : Fragment(R.layout.fragment_select_image) {

    // ATUALIZADO
    companion object {
        private const val KEY_PERMISSIONS_GRANTED = "KEY_PERMISSIONS_GRANTED"
        private const val KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT"
        private const val MAX_NUMBER_REQUEST_PERMISSIONS = 2
    }

    private var permissionRequestCount: Int = 0
    private lateinit var binding: FragmentSelectImageBinding
    private lateinit var launcher: ActivityResultLauncher<String>

    private var userHasPermission = false
    private val permissions = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Deve ser definido ou no onAttach ou no onCreate
        launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
            handleImageRequestResult(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSelectImageBinding.bind(view)

        // Recuperando o ultimo estado, caso o usuário retacione o aparelho
        savedInstanceState?.let {
            permissionRequestCount = it.getInt(KEY_PERMISSIONS_REQUEST_COUNT, 0)
            userHasPermission = it.getBoolean(KEY_PERMISSIONS_GRANTED, false)
        }

        // Asseguresse que o usuário tens as permissões necessárias
        requestPermissionsOnlyTwice(userHasPermission)

        // Abrir o file chooser
        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }
    }

    private fun requestPermissionsOnlyTwice(hasPermissionsAlready: Boolean) {
        if (!hasPermissionsAlready) {
            if(permissionRequestCount < MAX_NUMBER_REQUEST_PERMISSIONS) {
                permissionRequestCount += 1
                // NOVA API: PRESTA A ATENÇÃO AGORA
                val permissionChecker = registerForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) {
                    acceptedPermissions ->
                    val permissionIdentified = acceptedPermissions.all { it.key in permissions }
                    val permissionGrant = acceptedPermissions.all { it.value == true }
                    if(permissionIdentified && permissionGrant) {
                        permissionRequestCount = 0
                        userHasPermission = true
                    }
                }
                if(!userHasPermission){
                    permissionChecker.launch(permissions)
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    R.string.set_permissions_in_settings,
                    Toast.LENGTH_LONG
                ).show()
                binding.selectImage.isEnabled = false
            }
        }
    }

    private fun handleImageRequestResult(uri: Uri){
        // Navegar para proxima etapa onde exibimos as opções de blur
        navTo(R.id.blurFragment, bundleOf(Pair(KEY_IMAGE_URI, uri.toString())))
        //Forma antiga
        //findNavController().navigate(R.id.blurFragment, bundleOf(Pair(KEY_IMAGE_URI, uri.toString())))
    }

    // Salvar estados em caso que o usuario rotacione o aparelho
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_PERMISSIONS_REQUEST_COUNT, permissionRequestCount)
        outState.putBoolean(KEY_PERMISSIONS_GRANTED, userHasPermission)
    }
}