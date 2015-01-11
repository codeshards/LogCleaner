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
- Sdk target starts from v.17, since I needed to use this app on a specific version of android I had to chose this as minimum requirement, this might change sometime in the future. 

- This app has been tested only on a THL W100 con Android v. 4.2.1, so take it as it is because I've no idea of it's behaviour on other devices. 

- The app checks and delete files from two directories:
  * /data/log
  * /data/log_other_mode
  
  If you known more I made it easy (but I have the feeling that it may be made even easier) to add them to the app.
  
- The app needs root permission to do the job, so you need to root your phone in a way or another, so just be sure that **su** is available somewhere

- Currently there are only two languages supported: English (my very bad english) and Italian. I don't plan to add support to any other language to be honest, but whoever feels up to the task is welcome.

- I'm aware that another apps that does the same thing as this exists out there (in fact it's easy to guess that there are many apps alike) but I wanted to solve this by myself and, in the while, learn something new and not to become incredibly rich or something.

## Tested devices 
- THL W100
  * Android 4.2.1 - MOLY.WR8.W1248.MD.WG.MP.V6.P8 - ThL.W100.130912.JBV2.QHD.EN.COM.V01.8P32_V01_MT6589

## Credits
I've to thanks all the amazing people who answer questions on stackoverflow, many of the solutions I've adopted or derivated from comes from you all.

I've found the icon somewhere on the internet but I couldn't pin point the author, if you are (and have proof obviously) let me known.

## Download url
If you want the ready to use apk can be downloaded from [here](http://apk.repository.northernlights.io/logcleaner_1.0.apk).
Again be aware that I didn't had the time to test it on different devices and due to the specific location of the log directory it's entirely possible that doesn't works on all phones.
