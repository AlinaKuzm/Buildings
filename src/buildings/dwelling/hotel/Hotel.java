package buildings.dwelling.hotel;
import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Dwelling;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class Hotel extends Dwelling {

    public Hotel(HotelFloor[] hotelFloors) {
        super(hotelFloors);
    }

    public Hotel(int countFloor, int[] countSpaces) {
        super(countFloor, countSpaces);
    }

    public int getStars() {
        int stars = 0;
        for (Floor floor : floors) {
            if (floor instanceof HotelFloor) {
                if (stars < ((HotelFloor) floor).getStars()) {
                    stars = ((HotelFloor) floor).getStars();
                }
            }
        }
        return stars;
    }

    @Override
    public Space getBestSpace() {
        double[] coeff = {0.25, 0.5, 1.0, 1.25, 1.5};
        Space bestSpace = null;
        double result = 0;
        for (Floor floor : floors) {
            if (floor instanceof HotelFloor) {
                for (Space flat : floor.getSpaceArray()) {
                    double currentresult = coeff[((HotelFloor) floor).getStars() + 1] * flat.getSquare();
                    if (result < currentresult) {
                        result = currentresult;
                        bestSpace = flat;

                    //Space temporaryBest =  (floor.getBestSpace());
                    // if (temporaryBest.getSquare() > bestSpace.getSquare()) {
                    //    bestSpace = temporaryBest;
                }
            }
        }
    }
    return bestSpace;
}
    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();
        HotelFloor[] floor = (HotelFloor[])getFloorsArray();
        s.append("Hotel (").append(getStars()).append(", ").append(getFloorsCount());
        for (int i = 0; i < floor.length; i++) {
            if (floor[i] instanceof HotelFloor) {
                s.append(", ");
                s.append(floor[i].toString());
            }
        }
        s.append(")");
        return s.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;
        if (!(object instanceof Hotel))
            return false;
        Hotel other = (Hotel) object;
        if (this.getFloorsCount() != other.getFloorsCount() )
            return false;
        if (!(this.getFloorsArray().equals(other))){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
