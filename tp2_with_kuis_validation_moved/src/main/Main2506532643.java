import java.util.Scanner;

public class Main2506532643
{
    private static final Scanner     input       = new Scanner(System.in);
    private static final GameManager gameManager = new GameManager();

    public static void main(String[] args) throws Exception
    {
        boolean fill  = false;
        boolean admin = false;
        String  user  = "mugichan";

        for (int i = 0; i < args.length; i++)
        {
            if (args[i].equals("--fill"))
                fill = true;
            else if (args[i].equals("--admin"))
                admin = true;
            else if (args[i].equals("--user") && i + 1 < args.length)
                user = args[++i];
        }

        if (fill)
        {
            int id = 1;

            gameManager.addQuest(new Quest(id, "selamatkan orang yang dipenjara", "desc", "mudah", 100 * id, 1000 * id, id++, "combat"));
            gameManager.addQuest(new Quest(id, "quest_" + id, "desc", "mudah", 100 * id, 1000 * id, id++, "combat"));
            gameManager.addQuest(new Quest(id, "quest_" + id, "desc", "menengah", 100 * id, 1000 * id, id++, "combat"));
            gameManager.addQuest(new Quest(id, "quest_" + id, "desc", "sulit", 100 * id, 1000 * id, id++, "combat"));

            gameManager.addWanderer(new Wanderer(1, "Tsumugi Kotobuki", "mugichan", "cute", "combat"));
            gameManager.addWanderer(new Wanderer(2, "Yui Hirasawa", "yuichan", "guitar123", "combat"));
            gameManager.addWanderer(new Wanderer(3, "Mio Akiyama", "miochan", "cute", "gathering"));
            gameManager.addWanderer(new Wanderer(4, "Ritsu Tainaka", "ricchan", "forehead", "gathering"));
            gameManager.addWanderer(new Wanderer(5, "Azusa Nakano", "azunyan", "neko", "diplomacy"));;d();
        }

        if (admin)
            adminMenu();
        else if (fill)
        {
            if (gameManager.getPrisonerByUsername(user) != null)
                prisonerMenu(user);
            else
            {
                if (gameManager.getWandererByUsername(user) == null)
                {
                    System.out.println("User tidak ditemukan");
                    return;
                }
                else
                    userMenu(user);
            }
        }


        /***/


        // "Himmel", "himmel", "pass123", "combat"
        // "Frieren", "frieren", "pass456", "gathering"
        /***/

        run();
    }

    public static void run()
    {
        while (true)
        {
            System.out.println("Hari ke-" + gameManager.getDaysCount());
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Input: ");

            String choice = input.nextLine().trim();

            if (choice.equals("1"))
                handleLogin();
            else if (choice.equals("2"))
                break;
            else
                System.out.println("Input tidak valid!");
        }
    }

    public static void handleLogin()
    {
        System.out.print("Username: ");
        String username = input.nextLine().trim();

        System.out.print("Password: ");
        String password = input.nextLine().trim();

        /*
         * [subtask 7]
         * sembunyikan password setelah di-input
         **/

        System.out.print("\033[1A");
        System.out.print("\033[2K");
        System.out.println("Password: "
                           + "*".repeat(password.length()));

        if (gameManager.isAdmin(username, password))
        {
            System.out.println("Login berhasil sebagai Admin.");
            adminMenu();
        }

        else if (gameManager.isUser(username, password))
        {
            if (gameManager.isPrisoner(username))
            {
                System.out.println("Login berhasil sebagai tahanan.");
                prisonerMenu(username);
            }

            else
            {
                System.out.println("Login berhasil sebagai User.");
                userMenu(username);
            }
        }

        else
            System.out.println("Username atau password salah.");
    }

    public static void adminMenu()
    {
        while (true)
        {
            System.out.println("\nMenu Atmint (Hari ke-" + gameManager.getDaysCount() + ")");
            System.out.println("1. Lihat daftar quest");
            System.out.println("2. Lihat daftar pengembara");
            System.out.println("3. Tambah quest");
            System.out.println("4. Tambah pengembara");
            System.out.println("5. Filter daftar quest");
            System.out.println("6. Filter daftar pengembara");
            System.out.println("7. Tampilkan daftar quest terurut");
            System.out.println("8. Tampilkan daftar pengembara terurut");
            System.out.println("9. Lanjut ke hari berikutnya");
            System.out.println("10. Tampilkan daftar pengembara yang kelelahan");
            System.out.println("0. Keluar");

            System.out.print("Input: ");

            switch (input.nextLine().trim())
            {
                case "1" -> helperShowQuests(gameManager.getQuests(), gameManager.getQuestCount());
                case "2" -> helperShowWanderers(gameManager.getWanderers(), gameManager.getWandererCount());
                case "3" -> helperInputQuest();
                case "4" -> helperInputWanderer();
                case "5" -> helperInputQuestFilter();
                case "6" -> helperInputWandererFilter();
                case "7" -> helperInputQuestSorting();
                case "8" -> helperInputWandererSorting();
                case "9" ->
                    {
                        Quest[] completedQuestList = gameManager.nextDay();

                        System.out.println("Berganti ke hari ke-" + gameManager.getDaysCount());

                        for (int i = 0; i < completedQuestList.length; i++)
                        {
                            if (completedQuestList[i] == null)
                                break;

                            System.out.println(completedQuestList[i]);
                        }
                    }
                case "10" ->
                    {
                        // kuis soal 3
                        Wanderer[] filtered = gameManager.getTiredWanderers();
                        helperShowWanderers(filtered, filtered.length);
                    }
                case "0" ->
                    {
                        return;
                    }
                default -> System.out.println("Input tidak valid!");
            }
        }
    }

    private static void userMenu(String username)
    {
        while (true)
        {
            Wanderer currentWanderer = gameManager.getWandererByUsername(username);

            System.out.println("Menu pengembara: " + currentWanderer.getName() + " (Hari ke-" + gameManager.getDaysCount() + ")");
            System.out.println("1. Lihat data diri");
            System.out.println("2. Lihat daftar quest");
            System.out.println("3. Filter daftar quest");
            System.out.println("4. Tampilkan daftar quest terurut");
            System.out.println("5. Ambil quest");
            System.out.println("0. Keluar");

            System.out.print("Input: ");

            switch (input.nextLine().trim())
            {
                case "1" -> System.out.println(currentWanderer.getDisplayString());
                case "2" -> helperShowQuests(gameManager.getQuests(), gameManager.getQuestCount());
                case "3" -> helperInputQuestFilter();
                case "4" -> helperInputQuestSorting();
                case "5" -> helperInputTakeQuest(currentWanderer);
                case "0" ->
                    {
                        return;
                    }
                default -> System.out.println("Input tidak valid!");
            }
        }
    }

    private static void prisonerMenu(String username)
    {
        while (true)
        {
            Wanderer currentWanderer = gameManager.getWandererByUsername(username);
            String[] prisoner        = gameManager.getPrisonerByUsername(username);

            System.out.println("Menu tahanan penjara: " + currentWanderer.getName() + " (Hari ke-" + gameManager.getDaysCount() + ")");
            System.out.println("1. Lihat data tahanan");
            System.out.println("0. Keluar");

            System.out.print("Input: ");

            switch (input.nextLine().trim())
            {
                case "1" ->
                    {
                        System.out.println("Nama:                " + currentWanderer.getName());
                        System.out.println("Alasan dipenjara:    " + prisoner[1]);
                        System.out.println("Durasi dipenjara:    " + prisoner[2] + " tahun");
                        System.out.println();
                        // System.out.println("Sisa hari dipenjara: " + (Integer.parseInt(prisoner[3]) + Integer.parseInt(prisoner[2]) - gameManager.getDaysCount()) + " hari");
                    }

                case "0" ->
                    {
                        return;
                    }
                default -> System.out.println("Input tidak valid!");
            }
        }
    }

    private static void helperShowQuests(Quest[] quests, int questCount)
    {
        if (questCount == 0)
        {
            System.out.println("Tidak ditemukan adanya quest.");
            return;
        }

        for (int i = 0; i < questCount; i++)
        {
            if (quests[i] == null)
                break;

            System.out.println((i + 1) + ".\n" + quests[i] + "\n\n");
        }
    }

    private static void helperShowWanderers(Wanderer[] wanderers, int wandererCount)
    {
        if (wandererCount == 0)
        {
            System.out.println("Tidak ditemukan adanya wanderer.");
            return;
        }

        for (int i = 0; i < wandererCount; i++)
        {
            if (wanderers[i] == null)
                break;

            System.out.println((i + 1) + ".\n" + wanderers[i] + "\n\n");
        }
    }

    /* quest filtering */
    // i dont know if filtering and sorting is considered a bussiness logic but its mainly just for displays so...

    public static Quest[] HelperFilterQuestStatus(String status)
    {
        Quest[] quests     = gameManager.getQuests();
        int     questCount = gameManager.getQuestCount();

        if (questCount == 0)
            return new Quest[0];

        Quest[] filtered      = new Quest[1000];
        int     filteredCount = 0;

        for (int i = 0; i < questCount; i++)
        {
            if (quests[i].getStatus().equals(status))
                filtered[filteredCount++] = quests[i];
        }

        return filteredCount == 0 ? new Quest[0] : filtered;
    }

    public static Quest[] HelperFilterQuestDifficulty(String diff)
    {
        Quest[] quests     = gameManager.getQuests();
        int     questCount = gameManager.getQuestCount();

        if (questCount == 0)
            return new Quest[0];

        Quest[] filtered      = new Quest[1000];
        int     filteredCount = 0;

        for (int i = 0; i < questCount; i++)
        {
            if (quests[i].getDifficulty().equals(diff))
                filtered[filteredCount++] = quests[i];
        }

        return filteredCount == 0 ? new Quest[0] : filtered;
    }

    /* wanderer filtering */

    public static Wanderer[] HelperFilterWandererStatus(String status)
    {
        Wanderer[] wanderers     = gameManager.getWanderers();
        int        wandererCount = gameManager.getWandererCount();

        if (wandererCount == 0)
            return new Wanderer[0];

        Wanderer[] filtered      = new Wanderer[1000];
        int        filteredCount = 0;

        for (int i = 0; i < wandererCount; i++)
        {
            if (wanderers[i].getStatus().equals(status))
                filtered[filteredCount++] = wanderers[i];
        }

        return filteredCount == 0 ? new Wanderer[0] : filtered;
    }

    public static Wanderer[] HelperFilterWandererLevel(int upper, int lower)
    {
        Wanderer[] wanderers     = gameManager.getWanderers();
        int        wandererCount = gameManager.getWandererCount();

        if (wandererCount == 0)
            return new Wanderer[0];

        Wanderer[] filtered      = new Wanderer[1000];
        int        filteredCount = 0;

        for (int i = 0; i < wandererCount; i++)
        {
            if (wanderers[i].getLevel() >= lower && wanderers[i].getLevel() <= upper)
                filtered[filteredCount++] = wanderers[i];
        }

        return filteredCount == 0 ? new Wanderer[0] : filtered;
    }

    /* quest sorting */
    // clang-format off
    public static Quest[] HelperSortQuestDifficulty(boolean asc)
    {
        Quest[] quests     = gameManager.getQuests();
        int     questCount = gameManager.getQuestCount();

        if (questCount == 0) return new Quest[0];

        Quest[] filteredEasy      = new Quest[1000];
        int     filteredEasyCount = 0;

        Quest[] filteredMid      = new Quest[1000];
        int     filteredMidCount = 0;

        Quest[] filteredHard      = new Quest[1000];
        int     filteredHardCount = 0;

        for (int i = 0; i < questCount; i++)
        {
            switch (quests[i].getDifficulty())
            {
                case "mudah"    -> filteredEasy[filteredEasyCount++] = quests[i];
                case "menengah" -> filteredMid[filteredMidCount++]   = quests[i];
                case "sulit"    -> filteredHard[filteredHardCount++] = quests[i];
            }
        }

        Quest[] merged      = new Quest[filteredEasyCount + filteredMidCount + filteredHardCount];
        int     mergedCount = 0;

        if (asc)
        {
            for (int i = 0; i < filteredEasyCount; i++) merged[mergedCount++] = filteredEasy[i];
            for (int i = 0; i < filteredMidCount;  i++) merged[mergedCount++] = filteredMid[i];
            for (int i = 0; i < filteredHardCount; i++) merged[mergedCount++] = filteredHard[i];
        }

        else
        {
            for (int i = 0; i < filteredHardCount; i++) merged[mergedCount++] = filteredHard[i];
            for (int i = 0; i < filteredMidCount;  i++) merged[mergedCount++] = filteredMid[i];
            for (int i = 0; i < filteredEasyCount; i++) merged[mergedCount++] = filteredEasy[i];
        }

        return mergedCount == 0 ? new Quest[0] : merged;
    }

    public static Quest[] HelperSortQuestReward(boolean asc)
    {
        Quest[] quests     = gameManager.getQuests();
        int     questCount = gameManager.getQuestCount();

        if (questCount == 0) return new Quest[0];

        Quest[] sorted = new Quest[questCount];
        
        for (int i = 0; i < questCount; i++)
        {
            sorted[i] = quests[i];
        }

        // its sooooo uglyyyyyyyyyyyyyyyyyyyyyyyy broooooooooooooooooooo
        for (int gap = questCount / 2; gap > 0; gap /= 2)
        {
            for (int i = gap; i < questCount; i++)
            {
                Quest key = sorted[i];
                int   j   = i;

                while (j >= gap 
                    && (asc ? sorted[j - gap].getReward() > key.getReward()
                            : sorted[j - gap].getReward() < key.getReward()
                ))
                {
                    sorted[j] = sorted[j - gap];
                    j -= gap;
                }
                sorted[j] = key;
            }
        }

        return sorted;
    }


    public static Wanderer[] HelperSortWandererName(boolean asc)
    {
        Wanderer[] Wanderers     = gameManager.getWanderers();
        int        wandererCount = gameManager.getWandererCount();

        if (wandererCount == 0) return new Wanderer[0];

        Wanderer[] sorted = new Wanderer[wandererCount];
        
        for (int i = 0; i < wandererCount; i++)
        {
            sorted[i] = Wanderers[i];
        }

        for (int gap = wandererCount / 2; gap > 0; gap /= 2)
        {
            for (int i = gap; i < wandererCount; i++)
            {
                Wanderer key = sorted[i];
                int   j      = i;

                while (j >= gap 
                    && (asc ? sorted[j - gap].getName().compareToIgnoreCase(key.getName()) > 0
                            : sorted[j - gap].getName().compareToIgnoreCase(key.getName()) < 0
                ))
                {
                    sorted[j] = sorted[j - gap];
                    j -= gap;
                }
                sorted[j] = key;
            }
        }

        return sorted;
    }

    public static Wanderer[] HelperSortWandererLevel(boolean asc)
    {
        Wanderer[] Wanderers     = gameManager.getWanderers();
        int        wandererCount = gameManager.getWandererCount();

        if (wandererCount == 0) return new Wanderer[0];

        Wanderer[] sorted = new Wanderer[wandererCount];
        
        for (int i = 0; i < wandererCount; i++)
        {
            sorted[i] = Wanderers[i];
        }

        for (int gap = wandererCount / 2; gap > 0; gap /= 2)
        {
            for (int i = gap; i < wandererCount; i++)
            {
                Wanderer key = sorted[i];
                int   j      = i;

                while (j >= gap 
                    && (asc ? sorted[j - gap].getLevel() > key.getLevel()
                            : sorted[j - gap].getLevel() < key.getLevel()
                ))
                {
                    sorted[j] = sorted[j - gap];
                    j -= gap;
                }
                sorted[j] = key;
            }
        }

        return sorted;
    }
    // clang-format on

    private static void helperInputQuest()
    {
        while (true)
        {
            System.out.print("Masukan nama quest: ");
            String name = input.nextLine().trim();

            if (name.toLowerCase().equals("x"))
                break;

            System.out.print("Deskripsi quest: ");
            String desc = input.nextLine().trim();

            if (desc.toLowerCase().equals("x"))
                break;

            System.out.print("Reward quest: ");
            String reward = input.nextLine().trim();

            if (reward.toLowerCase().equals("x"))
                break;

            System.out.print("Bonus koin: ");
            String bonus = input.nextLine().trim();

            if (bonus.toLowerCase().equals("x"))
                break;

            System.out.print("Difficulty: ");
            String difficulty = input.nextLine().trim();

            if (difficulty.toLowerCase().equals("x"))
                break;

            System.out.print("Durasi quest: ");
            String duration = input.nextLine().trim();

            if (duration.toLowerCase().equals("x"))
                break;

            System.out.print("Tipe quest (combat | gathering | diplomacy): ");
            String type = input.nextLine().trim();

            if (type.toLowerCase().equals("x"))
                break;

            String[] invalids = validateQuestInput(name, desc, reward, bonus, difficulty, duration, type);

            if (invalids.length > 0)
            {
                for (int i = 0; i < invalids.length; i++)
                    System.out.println("[!] " + invalids[i]);

                System.out.println("Mengulangi seeding...");
                    
                continue;
            }

            else
            {
                gameManager.addQuest(
                    new Quest(
                        gameManager.getQuestCount() + 1,
                        name,
                        desc,
                        difficulty,
                        Integer.parseInt(reward),
                        Integer.parseInt(bonus),
                        Integer.parseInt(duration),
                        type
                ));

                System.out.println("Berhasil menambahkan quest!");

                break;
            }
        }
    }

    private static void helperInputWanderer()
    {
        while (true)
        {
            System.out.println("Masukan \"x\" untuk membatalkan aksi dan kembali ke menu.");

            System.out.print("Masukan nama pengembara: ");
            String name = input.nextLine().trim();

            if (name.toLowerCase().equals("x"))
                break;

            System.out.print("Masukan username pengembara: ");
            String username = input.nextLine().trim();

            if (username.toLowerCase().equals("x"))
                break;

            System.out.print("Masukan password: ");
            String password = input.nextLine().trim();

            if (password.toLowerCase().equals("x"))
                break;

            System.out.print("\033[1A");
            System.out.print("\033[2K");
            System.out.println("Masukan password: "
                               + "*".repeat(password.length()));

            System.out.print("Masukan specialization: ");
            String specialization = input.nextLine().trim();

            if (specialization.toLowerCase().equals("x"))
                break;

            String[] invalids = validateWandererInput(gameManager, name, username, password, specialization);

            if (invalids.length > 0)
            {
                for (int i = 0; i < invalids.length; i++)
                    System.out.println("[!] " + invalids[i]);

                System.out.println("Mengulangi seeding...");
                    
                continue;
            }

            else
            {
                gameManager.addWanderer(
                    new Wanderer(
                        gameManager.getWandererCount() + 1,
                        name,
                        username,
                        password,
                        specialization
                    ));

                System.out.println("Berhasil menambahkan pengembara");

                break;
            }
        }
    }

    private static void helperInputTakeQuest(Wanderer wanderer)
    {
        while (true)
        {
            System.out.println("Masukan \"x\" untuk membatalkan aksi dan kembali ke menu.\n");

            System.out.print("Masukan id quest: ");
            String id = input.nextLine().trim();

            if (id.toLowerCase().equals("x"))
                break;

            if (gameManager.getQuestById(id) == null)
            {
                System.out.println("Quest tidak ditemukan!");
                continue;
            }

            if (gameManager.takeQuest(gameManager.getQuestById(id), wanderer))
            {
                System.out.println("Berhasil mengambil quest!");
                break;
            }
            else
                System.out.println("Gagal mengambil quest!\nMengulangi pengambilan...\n");
        }
    }

    private static void helperInputWandererFilter()
    {
    funcMainLoop:
        while (true)
        {
            System.out.println("Masukan \"x\" untuk membatalkan aksi dan kembali ke menu.\n");
            System.out.println("1. Filter berdasarkan status");
            System.out.println("2. Filter berdasarkan rentang level");

            System.out.print("Masukan tipe filtering: ");
            String option = input.nextLine().trim();

            if (option.toLowerCase().equals("x"))
                break;

            if (option.equals("1"))
            {
                while (true)
                {
                    System.out.print("Masukan status (kosong | dalam quest): ");
                    String status = input.nextLine().trim().toLowerCase();

                    if (status.toLowerCase().equals("x"))
                        break funcMainLoop;

                    if (!status.equals("kosong") && !status.equals("dalam quest"))
                        System.out.println("Status tidak valid!");

                    else
                    {
                        Wanderer[] filtered = HelperFilterWandererStatus(status);

                        helperShowWanderers(filtered, filtered.length);
                        break funcMainLoop;
                    }
                }
            }

            else if (option.equals("2"))
            {
                while (true)
                {
                    System.out.print("Masukan batasan atas: ");
                    String upper = input.nextLine().trim().toLowerCase();

                    if (upper.toLowerCase().equals("x"))
                        break funcMainLoop;

                    System.out.print("Masukan batasan bawah: ");
                    String lower = input.nextLine().trim().toLowerCase();

                    if (lower.toLowerCase().equals("x"))
                        break funcMainLoop;

                    if (!upper.matches("\\d+") || !lower.matches("\\d+"))
                        System.out.println("[!] Input harus berupa angka.");

                    else
                    {
                        int upperInt = Integer.parseInt(upper);
                        int lowerInt = Integer.parseInt(lower);

                        if (lowerInt > upperInt)
                            System.out.println("[!] Batasan bawah tidak boleh lebih besar daripada batasan atas.");

                        else
                        {
                            Wanderer[] filtered = HelperFilterWandererLevel(upperInt, lowerInt);

                            helperShowWanderers(filtered, filtered.length);
                            break funcMainLoop;
                        }
                    }
                }
            }

            else
                System.out.println("Opsi tidak valid!");
        }
    }

    private static void helperInputQuestFilter()
    {
    funcMainLoop:
        while (true)
        {
            System.out.println("Masukan \"x\" untuk membatalkan aksi dan kembali ke menu.\n");

            System.out.println("1. Filter berdasarkan status");
            System.out.println("2. Filter berdasarkan tingkat kesulitan");

            System.out.print("Masukan tipe filtering: ");
            String option = input.nextLine().trim();

            if (option.toLowerCase().equals("x"))
                break;

            if (option.equals("1"))
            {
                while (true)
                {
                    System.out.print("Masukan status (tersedia | diambil | selesai): ");
                    String status = input.nextLine().trim().toLowerCase();

                    if (status.toLowerCase().equals("x"))
                        break funcMainLoop;

                    if (!status.equals("tersedia") && !status.equals("diambil") && !status.equals("selesai"))
                        System.out.println("Status tidak valid!");

                    else
                    {
                        Quest[] filtered = HelperFilterQuestStatus(status);

                        helperShowQuests(filtered, filtered.length);
                        break funcMainLoop;
                    }
                }
            }

            else if (option.equals("2"))
            {
                while (true)
                {
                    System.out.print("Masukan tingkat kesulitan (mudah | menengah | sulit): ");
                    String diff = input.nextLine().trim().toLowerCase();

                    if (diff.toLowerCase().equals("x"))
                        break funcMainLoop;

                    if (!diff.equals("mudah") && !diff.equals("menengah") && !diff.equals("sulit"))
                        System.out.println("Tingkat kesulitan tidak valid!");

                    else
                    {
                        Quest[] filtered = HelperFilterQuestDifficulty(diff);

                        helperShowQuests(filtered, filtered.length);
                        break funcMainLoop;
                    }
                }
            }

            else
                System.out.println("Opsi tidak valid!");
        }
    }

    private static void helperInputQuestSorting()
    {
    funcMainLoop:
        while (true)
        {
            System.out.println("Masukan \"x\" untuk membatalkan aksi dan kembali ke menu.\n");

            System.out.println("1. Sorting berdasarkan tingkat kesulitan");
            System.out.println("2. Sorting berdasarkan reward");

            System.out.print("Masukan tipe sorting: ");
            String option = input.nextLine().trim();

            if (option.toLowerCase().equals("x"))
                break funcMainLoop;

            if (option.equals("1"))
            {
                while (true)
                {
                    System.out.print("Urutan (asc | desc): ");
                    String direction = input.nextLine().trim().toLowerCase();

                    if (direction.toLowerCase().equals("x"))
                        break funcMainLoop;

                    if (!direction.equals("asc") && !direction.equals("desc"))
                        System.out.println("[!] urutan tidak valid");

                    else
                    {
                        Quest[] sorted = HelperSortQuestDifficulty(direction.equals("asc"));

                        helperShowQuests(sorted, sorted.length);
                        break funcMainLoop;
                    }
                }
            }

            else if (option.equals("2"))
            {
                while (true)
                {
                    System.out.print("Urutan (asc | desc): ");
                    String direction = input.nextLine().trim().toLowerCase();

                    if (direction.toLowerCase().equals("x"))
                        break funcMainLoop;

                    if (!direction.equals("asc") && !direction.equals("desc"))
                        System.out.println("[!] urutan tidak valid");

                    else
                    {
                        Quest[] sorted = HelperSortQuestReward(direction.equals("asc"));

                        helperShowQuests(sorted, sorted.length);
                        break funcMainLoop;
                    }
                }
            }

            else
                System.out.println("Opsi tidak valid!");
        }
    }

    private static void helperInputWandererSorting()
    {
    funcMainLoop:
        while (true)
        {
            System.out.println("Masukan \"x\" untuk membatalkan aksi dan kembali ke menu.\n");

            System.out.println("1. Sorting berdasarkan nama");
            System.out.println("2. Sorting berdasarkan level");

            System.out.print("Masukan tipe sorting: ");
            String option = input.nextLine().trim();

            if (option.toLowerCase().equals("x"))
                break funcMainLoop;

            if (option.equals("1"))
            {
                while (true)
                {
                    System.out.print("Urutan (asc | desc): ");
                    String direction = input.nextLine().trim().toLowerCase();

                    if (direction.toLowerCase().equals("x"))
                        break funcMainLoop;

                    if (!direction.equals("asc") && !direction.equals("desc"))
                        System.out.println("[!] urutan tidak valid");

                    else
                    {
                        Wanderer[] sorted = HelperSortWandererName(direction.equals("asc"));

                        helperShowWanderers(sorted, sorted.length);
                        break funcMainLoop;
                    }
                }
            }

            else if (option.equals("2"))
            {
                while (true)
                {
                    System.out.print("Urutan (asc | desc): ");
                    String direction = input.nextLine().trim().toLowerCase();

                    if (direction.toLowerCase().equals("x"))
                        break funcMainLoop;

                    if (!direction.equals("asc") && !direction.equals("desc"))
                        System.out.println("[!] urutan tidak valid");

                    else
                    {
                        Wanderer[] sorted = HelperSortWandererLevel(direction.equals("asc"));

                        helperShowWanderers(sorted, sorted.length);
                        break funcMainLoop;
                    }
                }
            }

            else
                System.out.println("Opsi tidak valid!");
        }
    }

    // sample subtask 7 :p
    //clang-format off
    public static void d()
    {
        gameManager.addWanderer(new Wanderer(6, new String(new char[] {83, 121, 97, 117, 113, 105}), new String(new char[] {115, 109, 121}), "praktikum5siang", "combat"));
        gameManager.addWanderer(new Wanderer(7, new String(new char[] {83, 101, 97, 110, 32, 70, 97, 114, 114, 101, 108}), new String(new char[] {102, 97, 114}), new String(new char[] {112, 114, 97, 107, 116, 105, 107, 117, 109, 53, 115, 105, 97, 110, 103}), "combat"));
        gameManager.addWanderer(new Wanderer(8, new String(new char[] {65, 108, 119, 105, 101, 32, 65, 116, 116, 97, 114}), new String(new char[] {101, 108, 102}), new String(new char[] {112, 114, 97, 107, 116, 105, 107, 117, 109, 53, 115, 105, 97, 110, 103}), "combat"));

        gameManager.addPrisoner(new String(new char[] {115, 109, 121}), new String(new char[] {116, 101, 114, 100, 117, 103, 97, 32, 107, 111, 109, 112, 108, 111, 116, 97, 110, 32, 112, 101, 109, 98, 117, 97, 116, 32, 84, 80, 50}), 15);
        gameManager.addPrisoner(new String(new char[] {102, 97, 114}), new String(new char[] {116, 101, 114, 100, 117, 103, 97, 32, 107, 111, 109, 112, 108, 111, 116, 97, 110, 32, 112, 101, 109, 98, 117, 97, 116, 32, 84, 80, 50}), 15);
        gameManager.addPrisoner(new String(new char[] {101, 108, 102}), new String(new char[] {116, 101, 114, 100, 117, 103, 97, 32, 107, 111, 109, 112, 108, 111, 116, 97, 110, 32, 112, 101, 109, 98, 117, 97, 116, 32, 84, 80, 50}), 15);
        // no serious feeling heeheheheh
    }


    /*****/

    // Wanderer#validateQuestInput
    public static String[] validateWandererInput(GameManager gm, String name, String username, String password, String specialization)
    {
        String[] temp  = new String[7];
        int      count = 0;

        if (name.isEmpty())
            temp[count++] = ("Nama pengembara tidak boleh kosong");
        if (!name.matches("^[a-zA-Z ]+$"))
            temp[count++] = ("Nama pengembara hanya boleh terdiri dari huruf dan spasi");
        if (!name.matches("^([A-Z][a-z]*(\\s|$))+$"))
            temp[count++] = ("Huruf kapital hanya boleh digunakan di awal setiap kata");

        if (username.isEmpty())
            temp[count++] = ("Username tidak boleh kosong");
        if (!username.matches("^[a-zA-Z0-9_]+$"))
            temp[count++] = ("Username hanya boleh huruf, angka, dan underscore");

        if (password.isEmpty())
            temp[count++] = ("Password tidak boleh kosong");

        if (gm.getWandererByUsername(username) != null)
            temp[count++] = ("Username sudah pernah digunakan");


        if (!specialization.equals("combat") && !specialization.equals("gathering") && !specialization.equals("diplomacy"))
            temp[count++] = ("Specialization pengembara harus: combat ATAU gathering ATAU diplomacy");

        String[] invalids = new String[count];

        for (int i = 0; i < count; i++)
            invalids[i] = temp[i];

        return invalids;
    }

    // Quest#validateQuestInput
    public static String[] validateQuestInput(String name, String desc, String reward, String bonus, String diff, String duration, String type)
    {
        String[] temp  = new String[9];
        int      count = 0;

        if (name.isEmpty())
            temp[count++] = ("Nama quest tidak boleh kosong");
        if (desc.isEmpty())
            temp[count++] = ("Deskripsi quest tidak boleh kosong");

        if (!name.matches("^[A-Za-z0-9 ]+$"))
            temp[count++] = ("Nama quest hanya boleh alphanumerik dan spasi");
        if (!desc.matches("^[A-Za-z0-9 ]+$"))
            temp[count++] = ("Deskripsi quest hanya boleh alphanumerik dan spasi");

        if (!reward.matches("\\d+"))
            temp[count++] = ("Reward harus bilangan bulat nonnegatif");
        if (!bonus.matches("\\d+"))
            temp[count++] = ("Bonus harus bilangan bulat nonnegatif");

        if (!diff.equals("mudah") && !diff.equals("menengah") && !diff.equals("sulit"))
            temp[count++] = ("Tingkat kesulitan harus: mudah ATAU menengah ATAU sulit");

        if (!duration.matches("\\d+"))
            temp[count++] = ("Durasi harus bilangan bulat nonnegatif");

        if (!type.equals("combat") && !type.equals("gathering") && !type.equals("diplomacy"))
            temp[count++] = ("Tipe quest harus: combat ATAU gathering ATAU diplomacy");

        String[] invalids = new String[count];

        for (int i = 0; i < count; i++)
            invalids[i] = temp[i];

        return invalids;
    }
}
//clang-format on
