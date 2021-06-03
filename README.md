# AppointMe

AppointMe is an Android App designed to be an appointment scheduling system for doctors and patients.

AppointMe allows users to register as patients, and schedule an appointment from a selection of doctors.

If the selected doctor is unavailable, the patient will be added to their waiting list, and once they're first in line they will receive a notification. Patients can also cancel an appointment, and sort the doctor's list based on availability.

If you are a doctor on AppointMe, you will be able to view your current waiting list sorted from earliest to latest patient.

AppointMe is written in Java using Android Studio, based on MVC architecture and implementing Google's Cloud Firestore as its database. Every visible list in the UI is implemented by using RecyclerView in order to minimize memory usage. 

A Swipe to refresh mechanism is used to send and receive updates from the DB in an elegant way.

The first patient in the doctor's patients list is the patient that is currently being treated, by default each patient gets a 5 minutes appointment, which after he will be removed from the list and the next patient begin his appointment. 

Doctors that have a green circle icon next to them, means that they have no current patients waiting. Once a patient schedules an appointment with the doctor, he will have a red busy icon next to him.

All calls to the Firestore DB are asynchronous so multiple DB requests can be dealt with simultaneously.

Made by: Or Levi 
Levior9@gmail.com


# Screenshots

Login page            |  Signup page
:-------------------------:|:-------------------------:
![](https://i.imgur.com/LlNOHOL.jpg)  |  ![](https://i.imgur.com/Y1mfXlG.jpg)


Doctors main page           |  Patients main page
:-------------------------:|:-------------------------:
![](https://i.imgur.com/DqanFFq.jpg)  |  ![](https://i.imgur.com/AQHtS2X.jpg)


Firebase - Doctor document           |  Firebase - Patient document
:-------------------------:|:-------------------------:
![](https://i.imgur.com/afj9j0y.jpg)  |  ![](https://i.imgur.com/OYUYDpH.jpg)
