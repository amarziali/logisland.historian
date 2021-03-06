


# Setup micro-services environment with docker


Pre-requisites : Add the following line to your `/etc/hosts` files

    127.0.0.1       keycloak chronix redis historian


## Option A : all-in-one docker-compose

Run a compose stack 
    
    docker-compose -f backend/src/main/docker/docker-compose.yml up

## Option B : start dependencies only with docker 


Run dependencies services ()redis/chronix & keycloak) in  Docker compose

    docker-compose -f backend/src/main/docker/docker-compose-dev.yml up 
    
    
## service list
    
Keycloak admin console

    http://keycloak:8080/auth/admin/master/console/#/realms/logisland/sessions/realm

Chronix admin (chronix core for timeseries and historian core for configs)

    http://chronix:8983/solr/#/chronix
    http://chronix:8983/solr/#/historian
    

# Build and run

Run and build Logisland Historian
    
    # Checkout the source and build

    git clone git@github.com:Hurence/logisland.historian.git
    git hf init
    cd logisland.historian

Run spring boot application on port 8701
    
    cd historian/backend
    mvn clean spring-boot:run -DappPort=8701


# REST API

Get swagger.json doc

    curl -XGET http://historian:8701/v2/api-docs

Generate some sample data

    curl -XPOST  http://historian:8701/api/v1/admin?flush=false
    
you can browse API 

    curl -X GET 'http://localhost:8701/api/v1/datasources?fq=hurence'

## Token based access

    curl -X POST \
      http://keycloak:8080/auth/realms/logisland/protocol/openid-connect/token \
      -H 'Cache-Control: no-cache' \
      -H 'Content-Type: application/x-www-form-urlencoded' \
      -H 'Postman-Token: 9454e224-4b3d-4b83-841f-6d1afd1ab24a' \
      -d 'grant_type=password&client_id=logisland-historian&username=tom&password=tom'
      
should give you an access token

    {  
       "access_token":"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJBeUdHWXdXSVBvd3JwWUhwMFhveWVZR0ljbEQyMUxQTXNSSzFtWlB1anJZIn0.eyJqdGkiOiJlYTE2MGY4MC0yMWI0LTQxMWYtOGFhZC1kMTlhMzZiN2E4MjgiLCJleHAiOjE1MjQyMzMwNTAsIm5iZiI6MCwiaWF0IjoxNTI0MjMyNzUwLCJpc3MiOiJodHRwOi8va2V5Y2xvYWs6ODA4MC9hdXRoL3JlYWxtcy9sb2dpc2xhbmQiLCJhdWQiOiJsb2dpc2xhbmQtaGlzdG9yaWFuIiwic3ViIjoiYTY2Yzg1NTMtMmU3MC00NDZmLWE5OTQtNDgxNTVmMWNkOTJiIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoibG9naXNsYW5kLWhpc3RvcmlhbiIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6IjA2NTUxN2MzLTNjYmQtNDlmZS05OWU3LTBlMThhNGNiN2RlZCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJ1bWFfYXV0aG9yaXphdGlvbiIsInVzZXIiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ0b20ifQ.E7AmotrO-xTd_KMJKCLhFye50uwK07hC_6RFTtUAwZINlu9KuMHD87o705LrefepYRHftnmrCgVbP5-kvVxue74FhwbX_qHroqtwge19zp0X5Lcx5k57_ULLlB4A5QIgzbJFCRIhEItT0CcX3tbiwxvXMczZP-TwwnPd_Np1-MTaL7SHlmouQtzxCcCuFQcHCNMz1p3nyi-6Q9HTA3eaVfrHc4vp2dpzM3TbqAty22Bv_wzVKRcFUbByotLBNlcxbc4Nn8efxoPi_86CqBYD_f8oBxzLsT8kp9TRf5QcxZcdDSsuExSmFt1aRDQSvJLc0po9g11YndBnXpLP7mUZOw",
       "expires_in":300,
       "refresh_expires_in":1800,
       "refresh_token":"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJBeUdHWXdXSVBvd3JwWUhwMFhveWVZR0ljbEQyMUxQTXNSSzFtWlB1anJZIn0.eyJqdGkiOiIwNWVmZWQzMS04ZWVmLTQ3NzEtYjg0MC1mNWVlMDg2MDI2ZWYiLCJleHAiOjE1MjQyMzQ1NTAsIm5iZiI6MCwiaWF0IjoxNTI0MjMyNzUwLCJpc3MiOiJodHRwOi8va2V5Y2xvYWs6ODA4MC9hdXRoL3JlYWxtcy9sb2dpc2xhbmQiLCJhdWQiOiJsb2dpc2xhbmQtaGlzdG9yaWFuIiwic3ViIjoiYTY2Yzg1NTMtMmU3MC00NDZmLWE5OTQtNDgxNTVmMWNkOTJiIiwidHlwIjoiUmVmcmVzaCIsImF6cCI6ImxvZ2lzbGFuZC1oaXN0b3JpYW4iLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiIwNjU1MTdjMy0zY2JkLTQ5ZmUtOTllNy0wZTE4YTRjYjdkZWQiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsidW1hX2F1dGhvcml6YXRpb24iLCJ1c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fX0.FmDmVzGUOyAeT3E7ITThVUCr7SNv13WOozBcsrDm8vZX7CWKnt9eSa6anwMKD0_PL8js8footCBlYwgkFvpKEVq_z0tNUHBjna2Uh4lVFalZ7l6ms3XPAbcSUe73k5fYGCjVamwHMLZ7_pRGbupeqkyQGXRw-QrP7aUT1WOOzFyTE_3HilOzioXKUvL_R8jjZc679zXtNEbiUty5B8a8wJdp8wXx-qieuxaXg7SGi9ITw-MwODVJIcBjm1ocGHJQ7pe71bB3wLTmIBNL6yBxOirCIe4UfmrvINhe6mPgkdBIStWwsjuaOS5CyvqkSoDygOnPNoq3QSiQr89-A9p7EQ",
       "token_type":"bearer",
       "not-before-policy":0,
       "session_state":"065517c3-3cbd-49fe-99e7-0e18a4cb7ded"
    }
    
this access token can be used to access to securised API zone

    curl -X GET \
      http://localhost:8701/api/v1/tags \
      -H 'Accept: application/json' \
      -H 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJBeUdHWXdXSVBvd3JwWUhwMFhveWVZR0ljbEQyMUxQTXNSSzFtWlB1anJZIn0.eyJqdGkiOiJmZTE4NjZmNC03NGE4LTQzMDgtYjhhOS0yYzMwZGE5MGZhZGIiLCJleHAiOjE1MjQyMzI0MjEsIm5iZiI6MCwiaWF0IjoxNTI0MjMyMTIxLCJpc3MiOiJodHRwOi8va2V5Y2xvYWs6ODA4MC9hdXRoL3JlYWxtcy9sb2dpc2xhbmQiLCJhdWQiOiJsb2dpc2xhbmQtaGlzdG9yaWFuIiwic3ViIjoiYTY2Yzg1NTMtMmU3MC00NDZmLWE5OTQtNDgxNTVmMWNkOTJiIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoibG9naXNsYW5kLWhpc3RvcmlhbiIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6ImUzYjY5MGM3LTcwYjUtNDc0ZC05ZTU2LWVjMWM2ODk0NWY0YSIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJ1bWFfYXV0aG9yaXphdGlvbiIsInVzZXIiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ0b20ifQ.KRv-NXkVCVf1z3vC-Ky4O_hRauN8Cze41b3wIlemWH5ATOE3o6JfXpG2W--aWmlWogOWwN7cDAtb3Vz-A3wS8bD4N2EVxmrRrNUnGFGErzUX4XUmc3SDUShafXp0dC626I1Hrkhyq8GzHWnqS6nGr2BBJGkKrJ5hWAyGuPXgCW6lbIJ4x6XBg8MUsKqj6D6bVUzdbG7MPfePjMfBrcuc4NAJVe0MNVPuCHFdoT0TVIqDFhdWqA1msvSDdN9D212BL0f5GkOYTUBqsaCeJ2b9nfjx-ZYVQ-iW28hG0QXH4RaVDsWXiHb_yf0m93T4LCC8JQE9TCoMngMO_11lhQjULg' \
      -H 'Cache-Control: no-cache' \
      -H 'Content-Type: application/json' \
      -H 'Postman-Token: a1f1faef-72e2-4071-95d2-b558275876f2'

## REST API generation with Swagger

Generate Java code after some API modification in `backend/src/main/swagger/swagger-api.yml` : 

    ./codegen.sh

Get swagger.json doc

    curl -XGET http://historian:8701/v2/api-docs

Generate some sample data

    curl -XPOST  http://historian:8701/api/v1/admin?flush=false


## Docker packaging & publishing
 
Build and run historian as Docker container
 
    mvn -U -X package docker:build
    docker run -idt -p 8701:8701 -e appPort=8701 hurence/historian:latest

    mvn -U -X clean versions:set -DnewVersion=1.0.37
    mvn -U -X package docker:build -Dpush.image=true

