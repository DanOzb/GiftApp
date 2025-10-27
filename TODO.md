# TODO for each package 

## data 
✅Implement room database
✅Implement dao
✅Implement repositoryImpl

## di - Hilt dependency injection
✅AppModule
✅FirebaseModule
✅MediaModule 

## domain 
✅ContentBlock -> GiftEntity's content blocks
✅GiftEntity -> used to define a table in db
✅RemoteGift
✅GiftConverter

### Left
✅ContentBlockItem - make sure they use gift and player view models
* Send gift screen - make it use intent to add content
* send gift screen - turn content into remote gift and send to firebase
* make sure app fetches gift correctly


## viewmodel 
✅GiftViewModel -> viewmodel for room db
✅PlayerViewModel -> viewmodel for exoplayer 

## ui 

### Done
✅Gallery Screen: skeleton + uses view model
✅OpeningGiftScreen + uses view model
✅SendGiftScreen skeleton
✅HomeScreen skeleton + uses view model

### Left
* HomeScreen - Add animations for screen transitions into "openGiftScreen"
* HomeScreen - If gifts available, light up, else dark
* SendGiftScreen - Add better ui


