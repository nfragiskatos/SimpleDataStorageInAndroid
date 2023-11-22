package com.nicholasfragiskatos.preferancestorageexample

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nicholasfragiskatos.preferancestorageexample.databinding.ActivitySharedPreferencesBinding

class SharedPreferencesActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySharedPreferencesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharedPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFromPrefs()

        binding.btnSave.setOnClickListener {
            savePreferences()
        }

        binding.btnGoToPrefDataStore.setOnClickListener {
            val intent = Intent(this, PrefsDataStoreActivity::class.java)
            startActivity(intent)
        }

        binding.btnGoToProtoDataStore.setOnClickListener {
            val intent = Intent(this, ProtoDataStoreActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initFromPrefs() {
        val demographicsPrefs = getSharedPreferences(DEMOGRAPHICS_FILE_KEY, Context.MODE_PRIVATE)
        val favoritePrefs = getSharedPreferences(FAVORITE_INFO_FILE_KEY, Context.MODE_PRIVATE)

        val firstName = demographicsPrefs.getString(FIRST_NAME_PREF_KEY, "")
        val lastName = demographicsPrefs.getString(LAST_NAME_PREF_KEY, "")
        val favoriteColor = favoritePrefs.getString(FAVORITE_COLOR_PREF_KEY, "")
        val favoriteIceCreamId = favoritePrefs.getInt(FAVORITE_ICE_CREAM_PREF_KEY, R.id.rbChocolate)

        binding.etFirstName.setText(firstName)
        binding.etLastName.setText(lastName)
        binding.etFavoriteColor.setText(favoriteColor)
        binding.rgIceCreamFlavor.check(favoriteIceCreamId)

    }

    private fun savePreferences() {
        val demographicsPrefs = getSharedPreferences(DEMOGRAPHICS_FILE_KEY, Context.MODE_PRIVATE)
        val favoritePrefs = getSharedPreferences(FAVORITE_INFO_FILE_KEY, Context.MODE_PRIVATE)

        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val favoriteColor = binding.etFavoriteColor.text.toString()
        val iceCreamId = binding.rgIceCreamFlavor.checkedRadioButtonId

        val edit = demographicsPrefs.edit()
        edit.apply()
        edit.commit()

        demographicsPrefs.edit().apply {
            putString(FIRST_NAME_PREF_KEY, firstName)
            putString(LAST_NAME_PREF_KEY, lastName)
            apply()
        }

        favoritePrefs.edit().apply {
            putString(FAVORITE_COLOR_PREF_KEY, favoriteColor)
            putInt(FAVORITE_ICE_CREAM_PREF_KEY, iceCreamId)
            apply()
        }
    }

    companion object {
        const val DEMOGRAPHICS_FILE_KEY = "${BuildConfig.APPLICATION_ID}.NAME_FILE_KEY"
        const val FAVORITE_INFO_FILE_KEY = "${BuildConfig.APPLICATION_ID}.FAVORITE_INFO_FILE_KEY"

        const val FIRST_NAME_PREF_KEY = "${BuildConfig.APPLICATION_ID}.FIRST_NAME_PREF_KEY"
        const val LAST_NAME_PREF_KEY = "${BuildConfig.APPLICATION_ID}.LAST_NAME_PREF_KEY"
        const val FAVORITE_COLOR_PREF_KEY = "${BuildConfig.APPLICATION_ID}.FAVORITE_COLOR_PREF_KEY"
        const val FAVORITE_ICE_CREAM_PREF_KEY = "${BuildConfig.APPLICATION_ID}.FAVORITE_ICE_CREAM_PREF_KEY"
    }
}