package buildings.comparators;
import buildings.interfaces.Floor;
import java.util.Comparator;

public class FloorComparator implements Comparator<Floor> {
    @Override
    public int compare(Floor o1, Floor o2) {
        if(o2.getSpacesSquare() - o1.getSpacesSquare() > 0){
            return 1;
        }
        else if (o2.getSpacesSquare() - o1.getSpacesSquare() < 0){
            return -1;
        }
        else return 0;
    }
}
