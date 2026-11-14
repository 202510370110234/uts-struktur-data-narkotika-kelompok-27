import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class PutusanService {

    private final SinglyLinkedList<Putusan> daftarPutusan;
    private final AuditStack<String> riwayatAudit;

    public PutusanService() {
        this.daftarPutusan = new SinglyLinkedList<>();
        this.riwayatAudit = new AuditStack<>();
    }

    public SinglyLinkedList<Putusan> getDaftarPutusan() {
        return daftarPutusan;
    }

    public AuditStack<String> getRiwayatAudit() {
        return riwayatAudit;
    }

    // 1. IMPOR CSV (Mendukung pemisah koma ',' atau titik koma ';' dan tanda kutip)
    public int imporDariCsv(String filePath) {
        daftarPutusan.clear();
        int berhasil = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String header = br.readLine();
            if (header == null) return 0;

            char delimiter = header.contains(";") ? ';' : ',';
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] kolom = parseCsvLine(line, delimiter);
                if (kolom.length < 16) continue;

                Putusan p = new Putusan(
                        kolom[0].trim(),
                        kolom[1].trim(),
                        kolom[2].trim(),
                        parseTanggal(kolom[3].trim()),
                        kolom[4].trim(),
                        kolom[5].trim(),
                        parseDoubleSafe(kolom[6].trim()),
                        kolom[7].trim(),
                        kolom[8].trim(),
                        parseIntSafe(kolom[9].trim()),
                        parseIntSafe(kolom[10].trim()),
                        parseLongSafe(kolom[11].trim()),
                        kolom[12].trim(),
                        kolom[13].trim(),
                        kolom[14].trim(),
                        kolom[15].trim()
                );
                daftarPutusan.add(p);
                berhasil++;
            }
            riwayatAudit.push("IMPOR CSV: Memuat " + berhasil + " record dari " + filePath);
        } catch (IOException e) {
            System.out.println("Gagal membaca file CSV: " + e.getMessage());
        }
        return berhasil;
    }

    // 2. PENCARIAN BERDASARKAN CASE ID + MENGHITUNG JUMLAH LANGKAH (KOMPLEKSITAS O(n))
    public Putusan cariByCaseId(String caseId) {
        int langkah = 0;
        for (int i = 0; i < daftarPutusan.size(); i++) {
            langkah++;
            Putusan p = daftarPutusan.get(i);
            if (p.getCaseId().equalsIgnoreCase(caseId.trim())) {
                riwayatAudit.push("CARI: Case ID '" + caseId + "' ditemukan pada indeks " + i + " (" + langkah + " langkah)");
                System.out.println("-> Ditemukan pada indeks ke-" + i + " dalam " + langkah + " langkah penelusuran.");
                return p;
            }
        }
        riwayatAudit.push("CARI: Case ID '" + caseId + "' tidak ditemukan (" + langkah + " langkah)");
        System.out.println("-> Data tidak ditemukan setelah menelusuri " + langkah + " node.");
        return null;
    }

    // 3. TAMBAH DATA BARU
    public void tambahPutusan(Putusan putusan) {
        daftarPutusan.add(putusan);
        riwayatAudit.push("TAMBAH: Menambahkan putusan " + putusan.getCaseId() + " (Total data: " + daftarPutusan.size() + ")");
    }

    // 4. HAPUS DATA BERDASARKAN CASE ID
    public Putusan hapusByCaseId(String caseId) {
        for (int i = 0; i < daftarPutusan.size(); i++) {
            Putusan p = daftarPutusan.get(i);
            if (p.getCaseId().equalsIgnoreCase(caseId.trim())) {
                Putusan terhapus = daftarPutusan.remove(i);
                riwayatAudit.push("HAPUS: Menghapus putusan " + terhapus.getCaseId() + " dari indeks " + i);
                return terhapus;
            }
        }
        riwayatAudit.push("HAPUS GAGAL: Case ID '" + caseId + "' tidak ditemukan");
        return null;
    }

    // 5. FILTER BERDASARKAN JENIS NARKOTIKA (Mengembalikan SinglyLinkedList baru)
    public SinglyLinkedList<Putusan> filterByJenisNarkotika(String jenis) {
        SinglyLinkedList<Putusan> hasil = new SinglyLinkedList<>();
        for (int i = 0; i < daftarPutusan.size(); i++) {
            Putusan p = daftarPutusan.get(i);
            if (p.getJenisNarkotika()
                    .toLowerCase()
                    .contains(jenis.trim()
                            .toLowerCase())) {
                hasil.add(p);
            }
        }
        riwayatAudit.push("FILTER: Jenis '" + jenis + "' menghasilkan " + hasil.size() + " record");
        return hasil;
    }

    // 6. SORTING VONIS PENJARA (Selection Sort ke dalam SinglyLinkedList baru tanpa mengubah urutan asli)
    public SinglyLinkedList<Putusan> urutkanByVonis(boolean ascending) {
        int n = daftarPutusan.size();
        Putusan[] arr = new Putusan[n];
        for (int i = 0; i < n; i++) {
            arr[i] = daftarPutusan.get(i);
        }

        for (int i = 0; i < n - 1; i++) {
            int targetIdx = i;
            for (int j = i + 1; j < n; j++) {
                boolean kondisi = ascending
                        ? arr[j].getVonisBulan() < arr[targetIdx].getVonisBulan()
                        : arr[j].getVonisBulan() > arr[targetIdx].getVonisBulan();
                if (kondisi) {
                    targetIdx = j;
                }
            }
            Putusan temp = arr[targetIdx];
            arr[targetIdx] = arr[i];
            arr[i] = temp;
        }

        SinglyLinkedList<Putusan> hasilUrut = new SinglyLinkedList<>();
        for (int i = 0; i < n; i++) {
            hasilUrut.add(arr[i]);
        }
        riwayatAudit.push("SORT: Mengurutkan " + n + " putusan berdasarkan vonis (" + (ascending ? "Terendah->Tertinggi" : "Tertinggi->Terendah") + ")");
        return hasilUrut;
    }

    // 7. AGREGASI STATISTIK
    public void tampilkanStatistik() {
        int n = daftarPutusan.size();
        if (n == 0) {
            System.out.println("Belum ada data putusan.");
            return;
        }

        double totalBerat = 0;
        long totalVonis = 0;
        long totalDenda = 0;
        Putusan vonisTertinggi = daftarPutusan.get(0);
        Putusan vonisTerendah = daftarPutusan.get(0);

        for (int i = 0; i < n; i++) {
            Putusan p = daftarPutusan.get(i);
            totalBerat += p.getBeratBarangBuktiGram();
            totalVonis += p.getVonisBulan();
            totalDenda += p.getDendaRupiah();

            if (p.getVonisBulan() > vonisTertinggi.getVonisBulan()) vonisTertinggi = p;
            if (p.getVonisBulan() < vonisTerendah.getVonisBulan()) vonisTerendah = p;
        }

        System.out.println("\n=== RINGKASAN AGREGASI DATA PUTUSAN (" + n + " PERKARA) ===");
        System.out.printf("Total Berat Barang Bukti : %.2f gram%n", totalBerat);
        System.out.printf("Rata-rata Berat BB       : %.2f gram%n", (totalBerat / n));
        System.out.printf("Rata-rata Vonis Penjara  : %.1f bulan%n", ((double) totalVonis / n));
        System.out.printf("Total Pidana Denda       : Rp%,d%n", totalDenda);
        System.out.println("Vonis Tertinggi          : " + vonisTertinggi.getVonisBulan() + " bulan (" + vonisTertinggi.getCaseId() + " - " + vonisTertinggi.getJenisNarkotika() + ")");
        System.out.println("Vonis Terendah           : " + vonisTerendah.getVonisBulan() + " bulan (" + vonisTerendah.getCaseId() + " - " + vonisTerendah.getJenisNarkotika() + ")");
        riwayatAudit.push("AGREGASI: Menghitung statistik dari " + n + " record");
    }

    // 8. EKSPOR LIST KE FILE CSV
    public boolean eksporKeCsv(SinglyLinkedList<Putusan> listSumber, String outputPath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {
            bw.write("case_id,nomor_perkara,pengadilan,tanggal_putusan,terdakwa_kode,jenis_narkotika,berat_barang_bukti_gram,pasal_dakwaan,pasal_terbukti,tuntutan_bulan,vonis_bulan,denda_rupiah,barang_bukti,keadaan_memberatkan,keadaan_meringankan,source_pages");
            bw.newLine();

            for (int i = 0; i < listSumber.size(); i++) {
                Putusan p = listSumber.get(i);
                String baris = String.join(",",
                        escapeCsv(p.getCaseId()),
                        escapeCsv(p.getNomorPerkara()),
                        escapeCsv(p.getPengadilan()),
                        p.getTanggalPutusan().toString(),
                        escapeCsv(p.getTerdakwaKode()),
                        escapeCsv(p.getJenisNarkotika()),
                        String.valueOf(p.getBeratBarangBuktiGram()),
                        escapeCsv(p.getPasalDakwaan()),
                        escapeCsv(p.getPasalTerbukti()),
                        String.valueOf(p.getTuntutanBulan()),
                        String.valueOf(p.getVonisBulan()),
                        String.valueOf(p.getDendaRupiah()),
                        escapeCsv(p.getBarangBukti()),
                        escapeCsv(p.getKeadaanMemberatkan()),
                        escapeCsv(p.getKeadaanMeringankan()),
                        escapeCsv(p.getSourcePages())
                );
                bw.write(baris);
                bw.newLine();
            }
            riwayatAudit.push("EKSPOR CSV: Menyimpan " + listSumber.size() + " record ke " + outputPath);
            return true;
        } catch (IOException e) {
            System.out.println("Gagal mengekspor CSV: " + e.getMessage());
            return false;
        }
    }

    // --- HELPER PARSING CSV TANPA JAVA.UTIL COLLECTIONS ---
    private String[] parseCsvLine(String line, char delimiter) {
        String[] result = new String[20];
        int colIdx = 0;
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == delimiter && !inQuotes) {
                if (colIdx < result.length) {
                    result[colIdx++] = current.toString();
                }
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        if (colIdx < result.length) {
            result[colIdx++] = current.toString();
        }

        String[] trimmed = new String[colIdx];
        for (int i = 0; i < colIdx; i++) {
            trimmed[i] = result[i];
        }
        return trimmed;
    }

    private String escapeCsv(String text) {
        if (text == null) return "";
        if (text.contains(",") || text.contains("\"") || text.contains(";")) {
            return "\"" + text.replace("\"", "\"\"") + "\"";
        }
        return text;
    }

    private LocalDate parseTanggal(String text) {
        try {
            return LocalDate.parse(text);
        } catch (Exception e) {
            return LocalDate.of(2025, 1, 1);
        }
    }

    private double parseDoubleSafe(String text) {
        try {
            return Double.parseDouble(text.replace(",", "."));
        } catch (Exception e) {
            return 0.0;
        }
    }

    private int parseIntSafe(String text) {
        try {
            return Integer.parseInt(text.replaceAll("[^0-9-]", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    private long parseLongSafe(String text) {
        try {
            return Long.parseLong(text.replaceAll("[^0-9-]", ""));
        } catch (Exception e) {
            return 0L;
        }
    }
}