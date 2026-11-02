# DEBUG REPORT: INTENTIONAL DEBUGGING CHALLENGE

**Student:** Nguyễn Trung Đức  
**MSSV:** 2474802010092  
**Course:** Mobile Application Programming (Week 07: Room ORM & Repository Pattern)

---

## 1. Error 1: Missing `@PrimaryKey` in Entity (Build-time Error)

### 📌 Snippet with Error:
```kotlin
// NoteEntity.kt (ERROR 1)
@Entity(tableName = "notes")
data class NoteEntity(
    val id: Long = 0L, // ❌ Missing @PrimaryKey -> build fail
    val content: String
)
```

### 🔴 Expected Error Message:
```text
error: An entity must have at least 1 field annotated with @PrimaryKey
```

### 🔍 Explanation:
In SQLite and Room ORM, every relational table must have a unique identifier (Primary Key) to uniquely distinguish each row. Without the `@PrimaryKey` annotation, Room's annotation processor (KSP / KAPT) cannot determine which column serves as the table's key, causing compilation to fail immediately.

### 🟢 Fix:
Annotate `id` with `@PrimaryKey(autoGenerate = true)`:
```kotlin
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val content: String
)
```

---

## 2. Error 2: Main-thread Database Access (Runtime Crash)

### 📌 Snippet with Error:
```kotlin
// NoteRepository.kt (ERROR 2)
class NoteRepository(private val dao: NoteDao) {
    fun add(note: NoteEntity) {
        dao.insert(note) // ❌ suspend call on main thread -> crash
    }
}
```

### 🔴 Expected Error Message:
```text
java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
```

### 🔍 Explanation:
Room strictly forbids executing database operations on the UI/Main Thread by default because disk I/O operations can take variable amounts of time, causing UI stutter or Application Not Responding (ANR) dialogs. Furthermore, `dao.insert` is declared as a `suspend` function, which cannot be invoked directly from a regular non-suspending synchronous function on the main thread without a coroutine context.

### 🟢 Fix:
Make `add` a `suspend` function and switch to background I/O dispatcher using `withContext(Dispatchers.IO)`:
```kotlin
class NoteRepository(
    private val dao: NoteDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun add(note: NoteEntity) = withContext(ioDispatcher) {
        dao.insert(note)
    }
}
```
In the ViewModel / UI layer, call `repository.add(note)` inside `viewModelScope.launch { ... }` or `lifecycleScope.launch { ... }`.
