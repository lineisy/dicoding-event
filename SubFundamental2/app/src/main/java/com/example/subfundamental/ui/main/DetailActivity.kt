package com.example.subfundamental.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.subfundamental.R
import com.example.subfundamental.data.repository.Factory
import com.example.subfundamental.databinding.ActivityDetailBinding
import com.example.subfundamental.ui.response.Event

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory : Factory = Factory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        val idEvent = intent.getIntExtra(EXTRA_ID_EVENT, -1)

        observeViewModel(idEvent)
    }

    private fun observeViewModel(id: Int) {
        viewModel.apply {
            loadDetailEvents(id)
            isLoading.observe(this@DetailActivity) { isLoading ->
                showLoading(isLoading)
            }
            detailEvent.observe(this@DetailActivity) { events ->
                updateUI(events)
            }
            errorMessage.observe(this@DetailActivity) {
                "Failed Fetching Data".showError()
            }
            isFav(id).observe(this@DetailActivity) { isFavorite ->
                changeFavorite(isFavorite)
                binding.btnFavorite.setOnClickListener {
                    if (isFavorite) {
                        viewModel.delFavorite(id)
                        Toast.makeText(this@DetailActivity, "Berhasil menghapus dari favorite", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.addFavorite(detailEvent.value ?: return@setOnClickListener)
                        Toast.makeText(this@DetailActivity, "Berhasil menambahkan ke favorite", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(detail: Event) {
        binding.apply {
            tvEventName.text = detail.name
            tvOrganizer.text = detail.ownerName
            tvEventTime.text = detail.beginTime
            tvRemainingQuota.text = "Sisa Kuota: ${detail.quota - detail.registrants}"
            val description = detail.description
            tvDescription.text = Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY)

            Glide.with(this@DetailActivity)
                .load(detail.imageLogo)
                .into(imageLogo)

            Glide.with(this@DetailActivity)
                .load(detail.mediaCover)
                .into(mediaCover)

            btnOpenLink.setOnClickListener {
                openLink(detail.link)
            }

//            btnFavorite.setOnClickListener {
//                val isFavorite = viewModel.isFav(detail.id).value ?: false
//                if (isFavorite) {
//                    viewModel.delFavorite(detail.id)
//                    Toast.makeText(this@DetailActivity, "Berhasil menghapus dari favorite", Toast.LENGTH_SHORT).show()
//                } else {
//                    viewModel.addFavorite(detail)
//                    Toast.makeText(this@DetailActivity, "Berhasil menambahkan ke favorite", Toast.LENGTH_SHORT).show()
//                }
//                changeFavorite(!isFavorite)
//            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun String.showError() {
        Toast.makeText(this@DetailActivity, this, Toast.LENGTH_SHORT).show()
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_ID_EVENT = "EXTRA_ID_EVENT"
    }

    private fun changeFavorite(isFavorite: Boolean){
        binding.btnFavorite.setImageResource(
            if (isFavorite){
                R.drawable.baseline_favorite_24
            }else{
                R.drawable.ic_love1
            }
        )
    }

}
