# Skenario & Dokumentasi Demo Aplikasi CLI

**Kelompok:** Kelompok 27  
**Kelas Utama:** `Main.java`  
**Dataset:** `data/putusan-50.csv` (50 Putusan Pengadilan Negeri Tanjung Karang)[cite: 5]

---

## Alur Demonstrasi Fitur (Menu `0 - 9`)

### 1. Memulai Aplikasi & Pemuatan Dataset Otomatis
* Jalankan `Main.java`[cite: 8].
* Sistem secara otomatis membaca berkas `data/putusan-50.csv` dan memuat seluruh 50 record ke dalam `SinglyLinkedList`[cite: 5, 7].
* Aktivitas pemuatan otomatis dicatat ke dalam `AuditStack`[cite: 7].

### 2. Menampilkan Ringkasan & Daftar Putusan (Menu `1` & `2`)
* Memilih **Menu 1** menampilkan seluruh daftar perkara yang tersimpan di dalam `SinglyLinkedList` beserta jumlah total record (`50 data`)[cite: 5, 7].
* Memilih **Menu 2** menampilkan detail lengkap satu putusan berdasarkan `Case ID` (contoh input: `P024` atau `P025`)[cite: 5].

### 3. Pencarian & Filter Data (Menu `3` & `4`)
* **Pencarian Case ID:** Memasukkan `P025` akan menjalankan *Linear Search* dan menampilkan jumlah langkah pencarian yang dibutuhkan[cite: 5].
* **Filter Jenis Narkotika:** Memasukkan kata kunci `Sabu` atau `Ganja` menyaring seluruh putusan dengan jenis barang bukti terkait[cite: 5].

### 4. Penambahan & Penghapusan Data Dinamis (Menu `5` & `6`)
* **Tambah Putusan Baru (`P051`):** Menguji operasi penambahan node baru ke dalam `SinglyLinkedList` tanpa mengubah kapasitas array secara manual[cite: 7].
* **Hapus Putusan (`P051`):** Menguji penghapusan node serta memastikan pointer `head`, `tail`, dan `size` tetap konsisten[cite: 7].

### 5. Pengurutan Lama Vonis & Statistik (Menu `7` & `8`)
* Mengurutkan data putusan berdasarkan `vonisBulan` (terendah ke tertinggi / tertinggi ke terendah) menggunakan *Non-Destructive Selection Sort*[cite: 7].
* Menampilkan ringkasan statistik perkara (total perkara, rata-rata tuntutan, rata-rata vonis, dan total denda).

### 6. Riwayat Operasi Stack & Ekspor CSV (Menu `9` & `0`)
* Memeriksa riwayat aktivitas terbaru melalui `AuditStack` (urutan LIFO / aktivitas terakhir muncul paling atas)[cite: 7].
* Mengekspor hasil olahan data ke berkas CSV baru dan keluar dari aplikasi (`0`).

---

## Catatan Pengujian Unit (`SinglyLinkedListTest`)
Sebelum demonstrasi CLI dilakukan, seluruh operasi inti `SinglyLinkedList` (`addFirst`, `addLast`, `insert`, `get`, `remove`, dan `search`) telah diverifikasi lulus 100% melalui kelas pengujian `src/test/SinglyLinkedListTest.java`[cite: 8].