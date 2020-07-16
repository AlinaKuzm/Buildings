package exception;

public class BuildingUnderArrestException extends Exception {
    public BuildingUnderArrestException() {
    }

    public BuildingUnderArrestException(String message) {
        super(message);
    }
}