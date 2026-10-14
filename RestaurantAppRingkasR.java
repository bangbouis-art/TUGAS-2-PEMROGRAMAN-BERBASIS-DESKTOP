import java.util.*;
import java.text.NumberFormat;

class Menu {
    String nama, kategori;
    double harga;
    Menu(String n, double h, String k) { nama = n; harga = h; kategori = k; }
}

public class RestaurantAppRingkasR {
    static ArrayList<Menu> menus = new ArrayList<>(Arrays.asList(
        new Menu("Nasi Goreng", 25000, "Makanan"),
        new Menu("Mie Ayam", 20000, "Makanan"),
        new Menu("Es Teh", 5000, "Minuman"),
        new Menu("Es Jeruk", 7000, "Minuman")
    ));
    static Scanner sc = new Scanner(System.in);
    static NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== APLIKASI RESTORAN ===");
            System.out.println("1. Menu Pelanggan\n2. Menu Pengelolaan\n3. Keluar");
            System.out.print("Pilih (1-3): ");
            String p = sc.nextLine();
            if (p.equals("1")) pelanggan();
            else if (p.equals("2")) pengelolaan();
            else if (p.equals("3")) break;
            else System.out.println("[Error] Pilihan tidak valid!");
        }
    }

    static void printMenu() {
        System.out.println("\n--- DAFTAR MENU ---");
        for (int i = 0; i < menus.size(); i++) {
            Menu m = menus.get(i);
            System.out.printf("%d. %s (%s) - %s\n", (i + 1), m.nama, m.kategori, fmt.format(m.harga));
        }
    }

    static void pelanggan() {
        List<int[]> cart = new ArrayList<>(); // Menyimpan [indexMenu, jumlah]
        while (true) {
            printMenu();
            System.out.print("Pilih nomor menu (atau ketik 'selesai'): ");
            String in = sc.nextLine().trim();
            if (in.equalsIgnoreCase("selesai")) {
                if (cart.isEmpty()) return;
                break;
            }
            try {
                int idx = Integer.parseInt(in) - 1;
                if (idx >= 0 && idx < menus.size()) {
                    System.out.print("Masukkan jumlah: ");
                    int qty = Integer.parseInt(sc.nextLine().trim());
                    if (qty > 0) cart.add(new int[]{idx, qty});
                    else System.out.println("[Error] Jumlah minimal 1.");
                } else {
                    System.out.println("[Error] Nomor menu tidak ada!");
                }
            } catch (Exception e) {
                System.out.println("[Error] Input tidak valid!");
            }
        }

        // Perhitungan & Cetak Struk
        double subtotal = 0;
        System.out.println("\n========================================");
        System.out.println("             STRUK PEMBAYARAN           ");
        System.out.println("========================================");
        for (int[] item : cart) {
            Menu m = menus.get(item[0]);
            double totalItem = m.harga * item[1];
            subtotal += totalItem;
            System.out.printf("%-15s x%d = %s\n", m.nama, item[1], fmt.format(totalItem));
        }

        double diskon = subtotal > 100000 ? subtotal * 0.1 : 0;
        String bogo = "-";
        if (subtotal > 50000) {
            Menu murah = null;
            for (int[] item : cart) {
                Menu m = menus.get(item[0]);
                if (m.kategori.equalsIgnoreCase("Minuman") && (murah == null || m.harga < murah.harga)) {
                    murah = m;
                }
            }
            if (murah != null) bogo = "BOGO Minuman (" + murah.nama + ")";
        }

        double pajak = (subtotal - diskon) * 0.1;
        double total = subtotal - diskon + pajak + 20000;

        System.out.println("----------------------------------------");
        System.out.printf("Subtotal        : %s\n", fmt.format(subtotal));
        if (diskon > 0) System.out.printf("Diskon 10%%      : -%s\n", fmt.format(diskon));
        if (!bogo.equals("-")) System.out.printf("Promo Spesial   : %s\n", bogo);
        System.out.printf("Pajak (10%%)     : %s\n", fmt.format(pajak));
        System.out.printf("Biaya Layanan   : %s\n", fmt.format(20000));
        System.out.println("----------------------------------------");
        System.out.printf("TOTAL KESELURUHAN: %s\n", fmt.format(total));
        System.out.println("========================================\n");
    }

    static void pengelolaan() {
        while (true) {
            System.out.println("\n--- MENU PENGELOLAAN ---");
            System.out.println("1. Tambah Menu\n2. Ubah Harga\n3. Hapus Menu\n4. Kembali");
            System.out.print("Pilih (1-4): ");
            String p = sc.nextLine();
            if (p.equals("4")) break;

            if (p.equals("1")) {
                System.out.print("Nama menu: "); String nama = sc.nextLine();
                System.out.print("Harga: "); double harga = Double.parseDouble(sc.nextLine());
                System.out.print("Kategori (Makanan/Minuman): "); String kat = sc.nextLine();
                System.out.print("Yakin ingin menambah? (Ya/Tidak): ");
                if (sc.nextLine().equalsIgnoreCase("Ya")) menus.add(new Menu(nama, harga, kat));
            } else if (p.equals("2") || p.equals("3")) {
                printMenu();
                System.out.print("Masukkan nomor menu: ");
                try {
                    int idx = Integer.parseInt(sc.nextLine()) - 1;
                    if (idx >= 0 && idx < menus.size()) {
                        if (p.equals("2")) {
                            System.out.print("Harga baru: "); double hb = Double.parseDouble(sc.nextLine());
                            System.out.print("Yakin ubah harga? (Ya/Tidak): ");
                            if (sc.nextLine().equalsIgnoreCase("Ya")) menus.get(idx).harga = hb;
                        } else {
                            System.out.print("Yakin hapus menu? (Ya/Tidak): ");
                            if (sc.nextLine().equalsIgnoreCase("Ya")) menus.remove(idx);
                        }
                    } else {
                        System.out.println("[Error] Nomor menu tidak valid.");
                    }
                } catch (Exception e) {
                    System.out.println("[Error] Input tidak valid.");
                }
            } else {
                System.out.println("[Error] Pilihan tidak valid.");
            }
        }
    }
}