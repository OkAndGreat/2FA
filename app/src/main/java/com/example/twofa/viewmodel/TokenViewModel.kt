package com.example.twofa.viewmodel

import android.content.Context
import android.media.session.MediaSession.Token
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.twofa.ParseQRCodeEvent
import com.example.twofa.ui.token.model.TokenModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class TokenViewModel : ViewModel() {

    companion object {
        fun get(context: Context): TokenViewModel? {
            (context as? ComponentActivity)?.apply {
                return ViewModelProvider(this).get(TokenViewModel::class.java)
            }
            return null
        }

    }

    private val _parseQRCodeEventFlow = MutableSharedFlow<ParseQRCodeEvent>()
    val parseQRCodeEventFlow: SharedFlow<ParseQRCodeEvent> = _parseQRCodeEventFlow

    val tokenList = mutableListOf<TokenModel>()

    fun getTokenListByQuery(query: String): List<TokenModel> {

        var filteredList = linkedSetOf<TokenModel>()

        tokenList.forEach {
            if (it.userName.contains(query, ignoreCase = true)) {
                filteredList.add(it)
            }
        }
        return filteredList.toList()
    }

    fun emitQRCodeScanEvent(event: ParseQRCodeEvent) {
        viewModelScope.launch {
            _parseQRCodeEventFlow.emit(event)
        }
    }

}