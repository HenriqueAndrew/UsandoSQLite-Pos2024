package br.edu.utfpr.usandosqlite_pos2024

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.usandosqlite_pos2024.adapter.MeuAdapter
import br.edu.utfpr.usandosqlite_pos2024.database.DatabaseHandler
import br.edu.utfpr.usandosqlite_pos2024.databinding.ActivityListarBinding
import br.edu.utfpr.usandosqlite_pos2024.entity.Cadastro
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ListarActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListarBinding
    private lateinit var banco : DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        banco = DatabaseHandler(this)

        binding.btIncluir.setOnClickListener {
            btIncluirOnClick()
        }

        //exemplo fonte de dados - exemplo dados estáticos
        //val registros = listOf<String>("Brasil", "Argentina", "Alemanha", "França")

        //exemplo fonte de dados - dados do banco
        //val registros = banco.cursorList()

        //transição de dados para o ListView dados estaticos utilizando ArrayAdapter
        //val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, registros)

        //transição de dados para o ListView dados de banco de dados utilizando SimpleCursorAdapter
        //val adapter = SimpleCursorAdapter(this, R.layout.simple_list_item_2, registros, arrayOf("nome", "telefone"), intArrayOf(R.id.text1, R.id.text2), 0)

        //transição de dados para o ListView dados de banco de dados utilizando MeuAdapter (Adapter criado)
        //val adapter = MeuAdapter(this, registros)

        //saída(destino) dos dados
        //binding.lvPrincipal.adapter = adapter
    }

    private fun btIncluirOnClick() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()

        val banco = Firebase.firestore

        //var registros = mutableListOf<Cadastro>()

        banco.collection( "cadastro" )
            .get()
            .addOnSuccessListener { result ->
                var registros = mutableListOf<Cadastro>()
                for ( document in result ) {
                    val cadastro = Cadastro(
                        document.data.get( "_id" ).toString().toInt(),
                        document.data.get( "nome" ).toString(),
                        document.data.get( "telefone" ).toString()
                    )
                    registros.add( cadastro )
                }

                //transição de dados para o ListView dados de banco de dados utilizando MeuAdapter (Adapter criado)
                val adapter = MeuAdapter(this, registros)

                //saída(destino) dos dados
                binding.lvPrincipal.adapter = adapter
            }
            .addOnFailureListener{e ->
                println ("Erro${e.message}")}

        //return registros

        //exemplo fonte de dados - exemplo dados estáticos
        //val registros = banco.cursorList()


    }
}