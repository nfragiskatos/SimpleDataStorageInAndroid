package com.nicholasfragiskatos.preferancestorageexample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.lifecycleScope
import com.nicholasfragiskatos.preferancestorageexample.databinding.ActivityProtoDataStoreBinding
import kotlinx.coroutines.launch

val Context.myProtoDataStore: DataStore<MySettings> by dataStore("MyProtoSchema.proto", serializer = MySettingsSerializer)

class ProtoDataStoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProtoDataStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProtoDataStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            initFromPreferences()
        }

        binding.btnSave.setOnClickListener {
            lifecycleScope.launch {
                savePreferences()
            }
        }
    }

    private suspend fun initFromPreferences() {
        applicationContext.myProtoDataStore.data.collect { settings ->
            binding.etFirstName.setText(settings.firstName)
            binding.etLastName.setText(settings.lastName)
            binding.etFavoriteColor.setText(settings.favoriteColor)
            binding.rgIceCreamFlavor.check(settings.favoriteIceCreamFlavor)
        }
    }

    private suspend fun savePreferences() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val favoriteColor = binding.etFavoriteColor.text.toString()
        val iceCreamId = binding.rgIceCreamFlavor.checkedRadioButtonId

        applicationContext.myProtoDataStore.updateData { settings: MySettings ->
            settings.toBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setFavoriteColor(favoriteColor)
                .setFavoriteIceCreamFlavor(iceCreamId)
                .build()
        }
    }
}