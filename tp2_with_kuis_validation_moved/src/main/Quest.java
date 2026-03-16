public class Quest
{
    private String id;
    private String name;
    private String description;
    private String difficulty;
    private String status;
    private String assignedWandererId;
    private int    reward;
    private int    bonusExp;
    private int    daysRequired;
    private int    dayTaken;

    private String type; // combat | gathering | diplomacy 

    public Quest(
        int idNumber, 
        String name, 
        String description, 
        String difficulty, 
        int reward, 
        int bonusExp, 
        int daysRequired, 
        String type
    )
    {
        this.id                 = "Q" + idNumber;
        this.name               = name;
        this.description        = description;
        this.reward             = reward;
        this.bonusExp           = bonusExp;
        this.difficulty         = difficulty;
        this.daysRequired       = daysRequired;
        this.status             = "tersedia";
        this.assignedWandererId = "";
        this.dayTaken           = 0;

        this.type = type;
    }

    public String getId() { return this.id; }
    public String getName() { return this.name; }
    public String getDescription() { return this.description; }
    public String getDifficulty() { return this.difficulty; }
    public String getStatus() { return this.status; }
    public String getAssignedWandererId() { return this.assignedWandererId; }
    public int    getReward() { return this.reward; }
    public int    getBonusExp() { return this.bonusExp; }
    public int    getDaysRequired() { return this.daysRequired; }
    public int    getDayTaken() { return this.dayTaken; }

    public boolean isAvailable() { return this.status.equals("tersedia"); }
    public boolean isTaken() { return this.status.equals("diambil"); }
    public boolean isCompleted() { return this.status.equals("selesai"); }

    public String getType() { return this.type; }


    public void assignTo(String wandererId, int daysCount)
    {
        this.assignedWandererId = wandererId;
        this.dayTaken           = daysCount;
        this.status             = "diambil";
    }

    public void complete()
    {
        this.status = "selesai";
    }

    public String getDisplayString()
    {
        // clang-format off
        return 
            "ID quest:                " + this.id             + "\n" +
            "Name quest:              " + this.name           + "\n" +
            "Deskripsi quest:         " + this.description    + "\n" +
            "Reward quest:            " + this.reward         + " koin\n" +
            "Bonus experience quest:  " + this.bonusExp       + " poin exp\n" +
            "Tingkat kesulitan quest: " + this.difficulty     + "\n" +
            "Durasi quest:            " + this.daysRequired   + "\n" +  
            "Status quest:            " + (String) (this.status.equals("diambil") ? ("diambil oleh " + this.assignedWandererId) : this.status) + "\n" +
            "Tipe quest:              " + this.type; 
        // clang-format on
    }

    @Override
    public String toString()
    {
        return getDisplayString();
    }
}
