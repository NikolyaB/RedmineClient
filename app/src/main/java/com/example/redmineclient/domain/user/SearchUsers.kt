package com.example.redmineclient.domain.user

class SearchUsers {
    fun execute(users: List<User>, query: String): List<User> {

        if (query.isBlank()) return users

        return users.filter {
            it.login.trim().lowercase().contains(query.lowercase())
        }
    }
}