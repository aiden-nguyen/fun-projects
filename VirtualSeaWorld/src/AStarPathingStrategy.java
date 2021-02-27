import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy implements PathingStrategy {


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {

        List<Point> path = new ArrayList<>();
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(Node::getF).thenComparingInt(Node::getH));
        Map<Point, Node> open = new HashMap<>();
        Map<Point, Node> closed = new HashMap<>();

        Node first = new Node(start, 0, Distance(start, end), null);

        openList.add(first);
        open.put(start, first);

        while (!openList.isEmpty()) {
            Node current = openList.remove();
            open.remove(current);
            closed.put(current.position(), current);

            if (withinReach.test(current.position(), end)) {
                path.add(current.position());
                current = current.getPriorNode();
                while (current.position() != first.position()) {
                    path.add(current.position());
                    current = current.getPriorNode();
                }
                Collections.reverse(path);
                return path;
            }

            List<Point> neighbors = potentialNeighbors.apply(current.position()).filter(canPassThrough)
                    .filter(p -> !p.equals(start) && !p.equals(end)).collect(Collectors.toList());
            for (Point neighbor : neighbors) {
                if (!closed.containsKey(neighbor)) {
                    Node newNeighbor = new Node(neighbor, current.getG() + Distance(neighbor, current.position()), Distance(neighbor, end), current);
                    if (open.containsKey(neighbor) && newNeighbor.getG() < open.get(neighbor).getG()) {
                        openList.remove(open.get(neighbor));
                        openList.add(newNeighbor);
                        open.replace(neighbor, newNeighbor);
                    }
                    openList.add(newNeighbor);
                    open.put(neighbor, newNeighbor);
                }
            }
        }
        return path;
    }



    public int Distance(Point pos, Point end){
        int deltaX = pos.x - end.x;
        int deltaY = pos.y - end.y;
        return (int) (Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) * 10) ;
    }
}

