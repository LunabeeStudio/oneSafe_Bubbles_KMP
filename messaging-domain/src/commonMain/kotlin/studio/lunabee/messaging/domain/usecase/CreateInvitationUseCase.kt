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
 * Created by Lunabee Studio / Date - 6/29/2023 - for the oneSafe6 SDK.
 * Last modified 29/06/2023 16:31
 */

package studio.lunabee.messaging.domain.usecase

import studio.lunabee.bubbles.domain.di.Inject
import studio.lunabee.bubbles.domain.model.ConversationId
import studio.lunabee.bubbles.domain.model.MessageSharingMode
import studio.lunabee.bubbles.domain.model.contact.ContactId
import studio.lunabee.bubbles.domain.model.contact.PlainContact
import studio.lunabee.bubbles.domain.repository.BubblesCryptoRepository
import studio.lunabee.bubbles.domain.usecase.CreateContactUseCase
import studio.lunabee.bubbles.domain.usecase.InsertHandShakeDataUseCase
import studio.lunabee.doubleratchet.DoubleRatchetEngine
import studio.lunabee.doubleratchet.crypto.DoubleRatchetKeyRepository
import studio.lunabee.doubleratchet.model.AsymmetricKeyPair
import studio.lunabee.doubleratchet.model.DRSharedSecret
import studio.lunabee.doubleratchet.model.createRandomUUID
import studio.lunabee.messaging.domain.model.HandShakeData

class CreateInvitationUseCase @Inject constructor(
    private val doubleRatchetEngine: DoubleRatchetEngine,
    private val doubleRatchetKeyRepository: DoubleRatchetKeyRepository,
    private val createContactUseCase: CreateContactUseCase,
    private val bubblesCryptoRepository: BubblesCryptoRepository,
    private val insertHandShakeDataUseCase: InsertHandShakeDataUseCase,
) {

    /**
     * Create a conversation for double ratchet message exchange
     * Create and save handShakeData required to establish the symmetric encryption
     * Create a bubbles contact with id corresponding to the conversation
     *
     * @return the message string to send
     */
    suspend operator fun invoke(contactName: String, sharingMode: MessageSharingMode): ContactId {
        val keyPair: AsymmetricKeyPair = doubleRatchetKeyRepository.generateKeyPair()
        val contactId = createRandomUUID()
        val sharedConversationId = createRandomUUID()
        createContactUseCase(
            PlainContact(
                id = ContactId(contactId),
                name = contactName,
                // Not created yet
                sharedKey = null,
                sharedConversationId = ConversationId(sharedConversationId),
                sharingMode = sharingMode,
            ),
        )
        val sharedSalt = bubblesCryptoRepository.deriveUUIDToKey(
            sharedConversationId,
            doubleRatchetKeyRepository.rootKeyByteSize,
        )
        doubleRatchetEngine.createInvitation(
            sharedSalt = DRSharedSecret(sharedSalt),
            newConversationId = contactId,
        )
        val handShakeData = HandShakeData(
            conversationLocalId = ConversationId(contactId),
            conversationSharedId = ConversationId(sharedConversationId),
            oneSafePrivateKey = keyPair.privateKey.value,
            oneSafePublicKey = keyPair.publicKey.value,
        )

        insertHandShakeDataUseCase(handShakeData)
        return ContactId(contactId)
    }
}
