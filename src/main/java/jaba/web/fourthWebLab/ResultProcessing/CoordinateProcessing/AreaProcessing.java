package jaba.web.fourthWebLab.ResultProcessing.CoordinateProcessing;

import org.springframework.stereotype.Component;

@Component
public class AreaProcessing implements CartesianAreas {

    @Override
    public boolean firstRotation(Double x, Double y, Double R) {
        if(x >= 0 && y >= 0){
            double circle = x*x+y*y;
            return circle <= R*R && x<=R && y<=R;
        }
        return false;
    }

    @Override
    public boolean secondRotation(Double x, Double y, Double R) {
        return false;
    }

    @Override
    public boolean thirdRotation(Double x, Double y, Double R) {
        if(x <= 0 && y <= 0){
            double line = -x/2 - R/2;
            return line<=y && x>=-R && y>=-R/2;
        }
        return false;
    }


    @Override
    public boolean fourthRotation(Double x, Double y, Double R) {
        if(x>=0 && y <= 0){
            return x<=R && y>=-R/2;
        }
        return false;
    }

    @Override
    public boolean areaCheck(Double x, Double y, Double R) {
        if(R < 0){
            x *= -1;
            y *= -1;
            R *= -1;
        }
        return firstRotation(x,y,R) || secondRotation(x,y,R) || thirdRotation(x,y,R) || fourthRotation(x,y,R);
    }
}
