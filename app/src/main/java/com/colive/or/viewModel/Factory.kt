package com.colive.or.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.colive.or.App
import com.colive.or.Apps
import kotlinx.coroutines.flow.Flow

class Factory(
    private val applic : App,
    private val appsFlow: Flow<Apps?>,
    private val deepFlow: Flow<String>
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ViewModelFruitsClash(applic, appsFlow, deepFlow) as T
        }

}