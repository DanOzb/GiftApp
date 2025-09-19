# Project Plan (20 hrs/week)

## Android App Project (Weeks 1–4, Week 8 polish)

### Week 1: Setup and Navigation

* Install and configure Android Studio and emulator.
* Create a new project targeting API 30 minimum.
* Implement BottomNavigationView with 4 tabs: Home, Text, Video, Picture.
* Add placeholder fragments for each tab.
* Document project setup, dependencies, and tools.

Deliverable: Navigation structure with 4 functional tabs.

### Week 2: Home Screen and Gift Logic

* Build Home screen with button to "Open Gift."
* Implement local storage (Room or SharedPreferences) to track unopened/opened gifts.
* On "Open Gift," update local data and move gift to correct tab (text, video, picture).
* Use mock/test data to simulate gifts.

Deliverable: Gift opening functionality with persistence in local storage.

### Week 3: Grid Views and Media Handling

* Implement RecyclerView with GridLayoutManager for text, pictures, and videos.
* Add basic viewers for each type: text viewer, image preview, video playback.
* Add "Save to Gallery" option for images and videos.

Deliverable: Opened gifts display in grids and can be viewed or saved to gallery.

### Week 4: Sync and Offline Caching

* Add startup sync: send GET request to server for new gifts if internet is available.
* Update local storage when sync succeeds.
* Ensure app works fully offline using cached/local data.
* Add error handling for no internet or no server.
* Clean up UI with Material styling and proper layouts.
* Document architecture decisions.

Deliverable: Offline-first Android app with optional cloud sync.
