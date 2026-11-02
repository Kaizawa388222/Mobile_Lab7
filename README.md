# Mobile_Lab7 - ProductRoomApp
**Course:** Mobile Application Programming  
**Week 07:** Local Database Management with Room Database ORM & Repository Pattern  
**Student:** Nguyễn Trung Đức - MSSV: 2474802010092  

## 🏗️ Architecture
- **Package Name:** `edu.vlu.week07.productroom`
- **Database (Room):** `ProductEntity`, `ProductDao`, `AppDatabase` (Thread-safe singleton)
- **Repository:** `ProductRepository` (Single source of truth with `Dispatchers.IO`)
- **ViewModel:** `ProductViewModel` (Survives configuration changes, exposes `LiveData`)
- **UI:** `MainActivity`, `ProductAdapter` (`ListAdapter` + `DiffUtil`), `activity_main.xml` (ConstraintLayout with Guideline & Barrier)

## 📑 Lab Deliverables
- [x] Room Entity, DAO, Database implementation
- [x] Repository and ViewModel implementation
- [x] UI implementation with ListAdapter & DiffUtil
- [x] [DEBUG_REPORT.md](DEBUG_REPORT.md)
- [x] [AI_USAGE.md](AI_USAGE.md)
