package com.biryulindevelop.unsplash.application

const val REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob"
const val ACCESS_KEY = "JldYVfpRuY9czIZ7Ye2hISDxZkVear9kx-CjRPdyZb0"
const val SECRET_KEY = "AQRrx_yDpkfjVazKVZIFbp4LlSoi-vzhCMAMy3hFiCM"

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