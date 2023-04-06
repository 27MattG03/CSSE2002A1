package srg.resources;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceContainerTest {

    @Test
    void canStore() {
        ResourceContainer container = new ResourceContainer(ResourceType.REPAIR_KIT,2);
        assertTrue(container.canStore(ResourceType.REPAIR_KIT));
        assertFalse(container.canStore(ResourceType.FUEL));
    }

    @Test
    void getAmount() {
        ResourceContainer container = new ResourceContainer(ResourceType.REPAIR_KIT,4);
        assertEquals(container.getAmount(),4);
    }

    @Test
    void setAmount() {
        ResourceContainer container = new ResourceContainer(ResourceType.REPAIR_KIT,4);
        assertEquals(container.getAmount(),4);
        container.setAmount(3);
        assertEquals(container.getAmount(),3);
    }

    @Test
    void getType() {
        ResourceContainer container = new ResourceContainer(ResourceType.REPAIR_KIT,4);
        assertEquals(container.getType(),ResourceType.REPAIR_KIT);
    }
    @Test
    void testToString() {
        ResourceContainer container = new ResourceContainer(ResourceType.REPAIR_KIT,4);

        assertEquals("REPAIR_KIT: 4",container.toString() );
    }

    @Test
    void getShortName() {
        ResourceContainer container = new ResourceContainer(ResourceType.REPAIR_KIT,4);
        assertEquals("REPAIR_KIT",container.getShortName());
    }
}