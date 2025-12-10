package org.izv.aa.agenda.model.repository

import android.content.Context;
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.izv.aa.agenda.model.data.Contact
import org.izv.aa.agenda.ui.compose.ContactItem
import java.io.File
import kotlin.math.sqrt

public class ContactFileRepository (private val context:Context) {
    val fileName = "contacts.cvs"

    private fun getFile(): File {
        return File(context.filesDir, fileName)
    }



    suspend fun readContacts(): List<Contact> {//estos son corrutinas; que hacen la gestion en segundo plano
        return withContext(Dispatchers.IO) {
                val file = getFile()
                if(!file.exists()){
                return@withContext emptyList()
                 }
            try {
                file.readLines().mapNotNull { line ->
                    val part = line.split(",")
                    if (part.size >= 3){
                        Contact(
                            id = part[0].toInt(),
                            name = part[1],
                            phone = part[2]
                        )
                    }else{
                        null
                    }
                }
            }catch (e: Exception){
                emptyList()
            }
            }
        }
        suspend fun writeContact(contact: Contact): Unit {//como no me devuelven nada; les pogno el unit; lo mismo que void en java
            return withContext(Dispatchers.IO) {
                val file = getFile()
                val line = "${contact.id},${contact.name},${contact.phone}"
                file.appendText(line + "\n")
            }

        }

        suspend fun editContact(contact: Contact): Unit {
            return withContext(Dispatchers.IO) {

            }
        }

        suspend fun deleteContact(contact: Contact): Unit {
            return withContext(Dispatchers.IO) {

            }
        }

    }
