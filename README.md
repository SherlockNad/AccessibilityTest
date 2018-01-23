# AccessibilityTest

This is a test android project to understand and verify when do we get RuntimeException ("Views cannot have both real and virtual children") in ExploreByTouchHelper class. Currently, I'm getting this exception on ASUS (x86) with Adnroid 5.0 only. But if this exception make sense, then it should appear on all other devices and on different android versions.


## What's inside the project:
In MainActivity, I have created a custom pageView (RelativeLayout) and added support to create editText when user taps inside the pageView. Moreover, accessibility support has also been added with a TEST_VIRTUAL_ID. To add accessibilty support, I have created a custom PageViewAccessibilityHelper which extends ExploreBtTouchHelper.

## How to verify RuntimeException("Views cannot have both real and virtual children") in ExploreByTouchHelper:

Install and launch testAccessibility app in your ASUS (x86) with Android 5.0. Tap anywhere in the app, you will hear "Accessibility is working" (this means our added accessibility support with virtualView IDs is working). Now create a editText by tapping on pageView with two fingers (since actual tap in accessibility is simulated by two finger). Then, app should get crashed with this RuntimeException.
