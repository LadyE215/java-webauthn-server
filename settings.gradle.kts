rootProject.name = "webauthn-server-parent"
include(":webauthn-server-attestation")
include(":webauthn-server-core")
include(":webauthn-server-demo")
include(":yubico-util")
include(":yubico-util-scala")

include(":test-dependent-projects:java-dep-webauthn-server-attestation")
include(":test-dependent-projects:java-dep-webauthn-server-core")
include(":test-dependent-projects:java-dep-webauthn-server-core-and-bouncycastle")
include(":test-dependent-projects:java-dep-yubico-util")
include(":test-platform")

dependencyResolutionManagement {
    versionCatalogs {
        create("constraintLibs") {
            val jacksonVer = version("jackson", "[2.13.2.1,3)")

            library("cbor", "com.upokecenter:cbor:[4.5.1,5)")
            library("cose", "com.augustcellars.cose:cose-java:[1.0.0,2)")
            library("guava", "com.google.guava:guava:[24.1.1,32)")
            library("httpclient5", "org.apache.httpcomponents.client5:httpclient5:[5.0.0,6)")
            library("jackson-bom", "com.fasterxml.jackson", "jackson-bom").versionRef(jacksonVer)
            library("slf4j", "org.slf4j:slf4j-api:[1.7.25,3)")
        }
    }
}
