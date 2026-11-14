# ADR 002: Strategi Pencarian (Searching) dan Pengurutan (Sorting)

* **Status:** Diterima (Accepted)
* **Tanggal:** 14 November 2026
* **Kelompok:** Kelompok 27

## Konteks
Aplikasi membutuhkan fitur pencarian data putusan berdasarkan `Case ID` (untuk detail maupun penghapusan), pemfilteran berdasarkan `Jenis Narkotika`, serta pengurutan data berdasarkan `Lama Vonis` (ascending maupun descending) di atas struktur data utama `SinglyLinkedList` berisi 50 record tanpa merusak urutan asli dataset.

## Keputusan
1. **Strategi Pencarian — Linear Search ($O(n)$):**
    * Diterapkan pada `cariByCaseId`, `hapusByCaseId`, dan `filterByJenisNarkotika` dengan menelusuri elemen dari indeks `0` hingga `size - 1`.
    * Pencarian dilakukan secara *case-insensitive* dan dilengkapi penghitung langkah (`langkah`) untuk mengamati performa penelusuran secara langsung.
2. **Strategi Pengurutan — Non-Destructive Selection Sort ($O(n^2)$):**
    * Diterapkan pada `urutkanByVonis(boolean ascending)` dengan menyalin referensi `Putusan` ke array sementara, melakukan pertukaran elemen minimum/maksimum menggunakan **Selection Sort**, lalu membangun `SinglyLinkedList` baru sebagai hasil keluaran.

## Alasan & Trade-off
* **Mengapa Linear Search dipilih dibanding Binary Search?**
    * Binary Search mewajibkan data dalam keadaan terurut terlebih dahulu dan membutuhkan akses acak (*random access*) $O(1)$, sedangkan `SinglyLinkedList` tidak mendukung akses acak cepat dan dataset awal tidak selalu terurut berdasarkan kata kunci pencarian. Untuk skala $N = 50$, kompleksitas $O(n)$ sangat efisien dan tidak memerlukan overhead pengurutan ulang setiap kali data baru ditambahkan.
* **Mengapa Selection Sort Non-Destruktif dipilih?**
    * Mengubah pointer langsung di dalam `SinglyLinkedList` saat *sorting* akan merusak urutan asli data dari CSV. Dengan menyalin referensi ke array sementara terlebih dahulu, kompleksitas waktu tetap $O(n^2)$ (sangat cepat untuk $N = 50$ dengan jumlah pertukaran/swap minimal yakni maksimal $n-1$ kali) dan struktur data utama tetap utuh.