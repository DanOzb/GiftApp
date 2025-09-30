# Notes for self

## Needed:
* Add thumbnails to view in the videos gallery 
* Setup firebase 
* Test & polish

## Priority 
* Setup firebase 
* Add fetching function to gift repository. Fetch from json to RemoteGift and 
from RemoteGift to GiftEntity via GiftRepositoryimpl

## How it should work 

* First time opening gift -> App downloads text + images -> ExoPlayer caches and downloads if theres any video
* Gifts disappear in 10 days from the app, user has to favorite it to make it stay on app
* Deleted gifts stay on cloud, just in case