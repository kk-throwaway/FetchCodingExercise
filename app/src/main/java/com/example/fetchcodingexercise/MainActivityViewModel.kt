package com.example.fetchcodingexercise

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel : ViewModel() {

    val TAG = "MainActivityViewModel"
    var itemList: MutableLiveData<List<ItemModel>> = MutableLiveData()
    var isLoading : MutableLiveData<Boolean> = MutableLiveData()
    var hasError : MutableLiveData<Boolean> = MutableLiveData()
    var tempList : MutableList<ItemModel> = ArrayList()

    fun getItemListObserver(): MutableLiveData<List<ItemModel>> =
        itemList

    /*
        Method using RxJava to pull down the data in the background and filter null and zero-length names
     */
    fun pollData() {
        val retrofitInstance = RetrofitInstance.getInstance().create(RetrofitService::class.java)
                retrofitInstance.getItems()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMapIterable { it }
                        .filter { it.name != null }
                        .filter { it.name?.length != 0 }
                        .subscribe(getItemObserver())
    }

    /*
        Observer to handle each emitted item
     */
    private fun getItemObserver(): Observer<ItemModel> {
        return object: Observer<ItemModel> {
            override fun onComplete() {
                itemList.postValue(getSortedList())
                isLoading.value = false
                Log.d(TAG, "onComplete()")
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe()")
                hasError.value = false
                isLoading.value = true
            }

            override fun onError(e: Throwable) {
                isLoading.value = false
                hasError.value = true
                itemList.postValue(null)
                Log.e(TAG, "Error in getItemObserver(): ", e)
            }

            override fun onNext(t: ItemModel) {
                Log.d(TAG, "onNext(): $t")
                tempList.add(t)
            }
        }
    }

    /*
        Note that the precedence of sorting is :
            1. ListId
            2. Name

        I understand that the number in the name is the exact same as the id, however, I wanted to
        follow the instructions as literally as possible. I initially planned on doing the sorting
        via RxJava operators, but since there was no clean way to alphabetize with lambda functions,
        I just chose to handle it with a method.
     */
    private fun getSortedList() : List<ItemModel>{
        val sortedList = tempList.sortedBy { it.name }
        return sortedList.sortedBy { it.listId }
    }



}