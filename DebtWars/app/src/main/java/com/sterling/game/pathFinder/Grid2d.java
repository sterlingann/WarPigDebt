package com.sterling.game.pathFinder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grid2d {
    private final double[][] map;


public class MapNode implements Node<MapNode> {
    private final int x, y;

    public MapNode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double getHeuristic(MapNode goal) {
        return Math.abs(x - goal.x) + Math.abs(y - goal.y);
    }

    public double getTraversalCost(MapNode neighbor) {
        return 1 + map[neighbor.y][neighbor.x];
    }

    public Set<MapNode> getNeighbors() {
        Set<MapNode> neighbors = new HashSet<MapNode>();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if ((i == x && j == y) || i < 0 || j < 0 || j >= map.length
                        || i >= map[j].length) {
                    continue;
                }

                if (((i < x && j < y) || (i > x && j > y))) {
                    continue;
                }

                if (map[j][i] < 0) {
                    continue;
                }

                // TODO: create cache instead of recreation
                neighbors.add(new MapNode(i, j));
            }
        }

        return neighbors;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int getRow(){
        return x;
    }

    public int getCol(){
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MapNode other = (MapNode) obj;
        if (!getOuterType().equals(other.getOuterType()))
            return false;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + getOuterType().hashCode();
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    private Grid2d getOuterType() {
        return Grid2d.this;
    }
}

    public Grid2d(double[][] map) {
        this.map = map;
    }

    public List<MapNode> findPath(int xStart, int yStart, int xGoal, int yGoal) {
        return PathFinding.doAStar(new MapNode(xStart, yStart), new MapNode(
                xGoal, yGoal));
    }

}
