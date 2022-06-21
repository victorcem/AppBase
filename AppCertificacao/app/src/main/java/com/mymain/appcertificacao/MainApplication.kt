package com.mymain.appcertificacao

import android.app.Application
import com.mymain.appcertificacao.codelab.datamanagement.roomwithview.WordRepository
import com.mymain.appcertificacao.codelab.datamanagement.roomwithview.WordRoomDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

class MainApplication : Application(){
    // NÃO HÁ NECESSIDADE DE CANCELAR ESTE ESCOPO, POIS ELE SERÁ DESTRUÍDO COM O PROCESSO
    private val applicationScope = CoroutineScope(SupervisorJob())

    // CRIACAO DO NOSSO BANCO DE DADOS LAZY (SO SERA INSTANCIADO QUANDO FOR USADO PELA PRIMEIRA VEZ)
    private val dataBase by lazy { WordRoomDataBase.getDatabase(this@MainApplication, applicationScope) }

    // DEFINICAO DO NOSSO REPOSITORIO A NIVEL DE APLICACAO PARA FICAR DISPONIVEL EM TODOS OS LUGARES
    val repository by lazy { WordRepository(dataBase.wordDao()) }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {

            Timber.plant(Timber.DebugTree())
        }
    }
}