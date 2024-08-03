package br.edu.utfpr.usandosqlite_pos2024

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.usandosqlite_pos2024.databinding.ActivityMainBinding

private const val codigo = 0
private const val nome = 1
private const val telefone = 2

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var banco : SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonListener()

        banco = SQLiteDatabase.openOrCreateDatabase( this.getDatabasePath("dbfile.sqlite"), null)
        banco.execSQL("CREATE TABLE IF NOT EXISTS cadastro (_id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, telefone TEXT)")

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
        banco.execSQL("INSERT INTO cadastro (nome, telefone) VALUES ('${binding.etNome.text}', '${binding.etTelefone.text}')")

        //Alternativa para INSERT
        //val registro = ContentValues()
        //registro.put("nome", binding.etNome.text.toString())
        //registro.put("telefone", binding.etTelefone.text.toString())
        //banco.insert("cadastro", null, registro)

        Toast.makeText(this, "Inclusão realizada com sucesso!", Toast.LENGTH_LONG).show()
    }

    private fun btAlterarOnClick() {
        val registro = ContentValues()
        registro.put("nome", binding.etNome.text.toString())
        registro.put("telefone", binding.etTelefone.text.toString())

        banco.update("cadastro", registro, "_id =${binding.etCod.text.toString()}", null)

        //Alternativa para UPDATE com possibilidade de incluir mais argumentos
        //banco.update("cadastro", registro, "_id = ?", arrayOf(binding.etCod.text.toString()))

        Toast.makeText(this, "Alteração realizada com sucesso!", Toast.LENGTH_LONG).show()
    }

    private fun btExcluirOnClick() {
        banco.delete("cadastro", "_id = ${binding.etCod.text.toString()}", null)

        Toast.makeText(this, "Exclusão realizada com sucesso!", Toast.LENGTH_LONG).show()
    }

    private fun btPesquisarOnClick() {
        //Cria cursor para percorrer registros
        val registro = banco.query("cadastro", null, "_id = ${binding.etCod.text.toString()}", null, null, null, null)

        //Cria StringBuilder para imprimir registros
        var saida = StringBuilder()

        //Percorre cursor para imprimir registros
        if (registro.moveToNext()) {
            binding.etNome.setText(registro.getString(nome))
            binding.etTelefone.setText(registro.getString(telefone))
        }

        Toast.makeText(this, saida.toString(), Toast.LENGTH_LONG).show()

    }

    private fun btListarOnClick() {
       //val cursor = banco.rawQuery("SELECT * FROM cadastro", null)

        //Cria cursor para percorrer registros
        val registro = banco.query("cadastro", null, null, null, null, null, null)

        //Cria StringBuilder para imprimir registros
        var saida = StringBuilder()

        //Percorre cursor para imprimir registros
        while (registro.moveToNext()) {
            saida.append(registro.getInt(codigo))
            saida.append(" - ")
            saida.append(registro.getString(nome))
            saida.append(" - ")
            saida.append(registro.getString(telefone))
            saida.append("\n")
        }

        Toast.makeText(this, saida.toString(), Toast.LENGTH_LONG).show()
    }








}