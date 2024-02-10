package jaba.web.fourthWebLab.ResultProcessing.CoordinateProcessing;

public interface CartesianAreas {
    boolean firstRotation(Double x, Double y, Double R);
    boolean secondRotation(Double x, Double y, Double R);
    boolean thirdRotation(Double x, Double y, Double R);
    boolean fourthRotation(Double x,Double y,Double R);
    boolean areaCheck(Double x, Double y, Double R);
}
