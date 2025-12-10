package org.izv.aa.agenda.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.izv.aa.agenda.model.data.Contact
import org.izv.aa.agenda.model.repository.ContactFileRepository
import org.izv.aa.agenda.ui.compose.ContactItem

class ContactFileViewModel(private val repository: ContactFileRepository): ViewModel() {//le decimos que es un viewmodel

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts = _contacts.asStateFlow()//con esto hago una copia para que solo puedan ver la publicq

    fun readContacts(){
        viewModelScope.launch {
           _contacts.value = repository.readContacts()
        }
    }

    fun addContact(contact: Contact){
        viewModelScope.launch {
            val contact = Contact(id = 0, name = "", phone = "")
            repository.writeContact(contact)
            readContacts()
        }
    }

}