package com.adndevelopersoftware.viajeroconductor.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.adndevelopersoftware.viajeroconductor.databinding.ActivityHistoriesDetailBinding
import com.adndevelopersoftware.viajeroconductor.models.Client
import com.adndevelopersoftware.viajeroconductor.models.History
import com.adndevelopersoftware.viajeroconductor.providers.ClientProvider
import com.adndevelopersoftware.viajeroconductor.providers.HistoryProvider
import com.adndevelopersoftware.viajeroconductor.utils.RelativeTime
import com.bumptech.glide.Glide

class HistoriesDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoriesDetailBinding

    private var historyProvider = HistoryProvider()
    private var clientProvider = ClientProvider()
    private var extraId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoriesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        extraId = intent.getStringExtra("id")!!
        getHistory()

        binding.imageViewBack.setOnClickListener { finish() }
    }

    private fun getHistory() {
        historyProvider.getHistoryById(extraId).addOnSuccessListener { document ->

            if (document.exists()) {
                val history = document.toObject(History::class.java)
                binding.textViewOrigin.text = history?.origin
                binding.textViewDestination.text = history?.destination
                binding.textViewDate.text = RelativeTime.getTimeAgo(history?.timestamp!!, this@HistoriesDetailActivity)
                binding.textViewPrice.text = "${String.format("%.1f", history?.price)}$"
                binding.textViewMyCalification.text = "${history?.calificationToDriver}"
                binding.textViewClientCalification.text = "${history?.calificationToClient}"
                binding.textViewTimeAndDistance.text = "${history?.time} Min - ${String.format("%.1f", history?.km)} Km"
                getClientInfo(history?.idClient!!)
            }

        }
    }

    private fun getClientInfo(id: String) {
        clientProvider.getClientById(id).addOnSuccessListener { document ->
            if (document.exists()) {
                val client = document.toObject(Client::class.java)
                binding.textViewEmail.text = client?.email
                binding.textViewName.text = "${client?.name} ${client?.lastname}"
                if (client?.image != null) {
                    if (client?.image != "") {
                        Glide.with(this).load(client?.image).into(binding.circleImageProfile)
                    }
                }
            }
        }
    }
}