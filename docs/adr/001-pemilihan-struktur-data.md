# ADR 001: Pemilihan Struktur Data Utama dan Sekunder

* **Status:** Diterima (Accepted)
* **Tanggal:** 14 November 2026
* **Kelompok:** Kelompok 27

## Konteks
Aplikasi perlu memuat dan mengelola 50 record data putusan perkara narkotika dari berkas `data/putusan-50.csv` ke dalam memori utama selama aplikasi berjalan, mendukung operasi penambahan (`add`), penyisipan (`insert`), penghapusan (`remove`), akses indeks (`get`), pencarian (`search`), serta mencatat riwayat aktivitas pengguna tanpa bergantung pada `java.util` collections bawaan.

## Keputusan
1. **Struktur Data Utama — Singly Linked List (`SinglyLinkedList.java`):**
    * Digunakan sebagai penyimpanan utama seluruh 50 objek `Putusan` dengan pointer `head` dan `tail` yang dijaga konsistensinya (`tail.next == null`).
    * Mendukung operasi dinamis penambahan dan penghapusan node tanpa perlu melakukan *resizing* kapasitas array secara manual.
2. **Struktur Data Sekunder — Stack (`AuditStack.java`):**
    * Digunakan sebagai log riwayat operasi (*audit trail*) dengan prinsip **LIFO (*Last-In, First-Out*)** sehingga aktivitas terbaru (impor CSV, pencarian, penambahan, penghapusan, filter, sorting, hingga ekspor) selalu berada di posisi paling atas (`top`) saat ditinjau pengguna.

## Konsekuensi
* **Positif:** Alokasi memori bersifat dinamis sesuai jumlah record, menjaga *invariant* `head`/`tail` tetap terukur melalui pengujian unit (`SinglyLinkedListTest`), serta riwayat aktivitas tercatat kronologis terbalik secara alami menggunakan Stack.
* **Negatif:** Akses elemen berdasarkan indeks (`get(i)`) membutuhkan penelusuran dari `head` dengan kompleksitas waktu $O(n)$ dibandingkan akses langsung $O(1)$ pada array.