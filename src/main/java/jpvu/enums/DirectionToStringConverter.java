package jpvu.enums;

public class DirectionToStringConverter {

    public static String convert(Directions direction){
        switch(direction){
            case UP: return "up";
            case DOWN: return "down";
            case LEFT: return "left";
            case RIGHT: return "right";
            default:
                System.out.println("Invalid direction");
                return "invalid direction";
        }
    }
}
