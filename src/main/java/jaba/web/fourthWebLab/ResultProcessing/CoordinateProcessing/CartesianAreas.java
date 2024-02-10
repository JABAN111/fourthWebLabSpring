package jaba.web.fourthWebLab.ResultProcessing.CoordinateProcessing;

public interface CartesianAreas {
    boolean firstRotation(Double x, Double y, Float R);
    boolean secondRotation(Double x, Double y, Float R);
    boolean thirdRotation(Double x, Double y, Float R);
    boolean fourthRotation(Double x,Double y,Float R);
    boolean areaCheck(Double x,Double y,Float R);
}
