
📚 Introduction:

This project implements a complete User Registration Flow for Android using Java, based on the provided Figma Design and API documentation. The flow guides the user from email registration to profile completion and onboarding.


💻 Tech Stack:

- Android Studio
- Java
- Retrofit (for networking)
- Data Binding
- ViewModel + LiveData (for lifecycle handling)
- Material Components  
- Custom UI Elements (OTP View)
- Glide (For Gif image rendering)

🚀 Features:

-  Multi-step registration
-  Input validations
-  API integration
-  OTP verification
-  Multi-select components (hardcoded value for Interest Selection & Wellbeing Pillars)
-  Gif Loader and Material Components (using DatePickerDialog for Birthday selection)
-  Welcome screen on successful registration

📲 Registration Flow Breakdown:

Step 1 — Register with Email & Password

○ Fields: Email, Password
○ Validations: - Email format check and Password Min 8 characters 

Step 2 — OTP Verification

○ Fields: 6-digit OTP input
○ Auto-focus on next field after each digit

Step 3 — Complete Profile

- First Name
- Last Name
- Email
- Password
- Phone Number
- Date of Birth (DOB)

Step 4 — Interest Selection

- Multi-select list (hardcoded value)

Step 5 — Wellbeing Pillars

- Multi-select (hardcoded value)

Step 6 — Registration Completion

- Submit all collected data via API
- Show the loader

Step 7 — Welcome Screen

- Confirmation of successful onboarding


🧪 Validations & UX 

Email - Must follow valid email pattern  
Password - Min 8 chars
Phone - 10-digit numeric only  
OTP - 6-digit input, auto-focus after each digit
Birthday - Must be in MM/DD/YYYY format 


🔗 API Integration

- All steps are integrated with provided API endpoints. Click here
- Retrofit is used to manage HTTP calls and JSON parsing.

🖼 UI/UX

- Designed according to the provided Figma design. Click here
- Responsive across device sizes.
- Includes smooth navigation between steps. 

🧩 Folder Structure

app/

├ ui/

├ interfaces/

├ network/

├ data/

├ repository/

├ utils/

├ viewmodel/


📦 Installation & Setup

1. Clone the repository using git clone https://github.com/mayank18p/AndroidCodingChallenge.git
2. Open the project in Android Studio.
3. Build and run the App on an emulator or physical device.

📸 Applications Screenshots

<img width="1080" height="2160" alt="Screenshot_20250726_194125" src="https://github.com/user-attachments/assets/9d775bb0-e06e-49a7-9e00-3f77ec740a25" />

<img width="1080" height="2160" alt="Screenshot_20250727_142103" src="https://github.com/user-attachments/assets/e92762c8-25f9-46a7-b352-bf170ec275ca" />

<img width="1080" height="2160" alt="Screenshot_20250727_142330" src="https://github.com/user-attachments/assets/57c5348c-4676-41fd-86fd-499e9047e75e" />

<img width="1080" height="2160" alt="Screenshot_20250727_143710" src="https://github.com/user-attachments/assets/d33784cd-766d-4875-bf62-e8e716840c3f" />

<img width="1080" height="2160" alt="Screenshot_20250727_143252" src="https://github.com/user-attachments/assets/dc9ca6a4-2d06-469c-a444-93f37d7469a5" />

<img width="1080" height="2160" alt="Screenshot_20250727_142515" src="https://github.com/user-attachments/assets/2bd98df6-9794-4fbb-8db0-0b3ef6c03977" />

<img width="1080" height="2160" alt="Screenshot_20250727_142543" src="https://github.com/user-attachments/assets/a31d9742-89e1-4761-8cdd-54ebad6ce959" />

<img width="1080" height="2160" alt="Screenshot_20250727_142610" src="https://github.com/user-attachments/assets/3febed96-c0e7-4915-809a-eaafeaef5614" />

<img width="1080" height="2160" alt="Screenshot_20250727_141035" src="https://github.com/user-attachments/assets/713f5b11-bb51-4176-83cc-8eca22d9e5ac" />

<img width="1080" height="2160" alt="Screenshot_20250727_141112" src="https://github.com/user-attachments/assets/122c61f4-1ec7-425c-a059-738d372d4cdc" />











