package com.leoeutropio.pokemart.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leoeutropio.pokemart.database.converters.Converters
import com.leoeutropio.pokemart.database.dao.ProdutoDao
import com.leoeutropio.pokemart.model.Pedido
import com.leoeutropio.pokemart.model.Produto


@Database(
    version = 2,
    entities = [Produto::class, Pedido::class],
    exportSchema = true

)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao

    companion object {
        @Volatile
        private var db: AppDatabase? = null

        fun instancia(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "pokemart.db"
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}