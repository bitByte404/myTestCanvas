package com.wuliner.canvas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    val eraserState: MutableLiveData<Boolean> = MutableLiveData(false)
    val penState: MutableLiveData<Boolean> = MutableLiveData(true)
    val unDoState: MutableLiveData<Boolean> = MutableLiveData(false)
    val reDoState: MutableLiveData<Boolean> = MutableLiveData(false)
    val version: MutableLiveData<Int> = MutableLiveData(0)
}