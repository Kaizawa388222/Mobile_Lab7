# AI USAGE DISCLOSURE (Pe.AI Compliance)

**Student:** Nguyễn Trung Đức  
**MSSV:** 2474802010092  
**Course:** Mobile Application Programming (Week 07)  
**Project:** ProductRoomApp (Repository: Mobile_Lab7)  

---

## 1. Extent of AI Assistance
In accordance with the course's Pe.AI policy (Permitted AI up to 30–50%), generative AI was utilized for:
* Checking syntax compatibility between Room ORM, Kotlin 2.2, and Kotlin Symbol Processing (KSP2).
* Verifying the implementation of Room `@Entity`, `@Dao`, and `@Database` annotations.
* Creating initial template scaffolding for ConstraintLayout Barriers and Guidelines.
* Formulating the explanation for the Intentional Debugging Challenge (Section 5).

## 2. Student Understanding & Defense Readiness
All architectural components have been thoroughly reviewed and understood:
1. **Entity Mapping:** `ProductEntity` maps to SQLite table `products` with auto-generating Long primary key and custom column names.
2. **DAO Operations:** Suspend functions for asynchronous write operations (`insert`, `update`, `delete`, `deleteAll`), and reactive `LiveData<List<ProductEntity>>` for `observeAll()`.
3. **Thread-Safe Singleton:** `AppDatabase` utilizes `@Volatile` and double-checked locking inside `synchronized(this)` to ensure a single database instance across threads.
4. **Repository Pattern:** `ProductRepository` acts as the single source of truth, managing execution dispatch via `Dispatchers.IO`.
5. **ViewModel & UI Integration:** `ProductViewModel` exposes LiveData to `MainActivity`, using `ListAdapter` with `DiffUtil` for optimized UI updates.
