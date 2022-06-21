package com.mymain.appcertificacao.codelab.datamanagement.roomwithview

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

// +--------------------------------------------------------------------+
// | >>> 1º <<< ENTITY: DEFINIÇÃO DA NOSSA TABELA POR MEIO DE ANOTAÇÕES |
// +--------------------------------------------------------------------+
@Entity(tableName = "word_table")
class Word(@PrimaryKey @ColumnInfo(name = "word") val word: String)

// +-----------------------------------------------------------------------------------+
// >>> 2° <<< DAO: DATA ACCESS OBJECT - OBJETO QUE REALIZA OPERAÇÕES NO BANCO DE DADOS |
// |          ESPECIFICA O QUE PODEREMOS ALTERA NA NOSSA TABELA E FACILITA O USO       |
// +-----------------------------------------------------------------------------------+
@Dao
interface WordDao {
    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): Flow<List<Word>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}

// +-----------------------------------------------------------------------------------------------+
// | >>> 3° <<< REPOSITORY: MEDIADOR DE ACESSO A DADOS LOCAIS OU REMOTO CASO EXISTAM VÁRIAS FONTES /
// +-----------------------------------------------------------------------------------------------+
// Declare O DAO como uma propriedade privada no construtor. Passe apenas o DAO
// em vez do banco de dados inteiro, poruqe você só precisa acessar o DAO
class WordRepository(private val wordDao: WordDao): Repository {

    // Room executa todas as consultas em um thread separado.
    // Flow precisamos implementar qualquer outra coisa para garantir que não estamos fazendo
    // um trabalho de banco de dados de longa duração fora da Thread principal.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

    override fun observeWords(): Flow<List<Word>> {
        return wordDao.getAlphabetizedWords()
    }
}

interface Repository {
    suspend fun insert(word: Word)
    fun observeWords(): Flow<List<Word>>
}

// +----------------------------------------------------------------|
// >>> 4° <<< ROOM - NOSSO BANCO DE DADOS COM DEFINIÇÕES DE TABELAS |
// +--------------------------------------------------------------- |
@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
abstract class WordRoomDataBase : RoomDatabase() {

    abstract fun wordDao() : WordDao
    // +-------------------------------------------------------------------------------------------|
    // >>> 2° <<< CALLBACK - SERA USADA PARA INICIALIZAR O NOSSO BANCO DE DADOS NA HORA DA CRIAÇÃO |
    // +-------------------------------------------------------------------------------------------|

    private class WordDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let{ database ->
                scope.launch {
                    populateDatabase(database.wordDao()) // SO PARA EXEMPLIFICAR COMO PRE-POPULAR UM BANCO DE DADOS
                }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao) {
            // APAGAMOS TUDO QUE ESTEJA NO BANCO PRIMEIRO (NÃO FACA ISSO EM PRODUCÃO)
            wordDao.deleteAll()

            // ADICIONAMOS ALGUNS VALORES ALEATÓRIOS PARA TER ALGO PARA EXIBIR
            wordDao.insert(Word("Algo escrito Aqui"))
            wordDao.insert(Word("outra coisa escrita Aqui"))
        }
    }

    companion object {
        // +---------------------------------------------------------------------------------------------|
        // >>> 1° <<< SINGLETON - PREVINE QUE MULTIPLAS INSTANCIAS DO BANCO SEJAM ABERTAS AO MESMO TEMPO |
        //|                       PADRÃO DE PROJETO QUE SO DEIXA UMA INSTANCIA DO MESMO OBJETO EXISTIR   |
        // +---------------------------------------------------------------------------------------------|
        @Volatile
        private var INSTANCE: WordRoomDataBase? = null

        fun getDatabase(context: Context, scope: CoroutineScope) : WordRoomDataBase {
            // SE O BANCO JA EXISTIR, RETORNE DIRETO, DO CONTRÁRIO CRIE O BANCO
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDataBase::class.java,
                    "word_databse" // NOME DO NOSSO BANCO DE DADOS
                ).addCallback(WordDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// +--------------------------------------------------------------------------|
// | >>> 5° <<< VIEW MODEL: ATUALIZAR DADOS E RETEM A LÓGICA DA UI EM QUESTÃO |
// +--------------------------------------------------------------------------|
class WordViewModel(private val repository: Repository) : ViewModel() {

    // Usar LiveDAta e armazenar em cache o que allWords retorna tem vários beneficios:
    // - Podemos colocar um observador nos dados (em vez de pesquisar as alterações) e apenas
    //  atualizar a UI quando os dados realmente mudam.
    // - O  repositório é completamente separado da IU por meio do ViewModel.
    val allWords: LiveData<List<Word>> = repository.observeWords().asLiveData()

    /** Lançamento de uma nova co-rotina para inserir os dados de forma não bloqueadora */
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}
// +--------------------------------------------------------|
// | >>> 6° <<< MODEL FACTORY: CRIADOR DE OBJETOS COMPLEXOS |
// +--------------------------------------------------------|
class WordViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) return  WordViewModel(repository) as T
        throw IllegalArgumentException("MODELO DESCONHECIDO")
    }
}