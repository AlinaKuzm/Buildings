package buildings.dwelling.hotel;

import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.interfaces.Space;

public class HotelFloor extends DwellingFloor {

    public static final int HOTEL_STARS = 1;
    private int stars;

    public HotelFloor(int spacesCount) {
        super(spacesCount);
        this.stars = HOTEL_STARS;
    }
    public HotelFloor(Space[] spaces){
        super(spaces);
        this.stars = HOTEL_STARS;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append("HotelFloor (").append(getStars()).append(", ").append(getSpacesCount());
        for (Space flat : super.getSpaceArray()) {
            s.append(", ");
            s.append(flat.toString());
        }
        s.append(")");
        return s.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;
            if (!super.equals(object))
                return false;
        if (!(object instanceof HotelFloor))
            return false;
        HotelFloor other = (HotelFloor) object;
        if (stars != other.stars){
            return false;}
                return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode() ^stars;
        return result;
    }

    }


