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
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/36a6ee27-4cc3-4fc7-8527-5a90014ba2e4" height="600" />

Clicking on the exercises will allow the user to view the exercise in detail.<br />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/3fe1c320-60b2-499a-8f58-9165062f8977" height="600" />

Users can also edit the exercise by using the edit exercise page.<br />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/9233099c-37ab-49a6-881f-f6e3a873ea8b" height="600" />



### Progress Tracker
Users can use this tracker to track the progress of their exercises. When the user is taking a time longer than a certain period, they will receive a notification. (Delay and Message is customizable in settings)<br />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/1720e1f3-c03d-4e3b-acbf-e6d03a173661" height="600" />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/f0e65f49-991f-4f22-85c5-d356ca0bd113" height="600" />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/c590ab00-2307-41ac-9a33-31329f3fbb29" height="600" />

For exercises that have the duration stored, users also have the option to play a beeping sound according to the duration and the number of reps. A beeping sound will occur every second, lasting for a duration specified by the user. 3 Seconds after after the sound stops, the sound will resume, repeating according to the number of repetitions specified by the users. This would help prevent users taking too much break between each rep.<br />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/432e1f60-5423-48d5-9178-e3bc09d6491d" height="600" />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/b9b0ea78-afab-41c5-82a9-7043077ecfd2" height="600" />



Users can also go on breaks between each exercise, and they will also receive a notification if a break is too long. (Delay and Message is customizable in settings)<br />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/01c39cb7-5d8c-436f-94e9-e220831e77f1" height="600" />


During the session, users can also pause, end and reset the session at any time.<br />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/98ab34c1-eb04-41a2-918d-453d25dee3e3" height="600" />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/5896bc60-1fc5-48e3-acae-598a0464496a" height="600" />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/1ffcbdf7-5dee-4cc3-8f77-2dedf6fba71f" height="600" />


### Event logs
Using the event logs, users can take notes about their situation or activities (including their pain or exercises/sports that they do) to track their progress.<br />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/e35aabb4-36a7-4591-8944-0b9d891a9b5c" height="600" />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/c22aa89e-ef4e-40f8-bc39-a726400ddfc9" height="600" />

By press-and-holding an event, a pop-up menu will show up and users will be able to quickly edit the event.<br />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/a26da587-1d22-4b69-841e-049804973aca" height="600" />

Users can also quickly search for an event by searching for keywords or using different filters.<br />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/69a292cc-0129-4ad7-a3e5-5aca27015fbe" height="600" />



### Settings
Using the settings page, users can customize their notification settings (including the delay and message).<br />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/f99088ea-5cbc-4c30-a9c4-ca2664d596d3" height="600" />

Users can also decide whether or not they would use the night mode.<br />
<img src="https://github.com/karenlinky/physio-tracker/assets/61481010/2b63d796-29c3-4c14-be75-fa2a461a0641" height="600" />








