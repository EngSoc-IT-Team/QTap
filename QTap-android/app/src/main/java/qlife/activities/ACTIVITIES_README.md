# Activities

In this package all of the app's activities can be found. These define the main pages in the application, and are used to control what UI is shown (based on what fragments are used).

## LoginActivity

This activity shows a web view of Solus and allows the user to log into Solus. Upon Solus log in, the URL of the ICS file is found by manually looking for the user's NetID on the page, since the URL contains the NetID. Then, that link i
used to download the ICS file. After this point, the user is logged into the application. The user is given a loading screen to allow for the time to download the file, and parse it into the SQLite database.

## MainTabActivity

This activity is the main guts of the app. The user comes into MainTabActivity and is shown a screen with a calendar on it. From here, there is an options menu (the dots) in the top right to go to the settings screen. Also, there is a navigation bar that can be
swiped open on the left side of the screen. With this bar, the user can navigate to an agenda view, where there is an infinite scroll view showing the user's classes for each day, with appropriate spacing showing free time.
By selecting a date on the calendar, the agenda is opened at that day. There is also a week view where a full week's class schedule is shown. Finally, the last implemented part of the navigation bar (currently) is
the tab to go to Google Maps within the app.

## SettingsActivity

This activity shows the settings screen, which gives user customization and other options. The user can navigate here from MainTabActivity, and cannot navigate elsewhere from SettingsActivity, unless they log out.
The log out button will wipe the database holding sensitize information and wipe the browser cache, so that the user is no longer logged into Solus. Other features currently here are user information shown and links
to outside information resources.

## StartupActivity

This activity is shown the first time the user launches the app. It contains a few slides with some pictures and text that describes the uses of the app and gives a bit of an introduction. Once the user has skipped/
flipped through, they are sent to the MainTabActivity, at which they are re-directed to the LoginActivity to sign into Solus.