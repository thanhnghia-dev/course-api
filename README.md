# 📌 Course Management Backend

## 🚀 Introduction
This is a **RESTful API Backend** built with **Spring Boot**, designed for a course/training center management system.  
It provides features to manage users, students, courses, and enrollments.

## ⚙️ Technologies
- Java 17
- Spring Boot 3.x
- Spring Data JPA (Hibernate)
- Spring Security (JWT Authentication)
- MySQL
- Maven
- Cloudinary (image storage)

## 📌 Features
- 👩‍🏫 Instructor Management (admin role)
- 🎓 Student Management (create, update, delete, search)
- 📚 Course Management (CRUD + assign students to courses)
- 📝 Enrollment Management
- 🔒 Authentication & Authorization with JWT
- ☁️ Image upload & management with Cloudinary
- 🏷️ **Barcode generation for each student** (fixed per studentId, can be exported/printed on student cards)

## 🔑 Authentication
- Login to receive a **JWT Token**
- Add token to the request header for protected endpoints:  
