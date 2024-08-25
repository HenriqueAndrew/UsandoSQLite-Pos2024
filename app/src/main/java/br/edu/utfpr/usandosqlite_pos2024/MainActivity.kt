package br.edu.utfpr.usandosqlite_pos2024

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.usandosqlite_pos2024.database.DatabaseHandler
import br.edu.utfpr.usandosqlite_pos2024.databinding.ActivityMainBinding
import br.edu.utfpr.usandosqlite_pos2024.entity.Cadastro
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var banco : DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonListener()

        //binding.etCod.setEnabled(false)

        //trazer dados da intent para a tela se for editar se não trazer vazio para novo cadastro
        if(intent.getIntExtra("cod", 0) != 0) {
            binding.etCod.setText(intent.getIntExtra("cod", 0).toString())
            binding.etNome.setText(intent.getStringExtra("nome"))
            binding.etTelefone.setText(intent.getStringExtra("telefone"))
        }else{
            binding.btExcluir.visibility = View.GONE
            binding.btPesquisar.visibility = View.GONE
        }

        banco = DatabaseHandler(this)

        System.out.println("onCreate")
    }

    private fun setButtonListener() {

        binding.btSalvar.setOnClickListener {
            btSalvarOnClick()
        }

        binding.btExcluir.setOnClickListener {
            btExcluirOnClick()
        }

        binding.btPesquisar.setOnClickListener {
            btPesquisarOnClick()
        }

    }

    /*
    private fun btIncluirOnClick() {
        banco.insert(Cadastro(0, binding.etNome.text.toString(), binding.etTelefone.text.toString()))

        Toast.makeText(this, "Inclusão realizada com sucesso!", Toast.LENGTH_LONG).show()
    }
    */

    private fun btSalvarOnClick() {
        if (binding.etCod.text.isEmpty()) {
            banco.insert(
                Cadastro(
                    0,
                    binding.etNome.text.toString(),
                    binding.etTelefone.text.toString()
                )
            )
            Toast.makeText(this, "Inclusão realizada com sucesso!", Toast.LENGTH_LONG).show()
        } else {
            banco.update(
                Cadastro(
                    binding.etCod.text.toString().toInt(),
                    binding.etNome.text.toString(),
                    binding.etTelefone.text.toString()
                )
            )
            Toast.makeText(this, "Alteração realizada com sucesso!", Toast.LENGTH_LONG).show()
        }

        finish()
    }

    private fun btExcluirOnClick() {
        banco.delete(binding.etCod.text.toString().toInt())
        Toast.makeText(this, "Exclusão realizada com sucesso!", Toast.LENGTH_LONG).show()

        finish()
    }

    private fun btPesquisarOnClick() {
        val builder = AlertDialog.Builder(this)

        val etCodPesquisar = EditText(this)

        builder.setTitle("Informe o código para pesquisa")
        builder.setView(etCodPesquisar)
        builder.setCancelable(false)
        builder.setNegativeButton("Fechar", null)
        builder.setPositiveButton("Pesquisar", DialogInterface.OnClickListener { dialogInterface, id ->

            val banco = Firebase.firestore

            banco.collection( "cadastro" )
                .whereEqualTo( "_id", etCodPesquisar.text.toString().toInt() )
                .get()
                .addOnSuccessListener { result ->
                    val registro = result.documents.get(0)

                    binding.etCod.setText( etCodPesquisar.text.toString() )
                    binding.etNome.setText( registro.data?.get( "nome" ).toString() )
                    binding.etTelefone.setText( registro.data?.get( "telefone" ).toString() )

                }
                .addOnFailureListener { e ->
                    println( "Erro${e.message}")
                }

        }
        )
            /*Cria cursor para percorrer registros
            //val registro = banco.find(binding.etCod.text.toString().toInt())

            //Cria cursor para percorrer registros
            val registro = banco.find(binding.etCod.text.toString().toInt())

            //Percorre cursor para imprimir registros
            if (registro != null) {
                binding.etCod.setText(etCodPesquisar.text.toString())
                binding.etNome.setText(registro.nome)
                binding.etTelefone.setText(registro.telefone)
            } else {
                Toast.makeText(this, "Registro não encontrado!", Toast.LENGTH_LONG).show()
            }
        }) */

        builder.show()
    }

    //private fun btListarOnClick() {
        /*

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

        */

        //Cria intent para listar registros
        //val intent = Intent(this, ListarActivity::class.java)
        //startActivity(intent)
    //}

    override fun onStart() {
        super.onStart()

        System.out.println("onStart")
    }

    override fun onResume() {
        super.onResume()
        System.out.println("onResume")
    }

    override fun onPause() {
        super.onPause()
        System.out.println("onPause")
    }

    override fun onStop() {
        super.onStop()
        System.out.println("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        System.out.println("onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        System.out.println("onRestart")

    }
}