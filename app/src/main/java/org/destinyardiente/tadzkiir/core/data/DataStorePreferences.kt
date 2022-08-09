    package org.destinyardiente.tadzkiir.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.destinyardiente.tadzkiir.core.data.source.model.SavedJadwalShalat
import org.destinyardiente.tadzkiir.core.data.source.remote.response.LoginResponse
import java.io.IOException


const val IS_LOGIN = "IS_LANDING_ACTIVITY_OPENED"
const val USERNAME = "USERNAME"
const val LAYOUT_SETTINGS_PREFERENCE = "SETTINGS_PREFERENCE"
const val JADWAL_SHALAT_PREF = "JADWAL_SHALAT_PREFERENCE"
const val LAST_TIME_STARTED = "LAST_TIME_STARTED_PREFERENCE"

val Context.loginDataStore: DataStore<Preferences> by preferencesDataStore(name = IS_LOGIN)
val Context.usernameDataStore: DataStore<Preferences> by preferencesDataStore(name = USERNAME)
val Context.layoutSettingDataStore: DataStore<Preferences> by preferencesDataStore(name = LAYOUT_SETTINGS_PREFERENCE)
val Context.jadwalShalatDataStore: DataStore<Preferences> by preferencesDataStore(name = JADWAL_SHALAT_PREF)
val Context.ltsDataStore: DataStore<Preferences> by preferencesDataStore(name = LAST_TIME_STARTED)

class DataStorePreferences(preferencesDataStore: DataStore<Preferences>) {
    companion object{
        private val namePref = stringPreferencesKey("namePref")
        private val isLogin = stringPreferencesKey("isLogin")
        private val isLatinVisible = booleanPreferencesKey("isLatinVisible")
        private val isArtiVisible = booleanPreferencesKey("isArtiVisible")
        private val isJadwalShalatObtained = booleanPreferencesKey("isJadwalShalatObtained")
        private val date = stringPreferencesKey("date")
        private val fajr = stringPreferencesKey("fajr")
        private val syuruq = stringPreferencesKey("syuruq")
        private val zuhr = stringPreferencesKey("zuhr")
        private val asr = stringPreferencesKey("asr")
        private val maghrib = stringPreferencesKey("maghrib")
        private val isha = stringPreferencesKey("isha")
        private val location = stringPreferencesKey("location")
        private val lastTimeStarted = intPreferencesKey("lastTimeStarted")
    }

    suspend fun saveJadwalShalatPref(ctx: Context,savedData: SavedJadwalShalat?){
        ctx.jadwalShalatDataStore.edit { pref->
            if (savedData != null) {
                pref[isJadwalShalatObtained] = savedData.isObtained!!
                pref[date] = savedData.date!!
                pref[location] = savedData.location!!
//                pref[state] = savedData.state!!
                pref[fajr] = savedData.fajr!!
                pref[syuruq] = savedData.sunrise!!
                pref[zuhr] = savedData.zuhr!!
                pref[asr] = savedData.asr!!
                pref[maghrib] = savedData.maghrib!!
                pref[isha] = savedData.isha!!
            }
        }
    }
    suspend fun saveLtsPref(ctx: Context,lts: Int){
        ctx.layoutSettingDataStore.edit { pref->
            pref[lastTimeStarted] = lts
        }
    }

    suspend fun saveLatinSettingsPref(ctx: Context,isLatinEnable: Boolean){
        ctx.layoutSettingDataStore.edit { pref ->
            pref[isLatinVisible] = isLatinEnable
        }
    }
    suspend fun saveArtiSettingsPref(ctx: Context,isArtiEnable: Boolean){
        ctx.layoutSettingDataStore.edit { pref ->
            pref[isArtiVisible] = isArtiEnable
        }
    }

    suspend fun clearLayoutSettingsPref(ctx: Context){
        ctx.layoutSettingDataStore.edit { pref ->
            pref.clear()
        }
    }

    suspend fun clearJadwalShalatPref(ctx: Context){
        ctx.jadwalShalatDataStore.edit { pref ->
            pref.clear()
        }
    }

    val artiSettingsPreferenceFlow: Flow<Boolean> = preferencesDataStore.data
        .catch {
            if(it is IOException){
                it.printStackTrace()
            }
            else throw it
        }.map { pref ->
            pref[isArtiVisible] ?: false
        }

    val latinSettingsPreferenceFlow: Flow<Boolean> = preferencesDataStore.data
        .catch {
            if(it is IOException){
                it.printStackTrace()
            }
            else throw it
        }.map { pref ->
            pref[isLatinVisible] ?: false
        }

    suspend fun saveUsernamePref(ctx: Context, data: LoginResponse?){
        ctx.usernameDataStore.edit { pref ->
            pref[namePref] = data?.name.toString()
        }
    } suspend fun saveLoginPref(ctx: Context, data: LoginResponse?){
        ctx.loginDataStore.edit { pref ->
            pref[isLogin] = data?.token.toString()
        }
    }

    suspend fun clearLoginPref(ctx: Context){
        ctx.loginDataStore.edit { pref ->
            pref.clear()
        }
    }

    val usernamePreferenceFlow: Flow<String?> = preferencesDataStore.data
        .catch {
            if(it is IOException){
                it.printStackTrace()
            }
            else throw it
        }.map { pref ->
            pref[namePref]
        }

    val jadwalShalatPreferenceFlow: Flow<SavedJadwalShalat?> = preferencesDataStore.data
        .catch {
            if(it is IOException){
                it.printStackTrace()
            }
            else throw it
        }.map { pref ->
            SavedJadwalShalat(
                isObtained = pref[isJadwalShalatObtained] ?: false,
                date = pref[date] ?: "",
                location = pref[location] ?: "",
                fajr = pref[fajr] ?: "",
                sunrise = pref[syuruq] ?: "",
                zuhr = pref[zuhr] ?: "",
                asr = pref[asr] ?: "",
                maghrib = pref[maghrib] ?: "",
                isha = pref[isha] ?: "",
            )
        }

    val ltsPreferenceFlow: Flow<Int?> = preferencesDataStore.data
        .catch {
            if(it is IOException){
                it.printStackTrace()
            }
            else throw it
        }.map { pref ->
            pref[lastTimeStarted] ?: -1
        }

    val tokenPreferenceFlow: Flow<String?> = preferencesDataStore.data
        .catch {
            if(it is IOException){
            it.printStackTrace()
        }
        else throw it
    }.map { pref ->
            pref[isLogin]
    }

//    suspend fun readPref(ctx: Context): Boolean? = ctx.dataStore.data.first()[isLogin] ?: false

}