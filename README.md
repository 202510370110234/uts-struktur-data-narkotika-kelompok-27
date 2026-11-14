# Aplikasi Analisis Putusan Narkotika (UTS Struktur Data - Kelompok 27)

Aplikasi berbasis CLI (*Command Line Interface*) dalam bahasa Java untuk memuat, mengelola, mencari, memfilter, mengurutkan, dan menganalisis statistik 50 data putusan pengadilan kasus narkotika dari berkas CSV menggunakan implementasi struktur data manual.

## Identitas Kelompok
* **Kelompok:** Kelompok 27
* **Mata Kuliah:** Struktur Data (Semester Ganjil 2026/2027 - Universitas Muhammadiyah Malang)
* **Anggota:**
    1. Nur Ilda Yanti - 202510370110241
     2. Muaz Izzudin - 202510370110234

## Struktur Data Pilihan
* **Struktur Data Utama:** **Singly Linked List** (`SinglyLinkedList.java`)
    * **Representasi:** Daftar putusan berantai untuk menyimpan seluruh 50 objek `Putusan` selama aplikasi berjalan.
    * **Operasi Minimum:** `addFirst`, `addLast`, `insert`, `get`, `remove`, dan `search`.
    * **Invariant Utama:** Pointer `head` dan `tail` selalu konsisten, serta node terakhir selalu menunjuk ke `null`.
* **Struktur Data Sekunder:** **Stack** (`AuditStack.java`)
    * **Fungsi:** Menyimpan riwayat operasi/audit log aktivitas aplikasi secara LIFO (*Last-In, First-Out*).
* *(Catatan keputusan arsitektur selengkapnya terdokumentasi di folder `docs/adr`)*.

## Prasyarat Sistem
* Java Development Kit (**JDK 17** atau yang lebih baru)
* Terminal / Command Prompt (dijalankan dari direktori *root* `uts-struktur-data-narkotika-kelompok-27`)

## Cara Build (Kompilasi)
Kompilasi seluruh kode sumber utama (`src/main`) dan kode pengujian (`src/test`) ke dalam folder output `bin`:

**Windows (Command Prompt / PowerShell):**
```bash
mkdir bin
javac -d bin src\main\*.java src\test\*.java