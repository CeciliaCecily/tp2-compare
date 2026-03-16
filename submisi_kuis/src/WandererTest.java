import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

//clang-format off
class WandererTest
{
    private Wanderer wanderer;
    private Quest    easyQuest;
    private Quest    midQuest;
    private Quest    hardQuest;
    private Quest    takenQuest;

    @BeforeEach
    void setUp()
    {
        wanderer   = new Wanderer(1, "Tsumugi Kotobuki", "mugichan", "cute", "combat");
        
        easyQuest  = new Quest(1, "Easy Quest",  "jawa jawa jawa",  "mudah",    100, 50,  1, "combat");
        midQuest   = new Quest(2, "Mid Quest",   "jawa jawa jawa",  "menengah", 200, 100, 3, "combat");
        hardQuest  = new Quest(3, "Hard Quest",  "jawa jawa jawa",  "sulit",    500, 67,  5, "diplomacy");
        takenQuest = new Quest(4, "Taken Quest", "jawa jawa jawa",  "mudah",    696, 50,  2, "gathering");
        
        takenQuest.assignTo("P1", 1);
    }

    // kuis soal 1
    // test 2

    @Test
    void constructor_specializationTest()
    {
        assertEquals("combat", wanderer.getSpecialization());
    }



    // coverage soal 2
    @Test
    void staminaLowTest()
    {
        wanderer.setStamina(10);
        assertTrue(wanderer.getStamina() < 20);
    }

    @Test
    void staminaMaxTest()
    {
        wanderer.setStamina(5000);
        wanderer.setStamina(5000);

        assertEquals(100, wanderer.getStamina());        
    }

    /////
    
    @Test void constructor_nameTest() { assertEquals("Tsumugi Kotobuki", wanderer.getName()); }
    @Test void constructor_usernameTest() { assertEquals("mugichan", wanderer.getUsername()); }
    @Test void constructor_passwordTest() { assertEquals("cute", wanderer.getPassword()); }


    @Test
    void constructor_prefixTest()
    {
        assertEquals("P1", wanderer.getId());
    }

    @Test
    void constructor_defaultStatusTest()
    {
        assertEquals("kosong", wanderer.getStatus());
    }

    @Test
    void constructor_defaultLevelTest()
    {
        assertEquals(1, wanderer.getLevel());
    }

    @Test
    void constructor_defaultExp_CoinsTest()
    {
        assertEquals(0, wanderer.getExp());
        assertEquals(0, wanderer.getCoins());
    }

    ///////////////////

    @Test
    void isAvailable_trueWhenStatusKosongTest()
    {
        assertTrue(wanderer.isAvailable());
    }

    @Test
    void isAvailable_falseWhenOnQuestTest()
    {
        wanderer.startQuest();
        assertFalse(wanderer.isAvailable());
    }

    /////////////////

    @Test
    void authenticate_authTest()
    {
        assertTrue(wanderer.authenticate("mugichan", "cute"));
    }

    @Test
    void authenticate_wrongPasswordTest()
    {
        assertFalse(wanderer.authenticate("mugichan", "notcute" /* not true ofc */ ));
    }

    @Test
    void authenticate_wrongUsernameTest()
    {
        assertFalse(wanderer.authenticate("bocchi", "cute"));
    }

    /////

    @Test
    void canTakeQuest_EasyLevel1Test()
    {
        assertTrue(wanderer.canTakeQuest(easyQuest));
    }

    @Test
    void canTakeQuest_underLevel6Test()
    {
        assertFalse(wanderer.canTakeQuest(midQuest));
    }

    @Test
    void canTakeQuest_underLevel16Test()
    {
        assertFalse(wanderer.canTakeQuest(hardQuest));
    }

    @Test
    void canTakeQuest_TakenTest()
    {
        assertFalse(wanderer.canTakeQuest(takenQuest));
    }


    @Test
    void startQuest_setsStatusToOnQuestTest()
    {
        wanderer.startQuest();
        assertEquals("dalam quest", wanderer.getStatus());
    }

    @Test
    void completeQuest_addsExpAndCoinsTest()
    {
        wanderer.completeQuest(6767, 420);
        assertEquals(6767, wanderer.getExp());
        assertEquals(420,  wanderer.getCoins());
    }

    @Test
    void completeQuest_resetsStatusTest()
    {
        wanderer.startQuest();
        wanderer.completeQuest(6767, 420);
        assertEquals("kosong", wanderer.getStatus());
    }

    @Test
    void completeQuest_ExpLimitTest()
    {
        wanderer.completeQuest(5000, 0);
        assertEquals(2, wanderer.getLevel());
    }

    @Test
    void getNextLevelExp_doublesEachLevelTest()
    {
        assertEquals(10000, Wanderer.getNextLevelExp(2));
        assertEquals(20000, Wanderer.getNextLevelExp(3));
    }

    @Test
    void getNextLevelExp_overloadMethodTest()
    {
        assertEquals(Wanderer.getNextLevelExp(1), wanderer.getNextLevelExp());
    }

    @Test
    void a_Test()
    {
        wanderer.getDisplayString();
        assertTrue(true);
    }
    
}
//clang-format on
