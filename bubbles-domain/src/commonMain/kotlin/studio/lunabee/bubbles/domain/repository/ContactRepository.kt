/*
 * Copyright (c) 2023-2023 Lunabee Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by Lunabee Studio / Date - 6/20/2023 - for the oneSafe6 SDK.
 * Last modified 6/20/23, 1:41 PM
 */

package studio.lunabee.bubbles.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import studio.lunabee.bubbles.domain.model.SafeId
import studio.lunabee.bubbles.domain.model.contact.Contact
import studio.lunabee.bubbles.domain.model.contact.ContactId
import studio.lunabee.bubbles.domain.model.contactkey.ContactLocalKey
import studio.lunabee.bubbles.domain.model.contactkey.ContactSharedKey

interface ContactRepository {
    suspend fun save(contact: Contact, key: ContactLocalKey)
    fun getAllContactsFlow(safeId: SafeId): Flow<List<Contact>>
    fun getRecentContactsFlow(maxNumber: Int, safeId: SafeId): Flow<List<Contact>>
    fun getContactFlow(id: ContactId): Flow<Contact?>
    suspend fun getContact(id: ContactId): Contact?
    suspend fun getSharedKey(id: ContactId): ContactSharedKey?
    suspend fun addContactSharedKey(id: ContactId, sharedKey: ContactSharedKey)
    suspend fun deleteContact(id: ContactId)
    suspend fun updateMessageSharingMode(id: ContactId, encSharingMode: ByteArray, updateAt: Instant)
    suspend fun updateUpdatedAt(id: ContactId, updateAt: Instant)
    suspend fun updateContact(id: ContactId, encSharingMode: ByteArray, encName: ByteArray, updateAt: Instant)
    suspend fun updateContactConsultedAt(id: ContactId, consultedAt: Instant)
}
