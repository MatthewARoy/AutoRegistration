AutoRegistration
================

An Automatic ACES registration program via headless browser
For the jar file, download from here: https://drive.google.com/file/d/0B49KEI5qgNXVdjJlVi1tS2xKdzA/view?usp=sharing

Once upon a time, I didn't want to get up at 7am to register for classes. I wrote a program that signs up for classes through ACES automatically. My friends and I have been using it for the last few years, but I decided the time has come to share it with Duke as a whole.

You must have your desired classes selected in the book bag, and validate your schedule beforehand.

How to use it:
1) Open the jar file

2) Enter your netid and password into the marked boxes
(optional: enter the time you want to run the registration, and the prep time before. You don't need to mess with this)

3) AFTER MIDNIGHT, press the "Run Registration" button (don't press it multiple times) and leave your computer running overnight.

4) ???

5) ZZZ

6) Profit!

If something goes wrong, it tries again 50 times. After 50 times it begins sounding a buzzer alarm, so it can wake you up if it didn't work. Note, it is roughly 5-15 seconds than a person can be if they start signing up at exactly 7am. This is because ACES lags like crazy, and the program has to wait for the pages to load fully.

To clarify, your bookbag has to have the classes you want to register in selected with a check mark next to them and the schedule validated. Once the program runs, you can just close the GUI.
How it works: Opens up a headless web browser, logs into aces and moves to the bookbag window at the registration time - prep time minutes (default, 7am and 2 minutes prep time).
It then clicks on the "go to enroll page" button, and then clicks the next button too.
I use the java library htmlunit (http://htmlunit.sourceforge.net/). It is the exact same as you loading up a webpage on the server side. 
