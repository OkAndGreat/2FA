package com.example.twofa.viewmodel

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.twofa.db.AppDatabase
import com.example.twofa.db.Token
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrashViewModel : ViewModel() {

    companion object {
        fun get(context: Context): TrashViewModel? {
            (context as? FragmentActivity)?.apply {
                return ViewModelProvider(this)[TrashViewModel::class.java]
            }
            return null
        }
    }

    private val _tokenList =
        MutableStateFlow(emptyList<Token>().toMutableList())

    val tokenList: StateFlow<List<Token>> = _tokenList

    fun addToken(token: Token) {
        val newList = _tokenList.value.toMutableList().apply {
            add(token)
        }
        // 更新MutableStateFlow的值
        _tokenList.value = newList
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

}