import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

class QuestTest
{
    private Quest quest;

    @BeforeEach
    void setUp()
    {
        quest = new Quest(1, "Slime slimer", "slime the slimes", "mudah", 100, 50, 5, "combat");
    }


    // kuis soal 1
    // test 1
    // test 2 di Wanderer.java
    @Test
    void constructor_typeTest()
    {
        assertEquals("combat", quest.getType());
    }

    @Test
    void constructor_nameTest()
    {
        assertEquals("Q1", quest.getId());
    }

    @Test
    void constructor_statusTest()
    {
        assertEquals("tersedia", quest.getStatus());
    }

    @Test
    void constructor_wandererTest()
    {
        assertEquals("", quest.getAssignedWandererId());
    }

    @Test
    void constructor_dayTakenTest()
    {
        assertEquals(0, quest.getDayTaken());
    }


    @Test
    void constructor_fieldTest()
    {
        assertEquals("Slime slimer",     quest.getName());
        assertEquals("slime the slimes", quest.getDescription());
        assertEquals("mudah",            quest.getDifficulty());
        assertEquals(100,                quest.getReward());
        assertEquals(50,                 quest.getBonusExp());
        assertEquals(5,                  quest.getDaysRequired());
    }

    @Test
    void constructor_isAvailableTest()
    {
        assertTrue(quest.isAvailable());
    }

    @Test
    void constructor_isNotTakenTest()
    {
        assertFalse(quest.isTaken());
    }

    @Test
    void constructor_isNotCompletedTest()
    {
        assertFalse(quest.isCompleted());
    }


    @Test
    void assign_questIsNowTakenTest()
    {
        quest.assignTo("P1", 5);
        assertTrue(quest.isTaken());
    }

    @Test
    void assign_wandererIdIsSavedTest()
    {
        quest.assignTo("P1", 5);
        assertEquals("P1", quest.getAssignedWandererId());
    }

    @Test
    void assign_dayTakenIsSavedTest()
    {
        quest.assignTo("P1", 5);
        assertEquals(5, quest.getDayTaken());
    }

    @Test
    void assign_questIsNoLongerAvailableTest()
    {
        quest.assignTo("P1", 5);
        assertFalse(quest.isAvailable());
    }

    @Test
    void complete_questIsCompletedTest()
    {
        quest.assignTo("P1", 5);
        quest.complete();
        assertTrue(quest.isCompleted());
    }

    @Test
    void complete_questNoLongerTakenTest()
    {
        quest.assignTo("P1", 5);
        quest.complete();
        assertFalse(quest.isTaken());
    }

    @Test
    void a_Test()
    {
        quest.getDisplayString();
        assertTrue(true);
    }
}
