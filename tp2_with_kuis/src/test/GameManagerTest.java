import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

// clang-format off
class GameManagerTest
{
    private GameManager gameManager;
    private Wanderer    wanderer;
    private Quest       easyQuest;
    private Quest       midQuest;
    private Quest       hardQuest;

    @BeforeEach
    void setUp()
    {
        gameManager = new GameManager();
        wanderer    = new Wanderer(1, "Tsumugi Kotobuki", "mugichan", "cute", "combat");
        easyQuest   = new Quest(1, "Easy Quest",  "jawa jawa jawa",  "mudah",    100, 50,  1, "combat");
        midQuest    = new Quest(2, "Mid Quest",   "jawa jawa jawa",  "menengah", 200, 100, 3, "combat");
        hardQuest   = new Quest(3, "Hard Quest",  "jawa jawa jawa",  "sulit",    500, 67,  5, "combat");
    }

    /* */

    @Test
    void weakWandererEmpty()
    {
        gameManager.addWanderer(new Wanderer(2, "Mio Akiyama", "miochan", "cute", "gathering"));
        gameManager.addWanderer(new Wanderer(3, "Ritsu Tainaka", "ricchan", "forehead", "gathering"));
        gameManager.addWanderer(new Wanderer(4, "Azusa Nakano", "azunyan", "neko", "diplomacy"));
    
        assertEquals(0, gameManager.getTiredWanderers().length);
    }

    @Test
    void weakWandererExists()
    {
        wanderer.setStamina(0);
        
        gameManager.addWanderer(wanderer);

        assertTrue(gameManager.getTiredWanderers().length > 0);
    }

    /* */ 

    @Test
    void constructor_startsOnDayOneTest()
    {
        assertEquals(1, gameManager.getDaysCount());
    }

    @Test
    void constructor_hasNoQuestsTest()
    {
        assertEquals(0, gameManager.getQuestCount());
    }

    @Test
    void constructor_hasNoWanderersTest()
    {
        assertEquals(0, gameManager.getWandererCount());
    }

    @Test
    void questCounterTest()
    {
        gameManager.addQuest(easyQuest);
        assertEquals(1, gameManager.getQuestCount());
    }

    @Test
    void wandererCounterTest()
    {
        gameManager.addWanderer(wanderer);
        assertEquals(1, gameManager.getWandererCount());
    }

    @Test
    void multipleCounterTest()
    {
        gameManager.addQuest(easyQuest);
        gameManager.addQuest(midQuest);
        gameManager.addQuest(hardQuest);
        assertEquals(3, gameManager.getQuestCount());
    }

    @Test
    void getWandererByUsernameTest()
    {
        gameManager.addWanderer(wanderer);
        assertEquals(wanderer, gameManager.getWandererByUsername("mugichan"));
    }

    @Test
    void getWandererByUsernameNullTest()
    {
        assertNull(gameManager.getWandererByUsername("bocchi"));
    }

    @Test
    void getWandererByIdTest()
    {
        gameManager.addWanderer(wanderer);
        assertEquals(wanderer, gameManager.getWandererById("p1"));
    }

    @Test
    void getWandererByIdNullTest()
    {
        assertNull(gameManager.getWandererById("p67"));
    }

    @Test
    void getQuestByIdTest()
    {
        gameManager.addQuest(easyQuest);
        assertEquals(easyQuest, gameManager.getQuestById("q1"));
    }

    @Test
    void getQuestByIdNullTest()
    {
        assertNull(gameManager.getQuestById("q67"));
    }

    @Test
    void isAdminTest()
    {
        assertTrue(gameManager.isAdmin("burhan", "burunghantu123"));
    }

    @Test
    void isAdminPasswordTest()
    {
        assertFalse(gameManager.isAdmin("burhan", "burung123"));
    }

    @Test
    void isAdminUsernameTest()
    {
        assertFalse(gameManager.isAdmin("burunghantu", "burhan123"));
    }

    @Test
    void isUserTest()
    {
        gameManager.addWanderer(wanderer);
        assertTrue(gameManager.isUser("mugichan", "cute"));
    }

    @Test
    void isNotUserTest()
    {
        assertFalse(gameManager.isUser("bocchi", "cute"));
    }

    @Test
    void isUserPasswordTest()
    {
        gameManager.addWanderer(wanderer);
        assertFalse(gameManager.isUser("mugichan", "not cute"));
    }

    @Test
    void canTakeEasyQuestTest()
    {
        gameManager.addWanderer(wanderer);
        gameManager.addQuest(easyQuest);
        assertTrue(gameManager.takeQuest(easyQuest, wanderer));
    }

    @Test
    void cannotTakeMidQuestAtLevel1Test()
    {
        gameManager.addWanderer(wanderer);
        gameManager.addQuest(midQuest);
        assertFalse(gameManager.takeQuest(midQuest, wanderer));
    }

    @Test
    void cannotTakeHardQuestAtLevel1Test()
    {
        gameManager.addWanderer(wanderer);
        gameManager.addQuest(hardQuest);
        assertFalse(gameManager.takeQuest(hardQuest, wanderer));
    }

    @Test
    void questNowTakenTest()
    {
        gameManager.addWanderer(wanderer);
        gameManager.addQuest(easyQuest);
        gameManager.takeQuest(easyQuest, wanderer);
        assertTrue(easyQuest.isTaken());
    }

    @Test
    void wandererNowOnQuestTest()
    {
        gameManager.addWanderer(wanderer);
        gameManager.addQuest(easyQuest);
        gameManager.takeQuest(easyQuest, wanderer);
        assertFalse(wanderer.isAvailable());
    }

    @Test
    void TakeQuestTwiceTest()
    {
        gameManager.addWanderer(wanderer);
        gameManager.addQuest(easyQuest);
        gameManager.takeQuest(easyQuest, wanderer);
        assertFalse(gameManager.takeQuest(easyQuest, wanderer));
    }

    @Test
    void nextDay_counterTest()
    {
        gameManager.nextDay();
        assertEquals(2, gameManager.getDaysCount());
    }

    @Test
    void nextDay_questCompleteTest()
    {
        gameManager.addWanderer(wanderer);
        
        gameManager.addQuest(easyQuest);
        
        gameManager.takeQuest(easyQuest, wanderer);
        
        gameManager.nextDay();
        
        assertTrue(easyQuest.isCompleted());
    }

    @Test
    void nextDay_wandererAvailTest()
    {
        gameManager.addWanderer(wanderer);
        gameManager.addQuest(easyQuest);
        gameManager.takeQuest(easyQuest, wanderer);
        gameManager.nextDay();
        gameManager.nextDay();
        assertTrue(wanderer.isAvailable());
    }

    @Test
    void nextDay_returnsTest()
    {
        gameManager.addWanderer(wanderer);
        gameManager.addQuest(easyQuest);
        gameManager.takeQuest(easyQuest, wanderer);
        Quest[] completed = gameManager.nextDay();
        assertEquals(easyQuest, completed[0]);
    }

    @Test
    void getters_WandererTest()
    {
        assertEquals("P1",                wanderer.getId());
        assertEquals("Tsumugi Kotobuki",  wanderer.getName());
        assertEquals("mugichan",          wanderer.getUsername());
        assertEquals("cute",              wanderer.getPassword());
        assertEquals("kosong",            wanderer.getStatus());
        assertEquals(1,                   wanderer.getLevel());
        assertEquals(0,                   wanderer.getExp());
        assertEquals(0,                   wanderer.getCoins());
    }

    @Test
    void getters_QuestTest()
    {
        assertEquals("Q1",             easyQuest.getId());
        assertEquals("Easy Quest",     easyQuest.getName());
        assertEquals("jawa jawa jawa", easyQuest.getDescription());
        assertEquals("mudah",          easyQuest.getDifficulty());
        assertEquals("tersedia",       easyQuest.getStatus());
        assertEquals("",               easyQuest.getAssignedWandererId());
        assertEquals(100,              easyQuest.getReward());
        assertEquals(50,               easyQuest.getBonusExp());
        assertEquals(1,                easyQuest.getDaysRequired());
        assertEquals(0,                easyQuest.getDayTaken());
    }

    @Test
    void a_Test()
    {
        gameManager.getQuests();
        gameManager.getWanderers();
        gameManager.getPrisoners();
        gameManager.getPrisonerCount();
        gameManager.getPrisonerByUsername("aa");
        assertTrue(true);
    }
}

// clang-format on
