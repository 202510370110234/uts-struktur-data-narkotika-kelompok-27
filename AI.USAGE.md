# Catatan Penggunaan AI (AI Usage Log)

**Kelompok:** Kelompok 27  
**Mata Kuliah:** Struktur Data (UTS - Semester Ganjil 2026/2027)

## Ringkasan Penggunaan Alat AI
Dalam pengerjaan proyek UTS ini, kelompok kami memanfaatkan asisten AI (**Gemini** dan **ChatGPT**) secara terbatas dan bertanggung jawab sebagai alat bantu konsultasi teknis, *debugging*, dan penyusunan dokumentasi, dengan tetap menjaga keaslian implementasi kode inti serta validasi data manual.

## Detail Pemanfaatan AI

| Area / Komponen | Alat AI | Tujuan Penggunaan AI | Verifikasi & Tindakan Manusia |
| :--- | :--- | :--- | :--- |
| **Struktur Data, Algoritma & CLI** (`SinglyLinkedList`, `AuditStack`, `PutusanService`, `Main`) | ChatGPT & Gemini | Diskusi konsep *invariant* pada *Singly Linked List*, perilaku LIFO pada *AuditStack*, *debugging* *parsing* CSV, penyusunan antarmuka menu CLI, serta analisis kompleksitas waktu $O(n)$ untuk *Linear Search* dan $O(n^2)$ untuk *Selection Sort* non-destruktif. | Seluruh kode Java dikompilasi, dijalankan, dan diuji secara mandiri menggunakan `SinglyLinkedListTest` serta skenario menu CLI `0-9` di `Main.java`. |
| **Dokumentasi Proyek** (`README.md`, `docs/adr/`, `CONTRIBUTIONS.md`, `AI_USAGE.md`) | Gemini | Membantu menyusun kerangka penulisan Markdown agar terstruktur rapi dan mencakup seluruh poin yang dipersyaratkan pada panduan pengumpulan tugas. | Memeriksa dan menyesuaikan seluruh isi dokumen agar akurat sesuai dengan struktur berkas, perintah kompilasi, NIM anggota, dan alur program yang sebenarnya. |
| **Dataset Putusan** (`data/putusan-50.csv` & `docs/data-provenance.csv`) | *Tidak menggunakan AI* | Tidak menggunakan AI untuk mengarang atau membuat data putusan fiktif. | Seluruh 50 data perkara diekstrak dan diverifikasi secara manual oleh anggota kelompok dari dokumen putusan pengadilan asli sesuai catatan di `docs/data-provenance.csv`. |

## Pernyataan Kejujuran Akademik
Kami menyatakan bahwa seluruh bagian kode sumber dan dokumentasi yang dihasilkan dengan bantuan AI (Gemini maupun ChatGPT) telah kami pahami cara kerjanya, kami uji langsung pada lingkungan pengembangan (IntelliJ IDEA / JDK 17), dan disesuaikan sepenuhnya dengan kebutuhan proyek Kelompok 27.