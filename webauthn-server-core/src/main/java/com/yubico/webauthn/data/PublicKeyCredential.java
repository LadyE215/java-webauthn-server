// Copyright (c) 2018, Yubico AB
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice, this
//    list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright notice,
//    this list of conditions and the following disclaimer in the documentation
//    and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
// FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
// SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
// CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
// OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package com.yubico.webauthn.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;


/**
 * The PublicKeyCredential interface inherits from Credential <a href="https://www.w3.org/TR/credential-management-1/">[CREDENTIAL-MANAGEMENT-1]</a>,
 * and contains the attributes that are returned to the caller when a new credential is created, or a new assertion is
 * requested.
 *
 * @see <a href="https://w3c.github.io/webauthn/#iface-pkcredential">§5.1. PublicKeyCredential Interface</a>
 */
@Value
@Builder(toBuilder = true)
public class PublicKeyCredential<A extends AuthenticatorResponse, B extends ClientExtensionOutputs> implements Credential {

    /**
     * The raw Credential ID of this credential, corresponding to the <code>rawId</code> attribute in the WebAuthn API.
     */
    @NonNull
    private final ByteArray id;

    /**
     * The authenticator's response to the client’s request to either create a public key credential, or generate an
     * authentication assertion.
     * <p>
     * If the {@link PublicKeyCredential} was created in response to
     * <code>navigator.credentials.create()</code>, this attribute’s value will
     * be an {@link AuthenticatorAttestationResponse}, otherwise, the {@link PublicKeyCredential} was created in
     * response to
     * <code>navigator.credentials.get()</code>, and this attribute’s value will
     * be an {@link AuthenticatorAssertionResponse}.
     * </p>
     */
    @NonNull
    private final A response;

    /**
     * A map containing extension identifier → client extension output entries produced by the extension’s client
     * extension processing.
     */
    @NonNull
    private final B clientExtensionResults;

    /**
     * The {@link PublicKeyCredential}'s type value is the string "public-key".
     */
    @NonNull
    @Builder.Default
    private final PublicKeyCredentialType type = PublicKeyCredentialType.PUBLIC_KEY;

    @JsonCreator
    private PublicKeyCredential(
        @NonNull @JsonProperty("id") ByteArray id,
        @NonNull @JsonProperty("response") A response,
        @NonNull @JsonProperty("clientExtensionResults") B clientExtensionResults,
        @NonNull @JsonProperty("type") PublicKeyCredentialType type
    ) {
        this.id = id;
        this.response = response;
        this.clientExtensionResults = clientExtensionResults;
        this.type = type;
    }

    public static <A extends AuthenticatorResponse, B extends ClientExtensionOutputs> PublicKeyCredentialBuilder<A, B>.MandatoryStages builder() {
        return new PublicKeyCredentialBuilder<A, B>().start();
    }

    public static class PublicKeyCredentialBuilder<A extends AuthenticatorResponse, B extends ClientExtensionOutputs> {
        private MandatoryStages start() {
            return new MandatoryStages(this);
        }

        @AllArgsConstructor
        public class MandatoryStages {
            private final PublicKeyCredentialBuilder<A, B> builder;

            public Step2 id(ByteArray id) {
                builder.id(id);
                return new Step2();
            }

            public class Step2 {
                public Step3 response(A response) {
                    builder.response(response);
                    return new Step3();
                }
            }

            public class Step3 {
                public PublicKeyCredentialBuilder<A, B> clientExtensionResults(B clientExtensionResults) {
                    return builder.clientExtensionResults(clientExtensionResults);
                }
            }
        }

    }

}
