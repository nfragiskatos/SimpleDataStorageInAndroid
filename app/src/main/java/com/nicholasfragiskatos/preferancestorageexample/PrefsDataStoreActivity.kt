package com.nicholasfragiskatos.preferancestorageexample

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.nicholasfragiskatos.preferancestorageexample.databinding.ActivityPrefsDataStoreBinding
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Settings")

class PrefsDataStoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrefsDataStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrefsDataStoreBinding.inflate(layoutInflater)
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
        applicationContext.dataStore.data.collect { settings ->

            val firstName = settings[FIRST_NAME_PREF_KEY] ?: ""
            val lastName = settings[LAST_NAME_PREF_KEY] ?: ""
            val favoriteColor = settings[FAVORITE_COLOR_PREF_KEY] ?: ""
            val favoriteIceCreamId = settings[FAVORITE_ICE_CREAM_PREF_KEY] ?: R.id.rbChocolate

            binding.etFirstName.setText(firstName)
            binding.etLastName.setText(lastName)
            binding.etFavoriteColor.setText(favoriteColor)
            binding.rgIceCreamFlavor.check(favoriteIceCreamId)
        }
    }

    private suspend fun savePreferences() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val favoriteColor = binding.etFavoriteColor.text.toString()
        val iceCreamId = binding.rgIceCreamFlavor.checkedRadioButtonId

        applicationContext.dataStore.edit { settings ->
            settings[FIRST_NAME_PREF_KEY] = firstName
            settings[LAST_NAME_PREF_KEY] = lastName
            settings[FAVORITE_COLOR_PREF_KEY] = favoriteColor
            settings[FAVORITE_ICE_CREAM_PREF_KEY] = iceCreamId
        }
    }

    companion object {
        val FIRST_NAME_PREF_KEY = stringPreferencesKey("${BuildConfig.APPLICATION_ID}.FIRST_NAME_PREF_KEY")
        val LAST_NAME_PREF_KEY = stringPreferencesKey("${BuildConfig.APPLICATION_ID}.LAST_NAME_PREF_KEY")
        val FAVORITE_COLOR_PREF_KEY = stringPreferencesKey("${BuildConfig.APPLICATION_ID}.FAVORITE_COLOR_PREF_KEY")
        val FAVORITE_ICE_CREAM_PREF_KEY = intPreferencesKey("${BuildConfig.APPLICATION_ID}.FAVORITE_ICE_CREAM_PREF_KEY")
    }
}
