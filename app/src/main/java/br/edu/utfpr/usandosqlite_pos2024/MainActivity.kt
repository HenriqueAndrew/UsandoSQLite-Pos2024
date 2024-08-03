package br.edu.utfpr.usandosqlite_pos2024

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.usandosqlite_pos2024.database.DatabaseHandler
import br.edu.utfpr.usandosqlite_pos2024.databinding.ActivityMainBinding
import br.edu.utfpr.usandosqlite_pos2024.entity.Cadastro

private const val CODIGO = 0
private const val NOME = 1
private const val TELEFONE = 2

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var banco : DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonListener()

        banco = DatabaseHandler(this)
    }

    private fun setButtonListener() {
        binding.btIncluir.setOnClickListener {
            btIncluirOnClick()
        }

        binding.btAlterar.setOnClickListener {
            btAlterarOnClick()
        }

        binding.btExcluir.setOnClickListener {
            btExcluirOnClick()
        }

        binding.btPesquisar.setOnClickListener {
            btPesquisarOnClick()
        }

        binding.btListar.setOnClickListener {
            btListarOnClick()
        }

    }

    private fun btIncluirOnClick() {
        banco.insert(Cadastro(0, binding.etNome.text.toString(), binding.etTelefone.text.toString()))

        Toast.makeText(this, "Inclusão realizada com sucesso!", Toast.LENGTH_LONG).show()
    }

    private fun btAlterarOnClick() {
        banco.update(Cadastro (binding.etCod.text.toString().toInt(), binding.etNome.text.toString(), binding.etTelefone.text.toString()))

        Toast.makeText(this, "Alteração realizada com sucesso!", Toast.LENGTH_LONG).show()
    }

    private fun btExcluirOnClick() {
        banco.delete(binding.etCod.text.toString().toInt())

        Toast.makeText(this, "Exclusão realizada com sucesso!", Toast.LENGTH_LONG).show()
    }

    private fun btPesquisarOnClick() {
        //Cria cursor para percorrer registros
        val registro = banco.find(binding.etCod.text.toString().toInt())

        //Percorre cursor para imprimir registros
        if (registro != null) {
            binding.etNome.setText(registro.nome)
            binding.etTelefone.setText(registro.telefone)
        }

        Toast.makeText(this, registro.toString(), Toast.LENGTH_LONG).show()
    }

    private fun btListarOnClick() {
       //val cursor = banco.rawQuery("SELECT * FROM cadastro", null)

        //Cria cursor para percorrer registros
        val registro = banco.list()

        //Cria StringBuilder para imprimir registros
        var saida = StringBuilder()

        //imprime registros
        registro.forEach {
            saida.append(it.id)
            saida.append(" - ")
            saida.append(it.nome)
            saida.append(" - ")
            saida.append(it.telefone)
            saida.append("\n")
        }

        Toast.makeText(this, saida.toString(), Toast.LENGTH_LONG).show()
    }

}