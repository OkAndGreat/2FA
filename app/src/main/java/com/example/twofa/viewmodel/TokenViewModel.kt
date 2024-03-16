package com.example.twofa.viewmodel

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.twofa.ParseQRCodeEvent
import com.example.twofa.db.AppDatabase
import com.example.twofa.db.Token
import com.example.twofa.utils.TOTPParseUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TokenViewModel : ViewModel() {

    companion object {
        fun get(context: Context): TokenViewModel? {
            (context as? ComponentActivity)?.apply {
                return ViewModelProvider(this).get(TokenViewModel::class.java)
            }
            return null
        }

    }

    private val _tokenList =
        MutableStateFlow(emptyList<Token>().toMutableList())

    val tokenList: StateFlow<List<Token>> = _tokenList

    fun getTokenListByQuery(query: String): List<Token> {

        var filteredList = linkedSetOf<Token>()

        _tokenList.value.forEach {
            if (it.userName.contains(query, ignoreCase = true) || it.platformName.contains(
                    query,
                    ignoreCase = true
                )
            ) {
                filteredList.add(it)
            }

        }
        return filteredList.toList()
    }

    fun updateToken(token: Token, index: Int) {
        _tokenList.value = _tokenList.value.toMutableList().apply {
            this[index] = token
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val db = AppDatabase.getDatabase()
                val tokenDao = db.tokenDao()
                tokenDao.updateToken(token)
            }
        }
    }

    fun deleteToken(token: Token) {
        _tokenList.value = _tokenList.value.toMutableList().apply {
            this.remove(token)
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val db = AppDatabase.getDatabase()
                val tokenDao = db.tokenDao()
                tokenDao.deleteToken(token = token)
            }
        }
    }


    fun getTokenListByDb() {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase()
            val tokenDao = db.tokenDao()
            withContext(Dispatchers.IO) {
                val newList = tokenDao.getAllTokens().toMutableList()
                _tokenList.value = newList
            }
        }
    }

    fun emitQRCodeScanEvent(event: ParseQRCodeEvent) {
        viewModelScope.launch {
            val totpAuthData = TOTPParseUtil.parseTOTPAuthUrl(event.msg) ?: return@launch
            val db = AppDatabase.getDatabase()
            val tokenDao = db.tokenDao()
            val token = Token(
                0,
                totpAuthData.platformName,
                totpAuthData.userName,
                totpAuthData.secretKey
            )
            // 创建新的列表，添加新元素
            val newList = _tokenList.value.toMutableList().apply {
                add(token)
            }
            // 更新MutableStateFlow的值
            _tokenList.value = newList

            withContext(Dispatchers.IO) {
                tokenDao.insert(token)
            }
        }
    }

}