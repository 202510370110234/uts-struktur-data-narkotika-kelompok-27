# Trace Operasi Struktur Data & Algoritma

**Kelompok:** Kelompok 27  
**Struktur Data Utama:** `SinglyLinkedList` (`head`, `tail`, `size`)[cite: 7]  
**Struktur Data Sekunder:** `AuditStack` (LIFO — `top`)[cite: 7]

---

## 1. Trace Penyisipan & Pembentukan Linked List (`addLast` / `insert`)[cite: 7]
Simulasi saat memuat 3 data awal dari `data/putusan-50.csv` ke dalam `SinglyLinkedList`[cite: 5, 7]:

| Langkah | Operasi | Kondisi `head` | Kondisi `tail` | `size` | Invariant (`tail.next == null`) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | Inisialisasi `new SinglyLinkedList()` | `null` | `null` | `0` | Terpenuhi (list kosong) |
| 1 | `addLast(P001)` | `Node(P001)` | `Node(P001)` | `1` | `P001.next == null` (Valid) |
| 2 | `addLast(P002)` | `Node(P001)` | `Node(P002)` | `2` | `P001.next -> P002`, `P002.next == null` (Valid) |
| 3 | `addLast(P003)` | `Node(P001)` | `Node(P003)` | `3` | `P002.next -> P003`, `P003.next == null` (Valid) |

---

## 2. Trace Linear Search (`cariByCaseId`)[cite: 7]
Simulasi pencarian perkara dengan `Case ID = "P025"` pada 50 record (`N = 50`)[cite: 5, 7]:

* **Langkah 1:** Cek indeks `0` (`P001`) $\rightarrow$ `"P001".equalsIgnoreCase("P025")` = `false`
* **Langkah 2:** Cek indeks `1` (`P002`) $\rightarrow$ `"P002".equalsIgnoreCase("P025")` = `false`
* ... *(penelusuran berlanjut secara sekuensial node demi node)* ...
* **Langkah 25:** Cek indeks `24` (`P025`) $\rightarrow$ `"P025".equalsIgnoreCase("P025")` = `true` (**Ditemukan dalam 25 langkah**, kompleksitas $O(n)$).

---

## 3. Trace Selection Sort Non-Destruktif (`urutkanByVonis`)[cite: 7]
Simulasi pengurutan 4 sampel putusan berdasarkan `vonisBulan` secara *Ascending*:
* **Data Awal di Linked List:** `[P001 (96 bln), P002 (60 bln), P003 (120 bln), P004 (72 bln)]`

| Iterasi (`i`) | Indeks Minimum Ditemukan (`minIdx`) | Pertukaran (*Swap*) | Susunan Array Sementara |
| :--- | :--- | :--- | :--- |
| `i = 0` | `1` (`P002` - 60 bln) | Tukar indeks `0` & `1` | `[P002 (60), P001 (96), P003 (120), P004 (72)]` |
| `i = 1` | `3` (`P004` - 72 bln) | Tukar indeks `1` & `3` | `[P002 (60), P004 (72), P003 (120), P001 (96)]` |
| `i = 2` | `3` (`P001` - 96 bln) | Tukar indeks `2` & `3` | `[P002 (60), P004 (72), P001 (96), P003 (120)]` |

* **Hasil:** Dibangun objek `SinglyLinkedList` baru dari array terurut di atas, sementara urutan asli pada `SinglyLinkedList` utama tetap tidak berubah.

---

## 4. Trace Audit Stack (`AuditStack` — LIFO)[cite: 7]
Simulasi pencatatan aktivitas pengguna selama satu sesi aplikasi:

1. `push("IMPORT_CSV: Berhasil memuat 50 record dari data/putusan-50.csv")` $\rightarrow$ `top` menunjuk ke `IMPORT_CSV`.
2. `push("SEARCH_ID: Mencari Case ID P025 (Ditemukan)")` $\rightarrow$ `top` menunjuk ke `SEARCH_ID`.
3. `push("SORT_VONIS: Mengurutkan data berdasarkan vonis (ASC)")` $\rightarrow$ `top` menunjuk ke `SORT_VONIS`.
4. **Saat pengguna memilih menu Lihat Riwayat (Stack):** Data ditampilkan mulai dari `top` (`SORT_VONIS` $\rightarrow$ `SEARCH_ID` $\rightarrow$ `IMPORT_CSV`).