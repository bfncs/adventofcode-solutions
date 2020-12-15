package us.byteb.advent.y20;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.byteb.advent.y20.Day12.*;
import static us.byteb.advent.y20.Day12.Direction.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import us.byteb.advent.y20.Day12.*;
import us.byteb.advent.y20.Day12.Instruction.Left;
import us.byteb.advent.y20.Day12.Instruction.Right;

class Day12Test {

  private static final List<Instruction> INSTRUCTIONS =
      parseInput("""
      F10
      N3
      F7
      R90
      F11
      """);
  private static final Position CENTER = new Position(0, 0);

  @Test
  void part1Example() {
    assertEquals(
        new Position(17, 8),
        move(INSTRUCTIONS, DIRECTION_POSITION_MOVE, new DirectionPosition(CENTER, EAST)));
  }

  @Test
  void part2Example() {
    assertEquals(
        new Position(214, 72),
        move(
            INSTRUCTIONS,
            WAYPOINT_POSITION_MOVE,
            new WaypointPosition(CENTER, new Position(10, -1))));
  }

  @Test
  void directionTurn() {
    assertEquals(EAST, NORTH.turn(90));
    assertEquals(SOUTH, NORTH.turn(180));
    assertEquals(WEST, NORTH.turn(270));
    assertEquals(NORTH, NORTH.turn(360));
    assertEquals(NORTH, NORTH.turn(720));
    assertEquals(WEST, NORTH.turn(-90));
    assertEquals(SOUTH, NORTH.turn(-180));
    assertEquals(EAST, NORTH.turn(-270));
    assertEquals(NORTH, NORTH.turn(-360));
    assertEquals(NORTH, NORTH.turn(-720));
  }

  @Test
  void waypointTurn() {
    final WaypointPosition pos = new WaypointPosition(CENTER, new Position(10, -4));
    assertEquals(new Position(4, 10), new Right(90).moveWaypoint(pos).waypoint());
    assertEquals(new Position(-10, 4), new Right(180).moveWaypoint(pos).waypoint());
    assertEquals(new Position(-4, -10), new Right(270).moveWaypoint(pos).waypoint());
    assertEquals(new Position(10, -4), new Right(360).moveWaypoint(pos).waypoint());
    assertEquals(new Position(10, -4), new Right(720).moveWaypoint(pos).waypoint());
    assertEquals(new Position(-4, -10), new Right(-90).moveWaypoint(pos).waypoint());
    assertEquals(new Position(-10, 4), new Right(-180).moveWaypoint(pos).waypoint());
    assertEquals(new Position(4, 10), new Right(-270).moveWaypoint(pos).waypoint());
    assertEquals(new Position(10, -4), new Right(-360).moveWaypoint(pos).waypoint());
    assertEquals(new Position(10, -4), new Right(-720).moveWaypoint(pos).waypoint());

    assertEquals(new Position(-4, -10), new Left(90).moveWaypoint(pos).waypoint());
    assertEquals(new Position(-10, 4), new Left(180).moveWaypoint(pos).waypoint());
    assertEquals(new Position(4, 10), new Left(270).moveWaypoint(pos).waypoint());
    assertEquals(new Position(10, -4), new Left(360).moveWaypoint(pos).waypoint());
    assertEquals(new Position(10, -4), new Left(720).moveWaypoint(pos).waypoint());
    assertEquals(new Position(4, 10), new Left(-90).moveWaypoint(pos).waypoint());
    assertEquals(new Position(-10, 4), new Left(-180).moveWaypoint(pos).waypoint());
    assertEquals(new Position(-4, -10), new Left(-270).moveWaypoint(pos).waypoint());
    assertEquals(new Position(10, -4), new Left(-360).moveWaypoint(pos).waypoint());
    assertEquals(new Position(10, -4), new Left(-720).moveWaypoint(pos).waypoint());
  }
}
