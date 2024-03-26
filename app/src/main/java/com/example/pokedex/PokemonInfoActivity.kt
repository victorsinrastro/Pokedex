package com.example.pokedex

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pokedex.database.PokemonDatabase
import com.example.pokedex.databinding.ActivityPokemonInfoBinding
import com.example.pokedex.network.models.Stats
import com.example.pokedex.utils.Constants
import com.example.pokedex.viewmodels.PokemonInfoViewModel
import com.example.pokedex.viewmodels.PokemonInfoViewModelFactory
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate


class PokemonInfoActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityPokemonInfoBinding
    private lateinit var pokemonInfoViewModel: PokemonInfoViewModel
    private var barEntries = ArrayList<BarEntry>()
    private val POKEMON_STATS_NAMES = listOf("HP","ATTACK","DEF","SP-ATK","SP-DEF","SPEED")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_pokemon_info)
        val pokemonName = intent.getStringExtra(Constants.POKEMON_NAME) ?: ""
        setUpToolBar(pokemonName)
        val database = PokemonDatabase.getDatabase(this)
        pokemonInfoViewModel = ViewModelProvider(this,PokemonInfoViewModelFactory(database))[PokemonInfoViewModel::class.java]
        pokemonInfoViewModel.fetchPokemonByName(pokemonName)
        observeViewModel()
    }

    private fun setUpToolBar(pokemonName: String) {
        val toolbar = detailBinding.toolbar as Toolbar?
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = pokemonName
        toolbar?.navigationIcon?.mutate()?.let { drawable ->
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.pokemon_yellow))
            toolbar.navigationIcon = drawable
        }
    }

    private fun observeViewModel() {
        pokemonInfoViewModel.pokemon.observe(this) { pokemon ->
            pokemon?.let {
                detailBinding.pokemon = it
                loadPokemonImage(it.imageUrl)
                setUpBarChart(it.stats)
            }
        }
        pokemonInfoViewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpBarChart(stats: List<Stats>) {
        stats.forEachIndexed { index, stat ->
            barEntries.add(BarEntry(index.toFloat(), stat.baseStat.toFloat()))
        }
        val barDataSet = BarDataSet(barEntries, "")
        barDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

        detailBinding.pokemonStatsBarChart.data = BarData(barDataSet)
        detailBinding.pokemonStatsBarChart.xAxis.valueFormatter =
            IndexAxisValueFormatter(POKEMON_STATS_NAMES)
        detailBinding.pokemonStatsBarChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        detailBinding.pokemonStatsBarChart.setFitBars(true)
        detailBinding.pokemonStatsBarChart.axisRight.isEnabled = false
        detailBinding.pokemonStatsBarChart.description.isEnabled = false
        detailBinding.pokemonStatsBarChart.axisLeft.axisMinimum = 0f
        detailBinding.pokemonStatsBarChart.invalidate()

        detailBinding.pokemonStatsBarChart.xAxis.setDrawGridLines(false)
        detailBinding.pokemonStatsBarChart.axisLeft.setDrawGridLines(false)
        detailBinding.pokemonStatsBarChart.axisLeft.isEnabled = false
        detailBinding.pokemonStatsBarChart.legend.isEnabled = false
    }

    private fun loadPokemonImage(imageUrl: String) {
        Glide.with(this@PokemonInfoActivity)
            .load(imageUrl)
            .into(detailBinding.imageViewPokemon)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}