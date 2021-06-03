# AppointMe

AppointMe is an Android App designed to be an appointment scheduling system for doctors and patients.

AppointMe allows users to register as patients, and schedule an appointment from a selection of doctors.
If the selected doctor is unavailable, the patient will be added to their waiting list, and once they're first in line they will receive a notification.
Patients can also cancel an appointment, and sort the doctor's list based on availability.

If you are a doctor on AppointMe, you will be able to view your current waiting list sorted from earliest to latest patient.

AppointMe is written in Java using Android Studio, based on MVC architecture and implementing Google's Cloud Firestore as its database.
Every visible list in the UI is implemented by using RecyclerView in order to minimize memory usage.
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
![](https://i.imgur.com/0RmIN9F.jpg)  |  ![](https://i.imgur.com/8cA6cTg.jpg)
