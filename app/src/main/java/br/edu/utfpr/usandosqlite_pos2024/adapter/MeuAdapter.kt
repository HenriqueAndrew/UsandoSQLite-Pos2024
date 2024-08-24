package br.edu.utfpr.usandosqlite_pos2024.adapter

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import br.edu.utfpr.usandosqlite_pos2024.MainActivity
import br.edu.utfpr.usandosqlite_pos2024.R
import br.edu.utfpr.usandosqlite_pos2024.database.DatabaseHandler.Companion.CODIGO
import br.edu.utfpr.usandosqlite_pos2024.database.DatabaseHandler.Companion.NOME
import br.edu.utfpr.usandosqlite_pos2024.database.DatabaseHandler.Companion.TELEFONE
import br.edu.utfpr.usandosqlite_pos2024.entity.Cadastro

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
}