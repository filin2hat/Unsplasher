package com.biryulindevelop.unsplash.application

const val REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob"
const val ACCESS_KEY = "dq7K-hs0otHTImGklsZeID8Y6HBlDd6WdFfHGEmEElk"
const val SECRET_KEY = "dw8PNk1WjIz4D0gT5oJDH-F0fGpCh-DtcNqHXjW6pOM"

//const val ACCESS_KEY = "0mWlk4H1JqeVUaZ0S136G2DkDB8N6poiRRNDXIdAsoM"
//const val SECRET_KEY = "6jPzllGr2RxoPQ9daKVVhTFJtQswY50Ley6YxjEKYCw"

//const val ACCESS_KEY = "JldYVfpRuY9czIZ7Ye2hISDxZkVear9kx-CjRPdyZb0"
//const val SECRET_KEY = "AQRrx_yDpkfjVazKVZIFbp4LlSoi-vzhCMAMy3hFiCM"

//const val ACCESS_KEY = "5bSR9PSHmHMrwe0hm3Lu2pa7uTtx-pG49Kf-l2lKHNQ"
//const val SECRET_KEY = "cN2oXv9VpzFjJge_X2rEFlYH4qSRunvSzAYSMHoCSBI"

const val TOKEN_NAME = "name_token"
const val TOKEN_KEY = "key_token"
const val TOKEN_ENABLED = "token_enabled"
const val ONBOARDING_SHOWN = "onboarding_shown"

private const val SCOPE =
    "public+read_user+" +
            "write_user+read_photos+" +
            "write_photos+" +
            "write_likes+" +
            "write_followers+" +
            "read_collections+" +
            "write_collections"

const val REQUEST =
    "https://unsplash.com/oauth/authorize" +
            "?client_id=" +
            ACCESS_KEY +
            "&redirect_uri=" +
            REDIRECT_URI +
            "&response_type=code" +
            "&scope=" +
            SCOPE