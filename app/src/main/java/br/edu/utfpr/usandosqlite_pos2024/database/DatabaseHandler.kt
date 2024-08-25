package br.edu.utfpr.usandosqlite_pos2024.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.utfpr.usandosqlite_pos2024.entity.Cadastro
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    //--------==============USANDO Firebase==============-------

    val banco = Firebase.firestore

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
        //public const val CODIGO = 0
        //public const val NOME = 1
        //public const val TELEFONE = 2
    }

    fun insert(cadastro : Cadastro){
        //val db = this.writableDatabase

        val registro = hashMapOf(
        "_id" to cadastro._id,
        "nome" to cadastro.nome,
        "telefone" to cadastro.telefone
        )

        banco.collection("cadastro")
            .document(cadastro._id.toString()).set(registro)
            .addOnSuccessListener {println("Sucesso")}
            .addOnFailureListener{e ->
                println ("Erro${e.message}")}

    }

    fun update(cadastro: Cadastro){

        val registro = hashMapOf(
            "_id" to cadastro._id,
            "nome" to cadastro.nome,
            "telefone" to cadastro.telefone
        )

        banco.collection("cadastro")
            .document(cadastro._id.toString()).set(registro)
            .addOnSuccessListener {println("Sucesso")}
            .addOnFailureListener{e ->
                println ("Erro${e.message}")}

    }

    fun delete(id : Int){
        banco.collection("cadastro")
            .document(id.toString()).delete()
            .addOnSuccessListener {println("Sucesso")}
            .addOnFailureListener{e ->
                println ("Erro${e.message}")}
    }

    /*
    fun find(id : Int) : Cadastro?{

        banco.collection( "cadastro" )
            .whereEqualTo( "_id", id )
            .get()
            .addOnSuccessListener { result ->
                val registro = result.documents.get(0)

                val cadastro = Cadastro(
                    registro.data?.get( "_id" ).toString().toInt(),
                    registro.data?.get( "nome" ).toString(),
                    registro.data?.get( "telefone" ).toString()
                )
            }
            .addOnFailureListener { e ->
                println( "Erro${e.message}")
            }

        return null

    }
     */

    /*

    fun list() : MutableList<Cadastro>{

        var registros= mutableListOf<Cadastro>()

        banco.collection("cadastro")
            .get()
            .addOnSuccessListener { result ->
                for (document in result){
                    val cadastro = Cadastro(document.data.get("_id").toString().toInt(), document.data.get("nome").toString(), document.data.get("telefone").toString())
                    registros.add(cadastro)
                }
            }
            .addOnFailureListener{e ->
                println ("Erro${e.message}")}

        return registros
    }


*/


/*
    //--------==============USANDO SQLITE==============-------
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
        public const val CODIGO = 0
        public const val NOME = 1
        public const val TELEFONE = 2
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

        //db.close()

    }

    fun update(cadastro: Cadastro){
        val db = this.writableDatabase

        val registro = ContentValues()
        registro.put("nome", cadastro.nome)
        registro.put("telefone", cadastro.telefone)

        db.update(TABLE_NAME, registro, "_id =${cadastro._id}", null)
        //db.close()

        //Alternativa para UPDATE com possibilidade de incluir mais argumentos
        //banco.update("cadastro", registro, "_id = ?", arrayOf(binding.etCod.text.toString()))

    }

    fun delete(id : Int){
        val db = this.writableDatabase

        db.delete(TABLE_NAME, "_id = ${id}", null)
        //db.close()
    }

    fun find(id : Int) : Cadastro?{
        val db = this.writableDatabase

        val registro = db.query(TABLE_NAME, null, "_id = ${id}", null, null, null, null)

        if (registro.moveToNext()){
            val cadastro = Cadastro(id, registro.getString(NOME), registro.getString(TELEFONE))
            return cadastro
        } else {
            return null
        }

    }

    fun list() : MutableList<Cadastro>{
        //val cursor = db.rawQuery("SELECT * FROM cadastro", null)

        val db = this.writableDatabase

        val registro = db.query(TABLE_NAME, null, null, null, null, null, null)

        val registros= mutableListOf<Cadastro>()

        while (registro.moveToNext()){
            val cadastro = Cadastro(registro.getInt(CODIGO), registro.getString(NOME), registro.getString(TELEFONE))
            registros.add(cadastro)
        }
        return registros
    }

    fun cursorList() : Cursor {
        val db = this.writableDatabase
        val registro = db.query(TABLE_NAME, null, null, null, null, null, null)

        return registro
    }

*/


}