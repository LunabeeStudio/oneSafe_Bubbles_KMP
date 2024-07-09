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
 * Created by Lunabee Studio / Date - 7/13/2023 - for the oneSafe6 SDK.
 * Last modified 13/07/2023 13:37
 */

package studio.lunabee.messaging.domain.usecase

import com.lunabee.lbcore.model.LBResult
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import studio.lunabee.bubbles.domain.di.Inject
import studio.lunabee.messaging.domain.extension.getOrThrow
import studio.lunabee.bubbles.domain.model.ConversationId
import studio.lunabee.bubbles.domain.model.contact.ContactId
import studio.lunabee.messaging.domain.model.proto.ProtoInvitationMessage
import studio.lunabee.bubbles.error.BubblesDomainError
import studio.lunabee.bubbles.error.BubblesError
import studio.lunabee.bubbles.error.BubblesMessagingError
import studio.lunabee.doubleratchet.storage.DoubleRatchetLocalDatasource

class GetInvitationMessageUseCase @Inject constructor(
    private val doubleRatchetLocalDatasource: DoubleRatchetLocalDatasource,
    private val getHandShakeDataUseCase: GetHandShakeDataUseCase,
) {

    @OptIn(ExperimentalSerializationApi::class)
    suspend operator fun invoke(contactId: ContactId): LBResult<ByteArray> = BubblesError.runCatching {
        val conversationId = ConversationId(contactId.value)
        val handShakeData = getHandShakeDataUseCase(conversationLocalId = conversationId).getOrThrow()
            ?: throw BubblesMessagingError(BubblesMessagingError.Code.HANDSHAKE_DATA_NOT_FOUND)
        val conversation = doubleRatchetLocalDatasource.getConversation(conversationId.value)
            ?: throw BubblesDomainError(BubblesDomainError.Code.NO_MATCHING_CONTACT)
        val invitationProto = ProtoInvitationMessage(
            doubleRatchetPublicKey = conversation.personalKeyPair.publicKey.value,
            oneSafePublicKey = handShakeData.oneSafePublicKey ?: byteArrayOf(),
            conversationId = handShakeData.conversationSharedId.toString(),
            recipientId = handShakeData.conversationLocalId.toString(),
        )
        ProtoBuf.encodeToByteArray(invitationProto)
    }
}
