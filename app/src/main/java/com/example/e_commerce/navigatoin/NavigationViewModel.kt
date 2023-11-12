package com.example.e_commerce.navigatoin

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreConstants.USER_AUTHORITY_KEY
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreConstants.USER_NAME_KEY
import com.example.e_commerce.data.local.dataStore.PreferenceDataStoreHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class NavigationViewModel(
    private val context: Application,
) : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _userAuthority = MutableStateFlow("")
    val userAuthority: StateFlow<String> = _userAuthority.asStateFlow()

    init {
        getUserNameAndAuthority()
    }

    private fun getUserNameAndAuthority() {
        viewModelScope.launch(Dispatchers.IO) {
            PreferenceDataStoreHelper(context).apply {
                val nameFlow = getPreference(USER_NAME_KEY, "")
                val authorityFlow = getPreference(USER_AUTHORITY_KEY, "")
                combine(nameFlow, authorityFlow) { name, authority ->
                    _userName.value = name
                    _userAuthority.value = authority
                    Log.e("TAG", "getUserNameAndAuthority value: $authority")
                }.collect()
            }
        }
    }
}
