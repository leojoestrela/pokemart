package com.leoeutropio.pokemart.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.leoeutropio.pokemart.PrefConfig
import com.leoeutropio.pokemart.R
import com.leoeutropio.pokemart.database.AppDatabase
import com.leoeutropio.pokemart.model.Produto

class LoadingScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)

        carregaProdutosLimpaCarrinho()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun carregaProdutosLimpaCarrinho() {
        val db = AppDatabase.instancia(this)
        val produtoDao = db.produtoDao()

        if (produtoDao.buscaTodosProdutos().isEmpty()) {
            produtoDao.salvaProduto(produtos())
        }

        PrefConfig.clearPref(this)
    }

    private fun produtos(): List<Produto> {
        return listOf(
            Produto(
                0L,
                "Poke ball",
                "The Poké Ball is a type of Poké Ball introduced in Generation I. It is the most basic form of Poké Ball, an item used to catch a wild Pokémon.",
                1,
                "100",
                R.drawable.ic_pokeball
            ),
            Produto(
                0L,
                "Great Ball",
                "The Great Ball is a type of Poké Ball introduced in Generation I. It is an improved variant of the regular Poké Ball that can be used to catch wild Pokémon.",
                1,
                "150",
                R.drawable.ic_greatball
            ),
            Produto(
                0L,
                "Potion",
                "The Potion is a type of medicine introduced in Generation I.\n" +
                        "\n" +
                        "It has four improved counterparts: Super Potion, Hyper Potion, Max Potion, and Full Restore.",
                1,
                "50",
                R.drawable.ic_potion
            ),
            Produto(
                0L,
                "Rare Candy",
                "When used from the Bag on a Pokémon, it increases that Pokémon's level by one, up to level 100. After leveling up, it will have the minimum required experience for its current level. It can only be used outside of battle.",
                1,
                "500",
                R.drawable.ic_rarecandy
            ),
            Produto(
                0L,
                "Antidote",
                "The Antidote is a type of status condition healing medicine introduced in Generation I. It cures a Pokémon of poison.",
                1,
                "125",
                R.drawable.ic_antidote
            ),
        )
    }
}