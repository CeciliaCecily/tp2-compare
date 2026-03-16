public class GameManager
{
    private Quest[]    quests        = new Quest[1000];
    private Wanderer[] wanderers     = new Wanderer[1000];
    private int        questCount    = 0;
    private int        wandererCount = 0;
    private int        daysCount     = 1;

    public Quest[]    getQuests() { return quests; }
    public Wanderer[] getWanderers() { return wanderers; }
    public int        getQuestCount() { return questCount; }
    public int        getWandererCount() { return wandererCount; }
    public int        getDaysCount() { return daysCount; }

    // kuis soal 3
    public Wanderer[] getTiredWanderers()
    {
        Wanderer[] wanderers     = getWanderers();
        int        wandererCount = getWandererCount();

        if (wandererCount == 0)
            return new Wanderer[0];

        Wanderer[] filtered      = new Wanderer[1000];
        int        filteredCount = 0;

        for (int i = 0; i < wandererCount; i++)
        {
            if (wanderers[i].getStamina() < 20)
                filtered[filteredCount++] = wanderers[i];
        }

        return filteredCount == 0 ? new Wanderer[0] : filtered;
    }



    /**
     *  subtask 7 :3
     *  penjara untuk user yang nakal.
     *  niatnya bisa dibebasin tapi keknya ga cukup waktu --05:19, 13/03/2026
     */
    private String[][] prisoners     = new String[1000][4];
    private int        prisonerCount = 0;

    public void addPrisoner(String username, String reason, int duration)
    {
        prisoners[prisonerCount][0] = username;
        prisoners[prisonerCount][1] = reason;
        prisoners[prisonerCount][2] = String.valueOf(duration);
        prisoners[prisonerCount][3] = String.valueOf(getDaysCount());

        prisonerCount++;
    }

    public int        getPrisonerCount() { return prisonerCount; }
    public String[][] getPrisoners() { return prisoners; }

    public String[] getPrisonerByUsername(String username)
    {
        for (int i = 0; i < prisonerCount; i++)
        {
            if (prisoners[i][0].equalsIgnoreCase(username))
                return prisoners[i];
        }

        return null;
    }

    public boolean isPrisoner(String username)
    {
        for (int i = 0; i < prisonerCount; i++)
        {
            if (prisoners[i][0].equalsIgnoreCase(username))
                return true;
        }

        return false;
    }

    /***************************/

    public Wanderer getWandererByUsername(String username)
    {
        for (int i = 0; i < wandererCount; i++)
        {
            if (this.wanderers[i].getUsername().equalsIgnoreCase(username.toLowerCase()))
                return this.wanderers[i];
        }

        return null;
    }

    public Wanderer getWandererById(String id)
    {
        for (int i = 0; i < wandererCount; i++)
        {
            if (this.wanderers[i].getId().equalsIgnoreCase(id.toLowerCase()))
                return this.wanderers[i];
        }

        return null;
    }

    public Quest getQuestById(String id)
    {
        for (int i = 0; i < questCount; i++)
        {
            if (this.quests[i].getId().equalsIgnoreCase(id))
                return this.quests[i];
        }

        return null;
    }

    public boolean isAdmin(String username, String password)
    {
        return username.equals("burhan") && password.equals("burunghantu123");
    }

    public boolean isUser(String username, String password)
    {
        for (int i = 0; i < wandererCount; i++)
        {
            if (this.wanderers[i].authenticate(username, password))
                return true;
        }

        return false;
    }

    public Quest[] nextDay()
    {
        daysCount++;

        Quest[] completed       = new Quest[100];
        int     completedLength = 0;

        for (int i = 0; i < questCount; i++)
        {
            if (this.quests[i].getStatus().equals("diambil"))
            {
                Wanderer wanderer = getWandererById(quests[i].getAssignedWandererId());

                // check bila daysreq > 1
                // spec == type
                // +1 ke daysCount sebagai bonus efisiensi durasi 1 hari
                if (this.quests[i].getDaysRequired() > 1
                    && this.quests[i].getType().equals(wanderer.getSpecialization())
                )
                {
                    if (daysCount + 1 >= (quests[i].getDayTaken() + quests[i].getDaysRequired()))
                    {
                        quests[i].complete();
                        wanderer.completeQuest(quests[i].getBonusExp(), quests[i].getReward());

                        completed[completedLength++] = quests[i];

                        System.out.println("soal 1 triggered");

                        continue;
                    }
                }


                if (daysCount >= (quests[i].getDayTaken() + quests[i].getDaysRequired()))
                {
                    quests[i].complete();

                    wanderer.completeQuest(quests[i].getBonusExp(), quests[i].getReward());

                    completed[completedLength++] = quests[i];
                }
            }
        }


        for (int i = 0; i < this.wandererCount; i++)
        {
            this.wanderers[i].setStamina(this.wanderers[i].getStamina() + 15);
        }

        return completed;
    }

    public void addQuest(Quest quest)
    {
        quests[questCount++] = quest;
    }

    public void addWanderer(Wanderer wanderer)
    {
        wanderers[wandererCount++] = wanderer;
    }

    public boolean takeQuest(Quest quest, Wanderer wanderer)
    {
        if (quest == null || wanderer == null)
            return false;
    
        if (!wanderer.canTakeQuest(quest))
            return false;

        else
        {
            int cost = 20;
            
            if (quest.getDifficulty().equals("menengah")) cost = 40;
            else if (quest.getDifficulty().equals("menengah")) cost = 70;

            if (wanderer.getStamina() < cost)
            {
                System.out.println("Stamina tidak cukup untuk mengambil quest");
                System.out.println("Sisa stamina: " + wanderer.getStamina());

                return false;
            }

            wanderer.setStamina(wanderer.getStamina() - cost);

            quest.assignTo(wanderer.getId(), daysCount);
            wanderer.startQuest();

            return true;
        }
    }
}
