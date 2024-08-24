package br.edu.utfpr.usandosqlite_pos2024.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import br.edu.utfpr.usandosqlite_pos2024.MainActivity
import br.edu.utfpr.usandosqlite_pos2024.R
import br.edu.utfpr.usandosqlite_pos2024.entity.Cadastro

//------------------------USANDO MutableList (Base de dados Firebase)----------------------------------

class MeuAdapter(val context: Context, val registros : MutableList<Cadastro>) : BaseAdapter() {

    override fun getCount(): Int {
        return registros.size
    }

    override fun getItem(position: Int): Any {
        val cadastro = registros.get(position)
        return cadastro
    }

    override fun getItemId(position: Int): Long {
        val cadastro = registros.get(position)
        return cadastro._id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        //inflar elemento lista
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.elemento_lista, null)

        //inflar elementos da lista
        val tvNomeElementoLista = v.findViewById<TextView>(R.id.tvNomeElementoLista)
        val tvTelefoneElementoLista = v.findViewById<TextView>(R.id.tvTelefoneElementoLista)
        val btEditarElementoLista = v.findViewById<ImageButton>(R.id.btEditarElementoLista)

        //recupera posição do cursor
        val cadastro = registros.get(position)

        //preencher elementos da lista
        tvNomeElementoLista.setText(cadastro.nome)
        tvTelefoneElementoLista.setText(cadastro.telefone)

        btEditarElementoLista.setOnClickListener{
            val cadastro = registros.get(position)
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("id", cadastro._id)
            intent.putExtra("nome", cadastro.nome)
            intent.putExtra("telefone", cadastro.telefone)
            context.startActivity(intent)
        }

        return v
    }

    /*
    //------------------------USANDO CURSOR----------------------------------

    class MeuAdapter(val context: Context, val cursor: Cursor) : BaseAdapter() {

        override fun getCount(): Int {
            return cursor.count
        }

        override fun getItem(position: Int): Any {
            cursor.moveToPosition(position)
            val cadastro = Cadastro(cursor.getInt(CODIGO), cursor.getString(NOME), cursor.getString(TELEFONE))
            return cadastro
        }

        override fun getItemId(position: Int): Long {
            cursor.moveToPosition(position)
            return cursor.getLong(CODIGO)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            //inflar elemento lista
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v = inflater.inflate(R.layout.elemento_lista, null)

            //inflar elementos da lista
            val tvNomeElementoLista = v.findViewById<TextView>(R.id.tvNomeElementoLista)
            val tvTelefoneElementoLista = v.findViewById<TextView>(R.id.tvTelefoneElementoLista)
            val btEditarElementoLista = v.findViewById<ImageButton>(R.id.btEditarElementoLista)

            //recupera posição do cursor
            cursor.moveToPosition(position)

            //preencher elementos da lista
            tvNomeElementoLista.text = cursor.getString(NOME)
            tvTelefoneElementoLista.text = cursor.getString(TELEFONE)

            btEditarElementoLista.setOnClickListener{
                cursor.moveToPosition(position)
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("id", cursor.getInt(CODIGO))
                intent.putExtra("nome", cursor.getString(NOME))
                intent.putExtra("telefone", cursor.getString(TELEFONE))
                context.startActivity(intent)
            }

            return v
        }

    */
}