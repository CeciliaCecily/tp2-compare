public class Wanderer
{
    private String id;
    private String name;
    private String username;
    private String password;
    private String status;
    private int    level;
    private int    exp;
    private int    coins;

    private String specialization; // combat | gathering | diplomacy 
    private int    stamina;

    public Wanderer(int idNumber, String name, String username, String password, String specialization)
    {
        this.id       = "P" + idNumber;
        this.name     = name;
        this.username = username;
        this.password = password;
        this.status   = "kosong";
        this.level    = 1;
        this.exp      = 0;
        this.coins    = 0;

        this.specialization = specialization;
        this.stamina        = 100;
    }

    public String getId() { return this.id; }
    public String getName() { return this.name; }
    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }
    public String getStatus() { return this.status; }
    public int    getLevel() { return this.level; }
    public int    getExp() { return this.exp; }
    public int    getCoins() { return this.coins; }

    public String getSpecialization() { return this.specialization; }
    public int    getStamina() { return this.stamina; }

    public void setStamina(int val) 
    { 
        this.stamina = val; 

        if (this.stamina > 100)
            this.stamina = 100;
    }

    public boolean isAvailable()
    {
        return this.status.equals("kosong");
    }

    public boolean canTakeQuest(Quest quest)
    {
        int req = quest.getDifficulty().equals("menengah") ? 6
                  : quest.getDifficulty().equals("sulit")  ? 16
                                                           : 0;

        return (this.level >= req) && quest.isAvailable();
    }

    public boolean authenticate(String username, String password)
    {
        if (this.username.equals(username) && this.password.equals(password))
            return true;

        return false;
    }

    public void startQuest()
    {
        this.status = "dalam quest";
    }

    public static int getNextLevelExp(int currentLevel)
    {
        if (currentLevel == 1)
            return 5000;
        return 2 * getNextLevelExp(currentLevel - 1);
    }

    public int getNextLevelExp()
    {
        return getNextLevelExp(this.level);
    }

    public void completeQuest(int questExp, int questReward)
    {
        this.exp    += Math.min(this.exp + questExp, 1310720000);
        this.coins  += questReward;
        this.status  = "kosong";

        while (this.exp >= this.getNextLevelExp())
            this.level++;
    }

    public String getDisplayString()
    {
        // clang-format off
        return 
            "ID pengembara:             " + this.id       + "\n" + 
            "Name pengembara:           " + this.name     + "\n" +
            "Username pengembara:       " + this.username + "\n" +
            "Level pengembara:          " + this.level    + "\n" +
            "Exp pengembara:            " + this.exp      + " poin exp\n" +
            "Coin pengembara:           " + this.coins    + " koin\n" +
            "Status pengembara:         " + this.status         + "\n" +
            "Specialization pengembara: " + this.specialization + "\n" +
            "Stamina:                   " + this.stamina;
        // clang-format on
    }

    @Override
    public String toString()
    {
        return getDisplayString();
    }
}
