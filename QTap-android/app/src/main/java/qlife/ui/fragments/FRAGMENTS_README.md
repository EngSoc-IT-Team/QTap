# Fragments

These classes are fragments that have specific page UI, and are controlled by activities.

## Agenda Fragment

This fragment shows the user their class schedule. It is an infinite scrollview so that the user can continuously view future classes. There are spaces between classes on the same day, so that a sense of free time can be gotten.
It is accessed by the Agenda field on the navigation bar, or by selecting a date on the Calendar in the Calendar fragment.

## Calendar Fragment

This fragment shows the user a calendar of the current month, and is what the user sees upon login, being the default page. From here the user can select a date and view their class schedule for that day in the Agenda fragment.

This fragment also parses out meaningful data from the strings found by ParseICS in the ICS file. This is done manually, as the ICS file has standard notation that is consistent for all classes.

## Day Fragment

This fragment is currently not in use, but is being created to hold a full day's class schedule on its own.

## Student Tools, Eng Soc, About, ITS, Information

These fragments are currently just placeholders on the navigation bar. Eventually these fragments will be populated with useful features/information.