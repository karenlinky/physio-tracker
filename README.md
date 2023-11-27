# Physio Tracker
As someone in the midst of physiotherapy, I've got a routine of over 10 exercises scheduled 5 times a week. But I realized I've fallen into a bit of a habit - taking longer breaks between each exercise or set than I should. To help with this, I've developed an app that keeps track of how long I spend on each exercise and on breaks. It kindly reminds me with notifications when it's time to get back into my exercises.


## Skills involved
- Android Development
- Java
- Room (Local Storage)
- ExecutorService (to handle interactions with the database)
- LiveData (to get notified when there is a change in data)
- AlarmManager, BroadcastReceiver, Notification classes (to schedule notifications)
- Singleton Design Pattern (Handling database and sharedPreferences used for notification settings)
- Observer Pattern (Handling the status of the tracker)
  - TrackerStatusSubject contains the status of the tracker (i.e. what state of the session is the user at - not started, working out, on a break, completed; if the session is paused; the timestamp when the exercise/break is started)
  - All the buttons, text, exercise list are managed by an Observer class
  - Every time there is a status change in the tracker, the Observers get notified, and the UI elements will change accordingly
- Fragments (reusable fragments to display the exercise details (number of sets, number of reps, and duration of each exercise))
- RecyclerView (to display a list of exercises)

## Usage
### Exercises
The home page allows users to view the list of exercises of the user.<br />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/a75b843a-fd9e-40db-abe0-8f16f9437d0a" height="600" />

Clicking on the exercises will allow the user to view the exercise in detail.<br />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/6dc6c675-4421-4cb0-a359-8a0abb361eaa" height="600" />

Users can also edit the exercise by using the edit exercise page.<br />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/10058a58-d797-4858-acfb-82fdd3324efb" height="600" />


### Progress Tracker
Users can use this tracker to track the progress of their exercises. When the user is taking a time longer than a certain period, they will receive a notification. (Delay and Message is customizable in settings)<br />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/e7f3b512-f66f-4c2f-bec5-ce4f28a5636a" height="600" />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/c253f9d8-3dfd-4d83-9736-1fea91641df3" height="600" />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/adbe9d98-161f-4f82-9e5d-9f1fa0a56103" height="600" />



Users can also go on breaks between each exercise, and they will also receive a notification if a break is too long. (Delay and Message is customizable in settings)<br />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/f12331a5-79ed-4b22-b58f-ce037a74d0b8" height="600" />

During the session, users can also pause, end and reset the session at any time.<br />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/2227b2d6-9427-459e-ab5c-85092d463d90" height="600" />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/d05ec496-7d12-44cd-9425-761ac10489d5" height="600" />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/3123ef5c-4466-4e9e-80e2-9c630332bf40" height="600" />


### Settings
Users can customize their notification settings (including the delay and message).<br />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/95ed77f6-5430-4b33-a92f-ca98810173f0" height="600" />







