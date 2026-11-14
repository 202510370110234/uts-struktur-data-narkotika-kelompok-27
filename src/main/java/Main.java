import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        PutusanService service = new PutusanService();
        Scanner scanner = new Scanner(System.in);

        // Otomatis memuat 50 data dari data/putusan-50.csv saat aplikasi dimulai
        String defaultCsvPath = "data/putusan-50.csv";
        int jumlahMuat = service.imporDariCsv(defaultCsvPath);
        System.out.println("=== APLIKASI ANALISIS PUTUSAN NARKOTIKA (SINGLY LINKED LIST) ===");
        System.out.println("Status awal: " + jumlahMuat + " record berhasil dimuat dari " + defaultCsvPath);
        System.out.println("Validasi Invariant LinkedList: " + service.getDaftarPutusan().validateInvariant());

        boolean berjalan = true;
        while (berjalan) {
            tampilkanMenu();
            System.out.print("Pilih menu (1-0): ");
            String pilihan = scanner.nextLine().trim();

            switch (pilihan) {
                case "1":
                    tampilkanDaftar(service.getDaftarPutusan(), "SEMUA DATA PUTUSAN");
                    break;

                case "2":
                    System.out.print("Masukkan Case ID yang dicari (contoh: P001 atau P025): ");
                    String cariId = scanner.nextLine();
                    Putusan ditemukan = service.cariByCaseId(cariId);
                    if (ditemukan != null) {
                        cetakDetailPutusan(ditemukan);
                    }
                    break;

                case "3":
                    System.out.println("\n--- TAMBAH DATA PUTUSAN BARU ---");
                    System.out.print("Case ID (contoh P051): ");
                    String idBaru = scanner.nextLine().trim();
                    System.out.print("Nomor Perkara: ");
                    String noPerkara = scanner.nextLine().trim();
                    System.out.print("Pengadilan: ");
                    String pengadilan = scanner.nextLine().trim();
                    System.out.print("Jenis Narkotika (Sabu/Ganja/Ekstasi): ");
                    String jenis = scanner.nextLine().trim();
                    System.out.print("Berat Barang Bukti (gram): ");
                    double berat = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Vonis Penjara (bulan): ");
                    int vonis = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Denda (rupiah, angka saja): ");
                    long denda = Long.parseLong(scanner.nextLine().trim());

                    Putusan baru = new Putusan(
                            idBaru, noPerkara, pengadilan, LocalDate.now(),
                            "TDW-" + idBaru, jenis, berat,
                            "Pasal 112 ayat (1)", "Pasal 112 ayat (1)",
                            vonis + 12, vonis, denda,
                            berat + " gram " + jenis,
                            "Tidak mendukung program pemerintah", "Berterus terang", "hal 1-10"
                    );
                    service.tambahPutusan(baru);
                    System.out.println("Berhasil ditambahkan! Total data sekarang: " + service.getDaftarPutusan().size());
                    break;

                case "4":
                    System.out.print("Masukkan Case ID yang akan dihapus: ");
                    String hapusId = scanner.nextLine();
                    Putusan terhapus = service.hapusByCaseId(hapusId);
                    if (terhapus != null) {
                        System.out.println("Berhasil menghapus: " + terhapus);
                        System.out.println("Validasi Invariant setelah hapus: " + service.getDaftarPutusan().validateInvariant());
                    } else {
                        System.out.println("Gagal: Case ID tidak ditemukan.");
                    }
                    break;

                case "5":
                    System.out.print("Filter berdasarkan Jenis Narkotika (contoh: Sabu / Ganja): ");
                    String filterJenis = scanner.nextLine();
                    SinglyLinkedList<Putusan> hasilFilter = service.filterByJenisNarkotika(filterJenis);
                    tampilkanDaftar(hasilFilter, "HASIL FILTER: " + filterJenis.toUpperCase());
                    break;

                case "6":
                    System.out.print("Urutkan vonis (1 = Terendah ke Tertinggi, 2 = Tertinggi ke Terendah): ");
                    String arah = scanner.nextLine().trim();
                    boolean asc = !arah.equals("2");
                    SinglyLinkedList<Putusan> hasilUrut = service.urutkanByVonis(asc);
                    tampilkanDaftar(hasilUrut, "HASIL URUT VONIS (" + (asc ? "ASC" : "DESC") + ")");
                    break;

                case "7":
                    service.tampilkanStatistik();
                    break;

                case "8":
                    tampilkanRiwayatAudit(service.getRiwayatAudit());
                    break;

                case "9":
                    String outputPath = "data/putusan-export.csv";
                    boolean sukses = service.eksporKeCsv(service.getDaftarPutusan(), outputPath);
                    if (sukses) {
                        System.out.println("Data berhasil diekspor ke: " + outputPath);
                    }
                    break;

                case "0":
                    berjalan = false;
                    System.out.println("Program selesai. Terima kasih!");
                    break;

                default:
                    System.out.println("Pilihan tidak valid, silakan masukkan angka 0-9.");
            }
        }
        scanner.close();
    }

    private static void tampilkanMenu() {
        System.out.println("\n-------------------------------------------------------------");
        System.out.println("MENU UTAMA:");
        System.out.println("1. Tampilkan Semua Data Putusan (" + "SinglyLinkedList)");
        System.out.println("2. Cari Putusan berdasarkan Case ID (+ Hitung Langkah)");
        System.out.println("3. Tambah Putusan Baru");
        System.out.println("4. Hapus Putusan berdasarkan Case ID");
        System.out.println("5. Filter Putusan berdasarkan Jenis Narkotika");
        System.out.println("6. Urutkan Putusan berdasarkan Lama Vonis");
        System.out.println("7. Tampilkan Agregasi & Statistik Vonis/Barang Bukti");
        System.out.println("8. Lihat Riwayat Operasi / Audit Log (AuditStack LIFO)");
        System.out.println("9. Ekspor Data ke CSV (data/putusan-export.csv)");
        System.out.println("0. Keluar");
        System.out.println("-------------------------------------------------------------");
    }

    private static void tampilkanDaftar(SinglyLinkedList<Putusan> list, String judul) {
        System.out.println("\n=== " + judul + " (Total: " + list.size() + " record) ===");
        if (list.isEmpty()) {
            System.out.println("(Tidak ada data)");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%2d. %s%n", (i + 1), list.get(i));
        }
    }

    private static void cetakDetailPutusan(Putusan p) {
        System.out.println("\n--- DETAIL LENGKAP PUTUSAN ---");
        System.out.println("Case ID            : " + p.getCaseId());
        System.out.println("Nomor Perkara      : " + p.getNomorPerkara());
        System.out.println("Pengadilan         : " + p.getPengadilan());
        System.out.println("Tanggal Putusan    : " + p.getTanggalPutusan());
        System.out.println("Terdakwa           : " + p.getTerdakwaKode());
        System.out.println("Jenis Narkotika    : " + p.getJenisNarkotika());
        System.out.println("Berat Barang Bukti : " + p.getBeratBarangBuktiGram() + " gram");
        System.out.println("Pasal Dakwaan      : " + p.getPasalDakwaan());
        System.out.println("Pasal Terbukti     : " + p.getPasalTerbukti());
        System.out.println("Tuntutan / Vonis   : " + p.getTuntutanBulan() + " bulan / " + p.getVonisBulan() + " bulan");
        System.out.printf("Pidana Denda       : Rp%,d%n", p.getDendaRupiah());
        System.out.println("Barang Bukti       : " + p.getBarangBukti());
        System.out.println("Memberatkan        : " + p.getKeadaanMemberatkan());
        System.out.println("Meringankan        : " + p.getKeadaanMeringankan());
        System.out.println("Sumber Halaman     : " + p.getSourcePages());
    }

    private static void tampilkanRiwayatAudit(AuditStack<String> stack) {
        System.out.println("\n=== RIWAYAT OPERASI / AUDIT LOG (TERBARU DI ATAS - LIFO) ===");
        if (stack.isEmpty()) {
            System.out.println("(Belum ada riwayat operasi)");
            return;
        }
        for (int i = 0; i < stack.size(); i++) {
            System.out.println((i + 1) + ". " + stack.getFromTop(i));
        }
    }
}