import java.time.LocalDate;

public class SinglyLinkedListTest {

    public static void main(String[] args) {
        System.out.println("=== MULAI PENGUJIAN STRUKTUR DATA ===");

        testMuat50Record();
        testCariAdaDanTidakAda();
        testHapusAwalTengahAkhir();
        testTambahKembaliDanInvariant();
        testAuditStackLIFO();

        System.out.println("=== SEMUA PENGUJIAN BERHASIL (PASSED) ===");
    }

    private static void testMuat50Record() {
        SinglyLinkedList<Putusan> list = new SinglyLinkedList<>();
        for (int i = 1; i <= 50; i++) {
            list.add(buatPutusanDummy(i));
        }

        if (list.size() != 50) throw new AssertionError("Gagal: size seharusnya 50");
        if (!list.validateInvariant()) throw new AssertionError("Gagal: invariant rusak setelah muat 50 data");
        System.out.println("Skenario 1: Muat 50 record & validateInvariant() sukses.");
    }

    private static void testCariAdaDanTidakAda() {
        SinglyLinkedList<Putusan> list = new SinglyLinkedList<>();
        Putusan p1 = buatPutusanDummy(1);
        Putusan p25 = buatPutusanDummy(25);
        Putusan p50 = buatPutusanDummy(50);

        for (int i = 1; i <= 50; i++) {
            if (i == 1) list.add(p1);
            else if (i == 25) list.add(p25);
            else if (i == 50) list.add(p50);
            else list.add(buatPutusanDummy(i));
        }

        Putusan tidakAda = buatPutusanDummy(999);

        if (list.search(p1) != 0) throw new AssertionError("Gagal: elemen awal harus di indeks 0");
        if (list.search(p25) != 24) throw new AssertionError("Gagal: elemen tengah harus di indeks 24");
        if (list.search(p50) != 49) throw new AssertionError("Gagal: elemen akhir harus di indeks 49");
        if (list.search(tidakAda) != -1) throw new AssertionError("Gagal: elemen tidak ada harus -1");
        System.out.println("Skenario 2: Pencarian posisi batas, tengah, dan data tidak ada sukses.");
    }

    private static void testHapusAwalTengahAkhir() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");

        String hapusAwal = list.remove(0);
        if (!hapusAwal.equals("A") || !list.get(0).equals("B") || !list.validateInvariant()) {
            throw new AssertionError("Gagal hapus awal");
        }

        String hapusTengah = list.remove(1);
        if (!hapusTengah.equals("C") || !list.get(1).equals("D") || !list.validateInvariant()) {
            throw new AssertionError("Gagal hapus tengah");
        }

        String hapusAkhir = list.remove(2);
        if (!hapusAkhir.equals("E") || list.size() != 2 || !list.validateInvariant()) {
            throw new AssertionError("Gagal hapus akhir");
        }

        System.out.println("Skenario 3: Hapus awal, tengah, akhir & pergeseran pointer valid.");
    }

    private static void testTambahKembaliDanInvariant() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.add("P001");
        list.add("P002");

        list.addFirst("P000");
        list.insert(2, "P001-SISIP");
        list.addLast("P003");

        if (list.size() != 5 || !list.get(0).equals("P000") || !list.get(2).equals("P001-SISIP") || !list.get(4).equals("P003") || !list.validateInvariant()) {
            throw new AssertionError("Gagal tambah kembali 3 record");
        }
        System.out.println("Skenario 4: Tambah kembali 3 record & urutan data terjaga.");
    }

    private static void testAuditStackLIFO() {
        AuditStack<String> stack = new AuditStack<>();
        stack.push("1. Impor 50 data");
        stack.push("2. Cari P010");
        stack.push("3. Hapus P050");

        if (stack.size() != 3 || !stack.peek().equals("3. Hapus P050") || !stack.pop().equals("3. Hapus P050") || !stack.pop().equals("2. Cari P010") || !stack.validateInvariant()) {
            throw new AssertionError("Gagal uji AuditStack");
        }
        System.out.println("Struktur Sekunder AuditStack (LIFO) valid.");
    }

    private static Putusan buatPutusanDummy(int nomor) {
        String id = String.format("P%03d", nomor);
        return new Putusan(
                id, nomor + "/Pid.Sus/2025/PN Mlg", "PN Malang",
                LocalDate.of(2025, 5, 10), "TDW-" + nomor, "Sabu",
                1.5, "Pasal 112 ayat (1)", "Pasal 112 ayat (1)",
                60, 48, 800000000L, "1 klip sabu",
                "Tidak mendukung program pemerintah", "Berterus terang", "hal 1-15"
        );
    }
}