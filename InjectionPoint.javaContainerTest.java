public class InjectionPointContainerTest {

  @Test
  public void testAddInjectionPoint() {
    // Create an InjectionPoint (assuming it has a constructor)
    InjectionPoint ip = new InjectionPoint("testPoint");
    InjectionPointContainer container = new InjectionPointContainer();

    container.addInjectionPoint(ip);

    // Assert that the container is not empty
    assertFalse(container.isEmpty());

    // Assert that the getInjectionPointObject method retrieves the added point
    InjectionPoint retrievedPoint = container.getInjectionPointObject("testPoint");
    assertNotNull(retrievedPoint);
    assertEquals(ip, retrievedPoint);
  }

  @Test
  public void testAddDuplicateInjectionPoint() {
    // Create an InjectionPoint
    InjectionPoint ip = new InjectionPoint("duplicatePoint");
    InjectionPointContainer container = new InjectionPointContainer();

    container.addInjectionPoint(ip);

    // Try adding the same point again
    container.addInjectionPoint(ip);

    // Assert that the container size remains 1 (no duplicates added)
    assertEquals(1, container.injectionPointSet.size());
  }

  @Test
  public void testAddAction() {
    // Create an InjectionPoint and Action (assuming they have constructors)
    InjectionPoint ip = new InjectionPoint("testPoint");
    Action action = new Action("testAction");
    InjectionPointContainer container = new InjectionPointContainer();

    container.addInjectionPoint(ip);
    container.addAction(ip, action);

    // Assert that the action list for the injection point is not empty
    List<Action> actions = container.getActionsForInjectionPoint(ip);
    assertFalse(actions.isEmpty());

    // Assert that the retrieved action matches the added one
    assertEquals(action, actions.get(0));
  }

  @Test
  public void testRemoveInjectionPoint() {
    // Create an InjectionPoint and add it to the container
    InjectionPoint ip = new InjectionPoint("pointToRemove");
    InjectionPointContainer container = new InjectionPointContainer();
    container.addInjectionPoint(ip);

    // Remove the injection point with a reason
    container.removeInjectionPoint(ip, "testing removal");

    // Assert that the container is empty after removal
    assertTrue(container.isEmpty());

    // Assert that the getInjectionPointObject method doesn't find the removed point
    assertNull(container.getInjectionPointObject("pointToRemove"));
  }

  @Test
  public void testRemoveActions() {
    // Create an InjectionPoint, Actions, and a container
    InjectionPoint ip = new InjectionPoint("testPoint");
    Action action1 = new Action("action1");
    Action action2 = new Action("action2");
    Action actionToRemove = new Action("toRemove");
    InjectionPointContainer container = new InjectionPointContainer();

    container.addInjectionPoint(ip);
    container.addAction(ip, action1);
    container.addAction(ip, action2);
    container.addAction(ip, actionToRemove);

    List<Action> actionsToRemove = new ArrayList<>();
    actionsToRemove.add(actionToRemove);

    // Remove actions matching the list
    container.removeActions(actionsToRemove, "removing specific action");

    // Assert that the remaining actions don't include the removed one
    List<Action> remainingActions = container.getActionsForInjectionPoint(ip);
    assertFalse(remainingActions.contains(actionToRemove));
    assertEquals(2, remainingActions.size()); // action1 and action2 should remain
  }

@Test
public void testRemoveAllActionsOnly() {
  InjectionPoint ip1 = new InjectionPoint("point1");
  InjectionPoint ip2 = new InjectionPoint("point2");
  Action action1 = new Action("action1");
  Action action2 = new Action("action2");
  Action actionToRemove = new Action("toRemove");
  InjectionPointContainer container = new InjectionPointContainer();

  container.addInjectionPoint(ip1);
  container.addInjectionPoint(ip2);

  container.addAction(ip1, action1);
  container.addAction(ip1, actionToRemove);
  container.addAction(ip2, action1);
  container.addAction(ip2, action2);

  List<Action> actionsToRemove = new ArrayList<>();
  actionsToRemove.add(actionToRemove);

  // Point 1 has both actions, point 2 has only action1 (not to be removed)
  container.removeAllActionsOnly(actionsToRemove, "removing specific action");

  List<Action> remainingActions1 = container.getActionsForInjectionPoint(ip1);
  List<Action> remainingActions2 = container.getActionsForInjectionPoint(ip2);

  // Assert that point1's actions are empty (all removed)
  assertTrue(remainingActions1.isEmpty());

  // Assert that point2 still has action1
  assertEquals(1, remainingActions2.size());
  assertEquals(action1, remainingActions2.get(0));
}

@Test
public void testIsEmptyAfterCreation() {
  InjectionPointContainer container = new InjectionPointContainer();
  assertTrue(container.isEmpty());
}

@Test
public void testIsEmptyAfterRemoveAll() {
  // Add some injection points
  InjectionPoint ip1 = new InjectionPoint("point1");
  InjectionPointContainer container = new InjectionPointContainer();
  container.addInjectionPoint(ip1);

  // Remove the injection point
  container.removeInjectionPoint(ip1, "testing removal");

  // Assert that the container is now empty
  assertTrue(container.isEmpty());
}

@Test
public void testGetInjectionPointObjectFromEmptyContainer() {
  InjectionPointContainer container = new InjectionPointContainer();
  assertNull(container.getInjectionPointObject("anyName"));
}

@Test
public void testGetActionsForInjectionPointFromEmptyContainer() {
  InjectionPointContainer container = new InjectionPointContainer();
  List<Action> actions = container.getActionsForInjectionPoint(new InjectionPoint("anyPoint"));
  assertTrue(actions.isEmpty());
}

@Test(expected = NullPointerException.class)
public void testAddNullInjectionPoint() {
  InjectionPointContainer container = new InjectionPointContainer();
  container.addInjectionPoint(null);
}

@Test
public void testRemoveNullInjectionPoint() {
  InjectionPointContainer container = new InjectionPointContainer();
  container.removeInjectionPoint(null, "reason");
  // Assert that the container remains unchanged (modify assertion based on implementation)
}

@Test(expected = NullPointerException.class)
public void testAddActionWithNullInjectionPoint() {
  InjectionPointContainer container = new InjectionPointContainer();
  container.addAction(null, new Action("someAction"));
}




}
