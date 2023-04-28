package com.example.gamermonke

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow

class MainViewModel(repository: UserRepository) : ViewModel(){
    // Connect a live data object to the current bit of weather info
    private val jsonData: LiveData<User> = repository.data

    //Use a second live data here to show entire contents of db
    // This casts a flow in the repo as a live data so an observer in the view
    // can watch it. If you want to observe variables in the repo from the viewmodel, use
    // observeForever (not recommended as it's almost never needed)
    val allUsers: LiveData<List<User>> = repository.allUsers.asLiveData()

    val allNames: List<String> = repository.getNames()

    suspend fun setUserData(newUser: User) {
        mUserRepository.insert(newUser)
    }

    //The singleton repository. If our app maps to one process, the recommended
    // pattern is to make repo and db singletons. That said, it's sometimes useful
    // to have more than one repo so it doesn't become a kitchen sink class, but each
    // of those repos could be singleton.
    private var mUserRepository: UserRepository = repository

    // Returns the data contained in the live data object
    val data: LiveData<User>
        get() = jsonData
}

// This factory class allows us to define custom constructors for the view model
class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}