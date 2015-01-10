# LogCleaner

## Introduction
With the many new android phone models coming from everywhere, and since custom roms are even more easy to find and install now, some people stumble on an annoying bug that eats up their internal storage space. 
This is, in my experience, related to the log files growing up without limits and when you add to this a limited internal storage size (often 1 GB only) you have two solutions:
  1. Meddle with a tool to merge/resize partitions
  2. Keep deleting the log files manually from the log folder/s
  
Since I don't like the meddling solutions and since I'm tired to perform manual cleanings for customers and friends (who don't feel expert enough to do that by themself) I chose to write a small app to do this in a easier way and possibly in an automated way.

The code is here in the open, for sure not the best code out there (in my defense this is my first android app and I've learned along the way so cut me some slack please), in the bottom of this readme you can find an url to the apk so you don't need to compile it from scratch.

It's not complete yet and in the ToDo section you can find what I feel like it's missing.

## To Do
1. Test it on different phones to test layout
2. Improve layout removing the few, but still annoying, px margins
3. Add a background service to keep log folders under control
4. Add a tap feedback to Notifications

## Notes
- The app checks and delete files from two directories:
  * /data/log
  * /data/log_other_mode
  
  If you known more I made it easy (but I have the feeling that it may be made even easier) to add them to the app.
  
- The app needs root permission to do the job, so you need to root your phone in a way or another, so just be sure that **su** is available somewhere

## Credits
I've to thanks all the amazing people who answer questions on stackoverflow, many of the solutions I've adopted or derivated from comes from you all.

I've found the icon somewhere on the internet but I couldn't pin point the author, if you are (and have proof obviously) let me known.
