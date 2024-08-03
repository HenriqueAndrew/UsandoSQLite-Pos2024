package br.edu.utfpr.usandosqlite_pos2024

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.usandosqlite_pos2024.adapter.MeuAdapter
import br.edu.utfpr.usandosqlite_pos2024.database.DatabaseHandler
import br.edu.utfpr.usandosqlite_pos2024.databinding.ActivityListarBinding

class ListarActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListarBinding
    private lateinit var banco : DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        banco = DatabaseHandler(this)

        //exemplo fonte de dados - exemplo dados estáticos
        //val registros = listOf<String>("Brasil", "Argentina", "Alemanha", "França")

        //exemplo fonte de dados - dados do banco
        val registros = banco.cursorList()

        //transição de dados para o ListView dados estaticos utilizando ArrayAdapter
        //val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, registros)

        //transição de dados para o ListView dados de banco de dados utilizando SimpleCursorAdapter
        //val adapter = SimpleCursorAdapter(this, R.layout.simple_list_item_2, registros, arrayOf("nome", "telefone"), intArrayOf(R.id.text1, R.id.text2), 0)

        //transição de dados para o ListView dados de banco de dados utilizando MeuAdapter (Adapter criado)
        val adapter = MeuAdapter(this, registros)

        //saída(destino) dos dados
        binding.lvPrincipal.adapter = adapter
    }
}