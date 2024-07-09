/*
 * Copyright (c) 2023 Lunabee Studio
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
 * Last modified 6/20/23, 1:27 PM
 */

package studio.lunabee.messaging.repository.datasource

import kotlinx.coroutines.flow.Flow
import studio.lunabee.bubbles.domain.model.contact.ContactId
import studio.lunabee.messaging.domain.model.MessageId
import studio.lunabee.messaging.domain.model.MessageOrder
import studio.lunabee.messaging.domain.model.SafeMessage

interface MessageLocalDataSource {
    suspend fun save(message: SafeMessage, order: Float)
    suspend fun getAllByContact(contactId: ContactId): List<SafeMessage>
    suspend fun getLastMessage(contactId: ContactId): Flow<SafeMessage?>
    suspend fun getLastByContact(contactId: ContactId, exceptIds: List<MessageId>): MessageOrder?
    suspend fun getFirstByContact(contactId: ContactId, exceptIds: List<MessageId>): MessageOrder?
    suspend fun countByContact(contactId: ContactId, exceptIds: List<MessageId>): Int
    suspend fun getAtByContact(position: Int, contactId: ContactId, exceptIds: List<MessageId>): MessageOrder?
    suspend fun getByContactByOrder(contactId: ContactId, order: Float): SafeMessage
    suspend fun deleteAllMessages(contactId: ContactId)
    suspend fun deleteMessage(messageId: MessageId)
    suspend fun markMessagesAsRead(contactId: ContactId)
    // fun getAllPaged(config: PagingConfig, contactId: ContactId): Flow<PagingData<SafeMessage>>
}
