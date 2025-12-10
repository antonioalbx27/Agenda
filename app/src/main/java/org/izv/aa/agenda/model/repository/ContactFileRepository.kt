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

    private suspend fun safeSave(contacts: List<Contact>) {
        withContext(Dispatchers.IO) {
            val file = getFile()
            file.printWriter().use { out ->
                contacts.forEach {
                        contact ->
                    out.println("${contact.id}, ${contact.name}, ${contact.phone}")
                }
            }
        }
    }

    suspend fun readContacts(): List<Contact> {//estos son corrutinas; que hacen la gestion en segundo plano
        return withContext(Dispatchers.IO) {
                val file = getFile()
                if (!file.exists()) {
                    return@withContext emptyList<Contact>()
                }
                val contacts = mutableListOf<Contact>()

                file.forEachLine { line ->
                    val partes = line.split(",")
                    if (partes.size >= 3) {
                        try {
                            val contact = Contact(
                                id = partes[0].toInt(),
                                name = partes[1],
                                phone = partes[3]
                            )
                            contacts.add(contact)
                        } catch (e: NumberFormatException) {
                            e.printStackTrace()
                        }
                    }
                }
                return@withContext contacts
            }
        }
        suspend fun writeContact(contact: Contact): Unit {//como no me devuelven nada; les pogno el unit; lo mismo que void en java
            return withContext(Dispatchers.IO) {
                val listaActual = readContacts().toMutableList()
                val nuevoID = if (listaActual.isEmpty()) 1 else listaActual.maxOf {it.id} + 1
                val contactoNuevo = contact.copy(id = nuevoID)
                listaActual.add(contactoNuevo)
                safeSave(listaActual)
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
