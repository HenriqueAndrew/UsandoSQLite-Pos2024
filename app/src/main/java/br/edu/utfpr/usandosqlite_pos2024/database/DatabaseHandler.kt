package br.edu.utfpr.usandosqlite_pos2024.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.utfpr.usandosqlite_pos2024.entity.Cadastro

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS ${TABLE_NAME} (_id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, telefone TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME}")
        onCreate(db)
    }

    companion object{
        private const val DATABASE_NAME = "dbfile.sqlite"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "cadastro"
        private const val CODIGO = 0
        private const val NOME = 1
        private const val TELEFONE = 2
    }

    fun insert(cadastro : Cadastro){
        val db = this.writableDatabase

        val registro = ContentValues()
        registro.put("nome", cadastro.nome)
        registro.put("telefone", cadastro.telefone)

        db.insert(TABLE_NAME, null, registro)

        //Alternativa para INSERT
        //val registro = ContentValues()
        //registro.put("nome", binding.etNome.text.toString())
        //registro.put("telefone", binding.etTelefone.text.toString())
        //banco.insert("cadastro", null, registro)

        db.close()

    }

    fun update(cadastro: Cadastro){
        val db = this.writableDatabase

        val registro = ContentValues()
        registro.put("nome", cadastro.nome)
        registro.put("telefone", cadastro.telefone)

        db.update(TABLE_NAME, registro, "_id =${cadastro.id}", null)
        db.close()

        //Alternativa para UPDATE com possibilidade de incluir mais argumentos
        //banco.update("cadastro", registro, "_id = ?", arrayOf(binding.etCod.text.toString()))

    }

    fun delete(id : Int){
        val db = this.writableDatabase

        db.delete(TABLE_NAME, "_id = ${id}", null)
        db.close()
    }

    fun find(id : Int) : Cadastro?{
        val db = this.readableDatabase

        val registro = db.query(TABLE_NAME, null, "_id = ${id}", null, null, null, null)

        if (registro.moveToNext()){
            val cadastro = Cadastro(id, registro.getString(NOME), registro.getString(TELEFONE))
            return cadastro
        } else {
            return null
        }

    }

    fun list() : MutableList<Cadastro>{
        val db = this.readableDatabase

        val registro = db.query(TABLE_NAME, null, null, null, null, null, null)

        val registros= mutableListOf<Cadastro>()

        while (registro.moveToNext()){
            val cadastro = Cadastro(registro.getInt(CODIGO), registro.getString(NOME), registro.getString(TELEFONE))
            registros.add(cadastro)
        }
        return registros
        }
}